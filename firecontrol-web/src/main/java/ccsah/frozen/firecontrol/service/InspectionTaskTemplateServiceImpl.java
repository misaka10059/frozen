package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.domain.dao.InspectionPointTemplateDao;
import ccsah.frozen.firecontrol.domain.dao.InspectionTaskTemplateDao;
import ccsah.frozen.firecontrol.domain.dao.InspectionTaskTemplateDaoSpec;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskTemplateDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskTemplatePointDto;
import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.entity.Employee;
import ccsah.frozen.firecontrol.domain.entity.InspectionPointTemplate;
import ccsah.frozen.firecontrol.domain.entity.InspectionTaskTemplate;
import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import ccsfr.core.domain.BaseEntity;
import ccsfr.core.domain.EntityManagerHolder;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/19 10:07
 * DESC
 */
@Service
public class InspectionTaskTemplateServiceImpl implements InspectionTaskTemplateService {

    @Autowired
    private InspectionTaskTemplateDao taskTemplateDao;

    @Autowired
    private InspectionPointTemplateDao pointTemplateDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/19 11:04
     * DESC 添加巡检模板
     */
    @Override
    @Transactional
    public InspectionTaskTemplatePointDto addTaskTemplate(TemplateState templateState,
                                                          String templateNumber,
                                                          String inspectionContent,
                                                          FrequencyTypes inspectionFrequency,
                                                          String additionalFields,
                                                          int hourTime,
                                                          int minuteTime,
                                                          String inspectorId,
                                                          List<String> deviceIdList) {
        if ((getTaskTemplateByTemplateNumber(templateNumber) != null) || (getTaskTemplateByInspectionContent(inspectionContent) != null)) {
            throw new ServiceException(510, ExceptionCode.TASK_TEMPLATE510);
        }
        //生成taskTemplate
        InspectionTaskTemplate taskTemplate = InspectionTaskTemplate.create(templateState,
                templateNumber,
                inspectionContent,
                inspectionFrequency,
                additionalFields,
                hourTime,
                minuteTime,
                baseService.getEmployeeById(inspectorId));
        //生成pointTemplate
        deviceIdList.stream()
                .filter(deviceId -> getPointTemplateByTaskTemplateIdAndDeviceId(taskTemplate.getId(), deviceId) == null)
                .forEach(deviceId -> InspectionPointTemplate.create(
                        baseService.getTaskTemplateById(taskTemplate.getId()),
                        baseService.getEquipmentById(deviceId)));
        EntityManagerHolder.getEntityManager().flush();
        EntityManagerHolder.getEntityManager().refresh(taskTemplate);
        return baseService.getTaskTemplatePointDto(taskTemplate);
    }

    /**
     * DATE 2019/12/25 14:38
     * DESC 删除巡检模板
     */
    @Override
    @Transactional
    public InspectionTaskTemplateDto deleteTaskTemplate(String taskTemplateId) {
        InspectionTaskTemplate taskTemplate = baseService.getTaskTemplateById(taskTemplateId);
        if (!taskTemplate.getPointTemplateList().isEmpty()) {
            taskTemplate.getPointTemplateList().forEach(BaseEntity::deleteLogical);
        }
        taskTemplate.deleteLogical();//TODO
        return getTaskTemplateDto(taskTemplate);
    }

