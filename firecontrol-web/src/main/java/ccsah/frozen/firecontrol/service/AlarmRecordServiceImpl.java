package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.domain.dao.AlarmRecordDao;
import ccsah.frozen.firecontrol.domain.dao.AlarmRecordDaoSpec;
import ccsah.frozen.firecontrol.domain.dto.AlarmRecordDetailDto;
import ccsah.frozen.firecontrol.domain.dto.AlarmRecordListDto;
import ccsah.frozen.firecontrol.domain.entity.AlarmRecord;
import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.entity.Employee;
import ccsah.frozen.firecontrol.domain.knowledge.ConfirmedType;
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
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 10:52
 * DESC 报警记录
 */
@Service
public class AlarmRecordServiceImpl implements AlarmRecordService {

    @Autowired
    private AlarmRecordDao recordDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/27 11:19
     * DESC 添加报警记录
     */
    @Override
    @Transactional
    public AlarmRecordDetailDto addAlarmRecord(String deviceId,
                                               String alarmContent,
                                               long alarmTime) {
        Device device = baseService.getEquipmentById(deviceId);
        Timestamp time = Timestamp.from(Instant.ofEpochMilli(alarmTime));
        if (getRecordByDeviceAndAlarmTime(device, time) != null) {
            throw new ServiceException(540, ExceptionCode.ALARM_RECORD540);
        }
        AlarmRecord alarmRecord = AlarmRecord.create(
                device,
                alarmContent,
                time,
                baseService.getBaseEmployee(),
                Timestamp.from(Instant.ofEpochMilli(0)),
                baseService.getBaseEmployee(),
                ConfirmedType.UNCONFIRMED,
                "",
                Timestamp.from(Instant.ofEpochMilli(0)),
                "");
        return getAlarmRecordDetailDto(alarmRecord);
    }

    /**
     * DATE 2019/12/27 11:22
     * DESC 删除报警记录
     */
    @Override
    @Transactional
    public AlarmRecordDetailDto deletedAlarmRecord(String alarmRecordId) {
        AlarmRecord alarmRecord = getRecordById(alarmRecordId);
        alarmRecord.deleteLogical();
        return getAlarmRecordDetailDto(alarmRecord);
    }

    /**
     * DATE 2019/12/27 12:40
     * DESC 更新报警记录
     */
    @Override
    @Transactional
    public AlarmRecordDetailDto updateAlarmRecordMessage(String alarmRecordId,
                                                         String alarmReceiverId,
                                                         long alarmReceiveTime,
                                                         String confirmedPersonId,
                                                         ConfirmedType confirmedType,
                                                         String confirmedResult,
                                                         long confirmedTime,
                                                         String eventScene) {
        AlarmRecord alarmRecord = getRecordById(alarmRecordId);
        alarmRecord.setAlarmReceiver(baseService.getEmployeeById(alarmReceiverId));
        alarmRecord.setAlarmReceiveTime(Timestamp.from(Instant.ofEpochMilli(alarmReceiveTime)));
        alarmRecord.setConfirmedPerson(baseService.getEmployeeById(confirmedPersonId));
        alarmRecord.setConfirmedType(confirmedType);
        alarmRecord.setConfirmedResult(confirmedResult);
        alarmRecord.setConfirmedTime(Timestamp.from(Instant.ofEpochMilli(confirmedTime)));
        if (StringUtil.isNullOrEmpty(eventScene)) {
            eventScene = "";
        }
        alarmRecord.setEventScene(eventScene);
        return getAlarmRecordDetailDto(alarmRecord);
    }

    @Override
    @Transactional
    public AlarmRecordDetailDto updateAlarmReceiveMessage(String alarmRecordId,
                                                          String alarmReceiverId,
                                                          long alarmReceiveTime) {
        AlarmRecord alarmRecord = getRecordById(alarmRecordId);
        alarmRecord.setAlarmReceiver(baseService.getEmployeeById(alarmReceiverId));
        alarmRecord.setAlarmReceiveTime(Timestamp.from(Instant.ofEpochMilli(alarmReceiveTime)));
        return getAlarmRecordDetailDto(alarmRecord);
    }

    @Override
    @Transactional
    public AlarmRecordDetailDto updateAlarmConfirmedMessage(String alarmRecordId,
                                                            String confirmedPersonId,
                                                            ConfirmedType confirmedType,
                                                            String confirmedResult,
                                                            long confirmedTime,
                                                            String eventScene) {
        AlarmRecord alarmRecord = getRecordById(alarmRecordId);
        alarmRecord.setConfirmedPerson(baseService.getEmployeeById(confirmedPersonId));
        alarmRecord.setConfirmedType(confirmedType);
        alarmRecord.setConfirmedResult(confirmedResult);
        alarmRecord.setConfirmedTime(Timestamp.from(Instant.ofEpochMilli(confirmedTime)));
        if (StringUtil.isNullOrEmpty(eventScene)) {
            eventScene = "";
        }
        alarmRecord.setEventScene(eventScene);
        return getAlarmRecordDetailDto(alarmRecord);
    }

