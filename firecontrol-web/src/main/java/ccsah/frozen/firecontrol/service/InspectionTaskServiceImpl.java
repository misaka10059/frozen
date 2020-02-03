package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.domain.dao.*;
import ccsah.frozen.firecontrol.domain.dto.InspectionPointDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionPointReceiveDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskDetailDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskDto;
import ccsah.frozen.firecontrol.domain.entity.*;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionPointState;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionTaskState;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import ccsfr.core.domain.BaseEntity;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/25 17:18
 * DESC
 */
@Service
public class InspectionTaskServiceImpl implements InspectionTaskService {

    @Autowired
    private InspectionTaskDao taskDao;

    @Autowired
    private InspectionPointDao pointDao;

    @Autowired
    private InspectionPointTemplateDao pointTemplateDao;

    @Autowired
    private InspectionTaskTemplateDao taskTemplateDao;

    @Autowired
    private BaseService baseService;

    @Autowired
    private MessageProviderService messageProviderService;

    /**
     * DATE 2019/12/26 10:21
     * DESC 获取已启用的巡检模板，并调用生成方法生成巡检任务
     */
    @Override
    @Transactional
    public void check() {
        List<InspectionTaskTemplate> taskTemplateList = taskTemplateDao.findByTemplateStateAndIsDeletedFalseOrderByTemplateNumber(TemplateState.ENABLE);  //查找全部启用的巡检模板

        for (InspectionTaskTemplate taskTemplate : taskTemplateList) {

            LocalDateTime scheduledTime = getScheduledTime(taskTemplate);
            LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);  //获取系统当前的时间，精确到分钟
//            System.out.println(taskTemplate.getInspectionContent());
//            System.out.println("scheduledTime: " + scheduledTime);
//            System.out.println("nowTime      : " + nowTime);
//            System.out.println("-------------------");
            if (scheduledTime.equals(nowTime)) {  //时间一致，调用巡检计划生成方法
                generate(taskTemplate.getId());
            }
        }
    }

    /**
     * DATE 2019/12/26 9:59
     * DESC 根据模板生成巡检任务
     */
    @Override
    @Transactional
    public void generate(String taskTemplateId) {
        InspectionTaskTemplate taskTemplate = baseService.getTaskTemplateById(taskTemplateId);
        Timestamp scheduledTime = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
//        Timestamp scheduledTime = Timestamp.valueOf(getScheduledTime(baseService.getTaskTemplateById(taskTemplateId)));
        if (taskDao.findByInspectionTaskTemplateAndInspectionScheduledTimeAndIsDeletedFalse(taskTemplate, scheduledTime) != null) {
            return;
        }

        InspectionTask inspectionTask = InspectionTask.create(
                taskTemplate,
                taskTemplate.getInspector(),
                getInspectionNumber(),
                taskTemplate.getInspectionContent(),
                InspectionTaskState.NOT_STARTED_INSPECTION,
                scheduledTime,
                Timestamp.from(Instant.ofEpochMilli(0)),
                Timestamp.from(Instant.ofEpochMilli(0)),
                "",
                "");
        List<InspectionPointTemplate> pointTemplateList = pointTemplateDao.findByInspectionTaskTemplateAndIsDeletedFalse(taskTemplate);
        for (InspectionPointTemplate pointTemplate : pointTemplateList) {  //生成对应的巡检点
            InspectionPoint.create(
                    inspectionTask,
                    pointTemplate.getDevice(),
                    InspectionPointState.NOT_INSPECTED,
                    Timestamp.from(Instant.ofEpochMilli(0)));
            //发送巡检点信息
            messageProviderService.sendInspectionMessage(
                    pointTemplate.getDevice().getId(),
                    pointTemplate.getDevice().getDeviceCode(),
                    InspectionPointState.NOT_INSPECTED.toString(),
                    pointTemplate.getDevice().getAlarmArea().getId(),
                    pointTemplate.getDevice().getAlarmArea().getAreaName());
        }
    }

    /**
     * DATE 2020/1/2 16:27
     * DESC
     */
    public InspectionTaskDto deleteTask(String inspectionTaskId) {
        InspectionTask inspectionTask = getInspectionTaskById(inspectionTaskId);
        inspectionTask.getInspectionPointList().forEach(BaseEntity::deleteLogical);
        inspectionTask.deleteLogical();
        return getInspectionTaskDto(inspectionTask);
    }

    /**
     * DATE 2019/12/27 9:42
     * DESC
     */
    @Override
    @Transactional
    public InspectionTaskDto updateTaskMessage(String inspectionTaskId,
                                               InspectionTaskState taskState,
                                               long startTime,
                                               long endTime,
                                               String inspectionResult,
                                               String additionalMessage) {
        InspectionTask task = getInspectionTaskById(inspectionTaskId);
        task.setInspectionTaskState(taskState);
        task.setInspectionStartTime(Timestamp.from(Instant.ofEpochMilli(startTime)));
        task.setInspectionStartTime(Timestamp.from(Instant.ofEpochMilli(endTime)));
        task.setInspectionResult(inspectionResult);
        task.setAdditionalMessage(additionalMessage);
        return getInspectionTaskDto(task);
    }

    /**
     * DATE 2020/1/2 11:18
     * DESC 接收InspectionPointMessage的巡检信息
     */
    @Override
    @Transactional
    public List<InspectionPointDto> receivePointMessage(InspectionPointReceiveDto pointReceiveDto) {
        Device device = baseService.getEquipmentByCode(pointReceiveDto.getDeviceCode());
        if (device == null) {
            throw new ServiceException(531, ExceptionCode.DEVICE531);
        }
        List<InspectionPoint> pointList = pointDao.findAllByDeviceAndPointStateAndIsDeletedFalse(device, InspectionPointState.NOT_INSPECTED);
        if (pointList == null || pointList.isEmpty()) {
            return null;
        }
        List<InspectionPoint> newPointList = new ArrayList<>();
        for (InspectionPoint point : pointList) {
            if (point.getInspectionTask().getInspector().getId().equals(pointReceiveDto.getInspectorId()))
                newPointList.add(point);
        }
        for (InspectionPoint point : newPointList) {
            point.setPointState(pointReceiveDto.getInspectionPointState());
            point.setInspectionTime(Timestamp.valueOf(LocalDateTime.now()));
            //发送巡检点信息
            messageProviderService.sendInspectionMessage(
                    point.getDevice().getId(),
                    point.getDevice().getDeviceCode(),
                    point.getPointState().toString(),
                    point.getDevice().getAlarmArea().getId(),
                    point.getDevice().getAlarmArea().getAreaName());
        }
        return getInspectionPointDto(newPointList);
    }

    /**
     * DATE 2019/12/26 11:22
     * DESC
     */
    @Override
    public PageData<InspectionTaskDto> listInspectionTaskByParameter(String inspectorId,
                                                                     String inspectionTaskNumber,
                                                                     String inspectionContent,
                                                                     InspectionTaskState inspectionTaskState,
                                                                     String inspectionResult,
                                                                     String additionalMessage,
                                                                     Pageable pageRequest) {
        Employee inspector = StringUtil.isNullOrEmpty(inspectorId) ? null : baseService.getEmployeeById(inspectorId);
        Specification<InspectionTask> querySpec = InspectionTaskDaoSpec.getVariableSpec(
                inspector,
                inspectionTaskNumber,
                inspectionContent,
                inspectionTaskState,
                inspectionResult,
                additionalMessage);
        Page<InspectionTask> inspectionTaskList = taskDao.findAll(querySpec, pageRequest);
        List<InspectionTaskDto> inspectionTaskDtoList = getInspectionTaskDto(inspectionTaskList.getContent());
        return new PageData<>(inspectionTaskDtoList, (int) inspectionTaskList.getTotalElements());
    }

    /**
     * DATE 2020/1/2 15:48
     * DESC
     */
    private LocalDateTime getScheduledTime(InspectionTaskTemplate taskTemplate) {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        int day = 0;
        int hour = taskTemplate.getHourTime();
        int minute = taskTemplate.getMinuteTime();
        switch (taskTemplate.getInspectionFrequency()) {
            case DAILY: {
                day = LocalDateTime.now().getDayOfMonth();
                break;
            }
            case WEEKLY: {
                DayOfWeek dayOfWeek = DayOfWeek.of(Integer.parseInt(taskTemplate.getAdditionalFields()));
                day = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(dayOfWeek)).getDayOfMonth();
                break;
            }
            case MONTHLY: {
                day = Integer.parseInt(taskTemplate.getAdditionalFields());
                break;
            }
        }
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * DATE 2019/12/26 11:47
     * DESC
     */
    @Override
    public InspectionTaskDetailDto getInspectionTaskDetail(String inspectionTaskId) {
        InspectionTask inspectionTask = getInspectionTaskById(inspectionTaskId);
        return getInspectionTaskDetailDto(inspectionTask);
    }

    /**
     * DATE 2019/12/26 10:52
     * DESC
     */
    private InspectionTask getInspectionTaskById(String taskId) {
        InspectionTask inspectionTask = taskDao.findByIdAndIsDeletedFalse(taskId);
        if (inspectionTask == null) {
            throw new ServiceException(571, ExceptionCode.INSPECTION_TASK571);
        }
        return inspectionTask;
    }

    /**
     * DATE 2019/12/26 23:07
     * DESC
     */
    private String getInspectionNumber() {
        LocalDateTime time = LocalDateTime.now();
        return "XJ" + time.getYear() + getString(time.getMonthValue()) + getString(time.getDayOfMonth()) + getString(time.getHour()) + getString(time.getMinute());
    }

    /**
     * DATE 2020/1/3 16:09
     * DESC
     */
    private String getString(int value) {
        return (value < 10) ? ("0" + value) : ("" + value);
    }

    private InspectionTaskDto getInspectionTaskDto(InspectionTask inspectionTask) {
        return new InspectionTaskDto(
                inspectionTask.getId(),
                inspectionTask.getInspectionTaskTemplate().getId(),
                inspectionTask.getInspector().getId(),
                inspectionTask.getInspector().getEmployeeName(),
                inspectionTask.getInspectionTaskNumber(),
                inspectionTask.getInspectionContent(),
                inspectionTask.getInspectionTaskState(),
                inspectionTask.getInspectionScheduledTime().getTime(),
                inspectionTask.getInspectionStartTime().getTime(),
                inspectionTask.getInspectionEndTime().getTime(),
                inspectionTask.getInspectionResult(),
                inspectionTask.getAdditionalMessage());
    }

    private List<InspectionTaskDto> getInspectionTaskDto(List<InspectionTask> taskList) {
        return taskList.stream().map(this::getInspectionTaskDto).collect(Collectors.toList());
    }

    private InspectionTaskDetailDto getInspectionTaskDetailDto(InspectionTask inspectionTask) {
        return new InspectionTaskDetailDto(
                inspectionTask.getId(),
                inspectionTask.getInspectionTaskTemplate().getId(),
                inspectionTask.getInspector().getId(),
                inspectionTask.getInspector().getEmployeeName(),
                inspectionTask.getInspectionTaskNumber(),
                inspectionTask.getInspectionContent(),
                inspectionTask.getInspectionTaskState(),
                inspectionTask.getInspectionScheduledTime().getTime(),
                inspectionTask.getInspectionStartTime().getTime(),
                inspectionTask.getInspectionEndTime().getTime(),
                inspectionTask.getInspectionResult(),
                inspectionTask.getAdditionalMessage(),
                getInspectionPointDto(getValidInspectionPointList(inspectionTask.getInspectionPointList())));
    }

    private List<InspectionPoint> getValidInspectionPointList(List<InspectionPoint> inspectionPointList) {
        List<InspectionPoint> newList = new ArrayList<>();
        for (InspectionPoint point : inspectionPointList) {
            if (!point.isDeleted()) {
                newList.add(point);
            }
        }
        return newList;
    }

    private InspectionPointDto getInspectionPointDto(InspectionPoint inspectionPoint) {
        return new InspectionPointDto(
                inspectionPoint.getId(),
                inspectionPoint.getInspectionTask().getId(),
                inspectionPoint.getDevice().getId(),
                inspectionPoint.getDevice().getDeviceCode(),
                inspectionPoint.getPointState(),
                inspectionPoint.getInspectionTime().getTime());
    }

    private List<InspectionPointDto> getInspectionPointDto(List<InspectionPoint> inspectionPointList) {
        return inspectionPointList.stream().map(this::getInspectionPointDto).collect(Collectors.toList());
    }
}