    /**
     * DATE 2019/12/25 14:46
     * DESC 更新巡检模板
     */
    @Override
    @Transactional
    public InspectionTaskTemplateDto updateTaskTemplate(String taskTemplateId,
                                                        TemplateState templateState,
                                                        String templateNumber,
                                                        String inspectionContent,
                                                        FrequencyTypes inspectionFrequency,
                                                        String additionalFields,
                                                        int hourTime,
                                                        int minuteTime,
                                                        String inspectorId) {
        InspectionTaskTemplate taskTemplate = baseService.getTaskTemplateById(taskTemplateId);
        InspectionTaskTemplate anotherTaskTemplate = getTaskTemplateByTemplateNumber(templateNumber);
        if (anotherTaskTemplate != null && !anotherTaskTemplate.getId().equals(taskTemplateId)) {
            throw new ServiceException(510, ExceptionCode.TASK_TEMPLATE510);
        }
        taskTemplate.setTemplateState(templateState);
        taskTemplate.setTemplateNumber(templateNumber);
        taskTemplate.setInspectionContent(inspectionContent);
        taskTemplate.setInspectionFrequency(inspectionFrequency);
        taskTemplate.setAdditionalFields(additionalFields);
        taskTemplate.setHourTime(hourTime);
        taskTemplate.setMinuteTime(minuteTime);
        taskTemplate.setInspector(baseService.getEmployeeById(inspectorId));
        return getTaskTemplateDto(taskTemplate);
    }

    /**
     * DATE 2019/12/25 15:15
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<InspectionTaskTemplatePointDto> listTaskTemplateByParameter(TemplateState templateState,
                                                                                String templateNumber,
                                                                                String inspectionContent,
                                                                                FrequencyTypes inspectionFrequency,
                                                                                String inspectorId,
                                                                                long startQueryTime,
                                                                                long endQueryTime,
                                                                                Pageable pageRequest) {
        Employee inspector = StringUtil.isNullOrEmpty(inspectorId) ? null : baseService.getEmployeeById(inspectorId);
        Specification<InspectionTaskTemplate> querySpec = InspectionTaskTemplateDaoSpec.getVariableSpec(
                templateState,
                templateNumber,
                inspectionContent,
                inspectionFrequency,
                inspector,
                startQueryTime,
                endQueryTime);
        Page<InspectionTaskTemplate> taskTemplateList = taskTemplateDao.findAll(querySpec, pageRequest);
        List<InspectionTaskTemplatePointDto> taskTemplateDtoList = baseService.getTaskTemplatePointDto(taskTemplateList.getContent());
        return new PageData<>(taskTemplateDtoList, (int) taskTemplateList.getTotalElements());
    }

    /**
     * DATE 2019/12/19 11:38
     * DESC
     */
    private InspectionPointTemplate getPointTemplateByTaskTemplateIdAndDeviceId(String taskTemplateId, String deviceId) {
        InspectionTaskTemplate taskTemplate = baseService.getTaskTemplateById(taskTemplateId);
        Device device = baseService.getEquipmentById(deviceId);
        return pointTemplateDao.findInspectionPointTemplateByInspectionTaskTemplateAndDeviceAndIsDeletedFalse(taskTemplate, device);
    }

    /**
     * DATE 2019/12/19 10:15
     * DESC
     */
    private InspectionTaskTemplate getTaskTemplateByTemplateNumber(String templateNumber) {
        return taskTemplateDao.findInspectionTaskTemplateByTemplateNumberAndIsDeletedFalse(templateNumber);
    }

    /**
     * DATE 2019/12/19 10:17
     * DESC
     */
    private InspectionTaskTemplate getTaskTemplateByInspectionContent(String inspectionContent) {
        return taskTemplateDao.findInspectionTaskTemplateByInspectionContentAndIsDeletedFalse(inspectionContent);
    }

    private InspectionTaskTemplateDto getTaskTemplateDto(InspectionTaskTemplate taskTemplate) {
        return new InspectionTaskTemplateDto(
                taskTemplate.getId(),
                taskTemplate.getTemplateState(),
                taskTemplate.getTemplateNumber(),
                taskTemplate.getInspectionContent(),
                taskTemplate.getInspectionFrequency(),
                taskTemplate.getAdditionalFields(),
                taskTemplate.getHourTime(),
                taskTemplate.getMinuteTime(),
                taskTemplate.getInspector().getId(),
                taskTemplate.getInspector().getEmployeeName());
    }
}