    /**
     * DATE 2019/12/27 12:44
     * DESC 获取单条报警记录的详细信息
     */
    @Override
    public AlarmRecordDetailDto getAlarmRecordDetail(String alarmRecordId) {
        AlarmRecord alarmRecord = getRecordById(alarmRecordId);
        return getAlarmRecordDetailDto(alarmRecord);
    }

    @Override
    public List<AlarmRecordListDto> listAlarmRecordByEquipment(String equipmentId) {
        Device device = baseService.getEquipmentById(equipmentId);
        List<AlarmRecord> alarmRecordList = recordDao.findAllByDeviceAndIsDeletedFalseOrderByCtimeDesc(device);
        if (alarmRecordList == null || alarmRecordList.isEmpty()) {
            return null;
        }
        return getAlarmRecordListDto(alarmRecordList);
    }

    /**
     * DATE 2019/12/27 12:36
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<AlarmRecordListDto> listAlarmRecordByParameter(String deviceId,
                                                                   String alarmContent,
                                                                   String alarmReceiverId,
                                                                   String confirmedPersonId,
                                                                   String confirmedResult,
                                                                   long startQueryTime,
                                                                   long endQueryTime,
                                                                   Pageable pageRequest) {
        Device device = StringUtil.isNullOrEmpty(deviceId) ? null : baseService.getEquipmentById(deviceId);
        Employee alarmReceiver = StringUtil.isNullOrEmpty(alarmReceiverId) ? null : baseService.getEmployeeById(alarmReceiverId);
        Employee confirmedPerson = StringUtil.isNullOrEmpty(confirmedPersonId) ? null : baseService.getEmployeeById(confirmedPersonId);
        Specification<AlarmRecord> querySpec = AlarmRecordDaoSpec.getVariableSpec(
                device,
                alarmContent,
                alarmReceiver,
                confirmedPerson,
                confirmedResult,
                startQueryTime,
                endQueryTime);
        Page<AlarmRecord> alarmRecordList = recordDao.findAll(querySpec, pageRequest);
        List<AlarmRecordListDto> alarmRecordListDtoList = getAlarmRecordListDto(alarmRecordList.getContent());
        return new PageData<>(alarmRecordListDtoList, (int) alarmRecordList.getTotalElements());
    }

    /**
     * DATE 2019/12/27 11:21
     * DESC
     */
    private AlarmRecord getRecordById(String alarmRecordId) {
        AlarmRecord alarmRecord = recordDao.findByIdAndIsDeletedFalse(alarmRecordId);
        if (alarmRecord == null) {
            throw new ServiceException(541, ExceptionCode.ALARM_RECORD541);
        }
        return alarmRecord;
    }

    /**
     * DATE 2019/12/27 11:06
     * DESC
     */
    private AlarmRecord getRecordByDeviceAndAlarmTime(Device device, Timestamp alarmTime) {
        return recordDao.findByDeviceAndAlarmTimeAndIsDeletedFalse(device, alarmTime);
    }

    private AlarmRecordDetailDto getAlarmRecordDetailDto(AlarmRecord alarmRecord) {
        return new AlarmRecordDetailDto(
                alarmRecord.getId(),
                alarmRecord.getDevice().getId(),
                alarmRecord.getDevice().getDeviceCode(),
                alarmRecord.getAlarmContent(),
                alarmRecord.getAlarmTime().getTime(),
                alarmRecord.getAlarmReceiver().getId(),
                alarmRecord.getAlarmReceiver().getEmployeeName(),
                alarmRecord.getAlarmReceiveTime().getTime(),
                alarmRecord.getConfirmedPerson().getId(),
                alarmRecord.getConfirmedPerson().getEmployeeName(),
                alarmRecord.getConfirmedResult(),
                alarmRecord.getConfirmedTime().getTime(),
                alarmRecord.getEventScene());
    }

    private AlarmRecordListDto getAlarmRecordListDto(AlarmRecord alarmRecord) {
        return new AlarmRecordListDto(
                alarmRecord.getId(),
                alarmRecord.getDevice().getId(),
                alarmRecord.getDevice().getDeviceCode(),
                alarmRecord.getAlarmContent(),
                alarmRecord.getAlarmTime().getTime());
    }

    private List<AlarmRecordListDto> getAlarmRecordListDto(List<AlarmRecord> alarmRecordList) {
        return alarmRecordList.stream().map(this::getAlarmRecordListDto).collect(Collectors.toList());
    }
}
