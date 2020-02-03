package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.common.offset.AlarmContentOffset;
import ccsah.frozen.iot.domain.dao.AlarmRecordDao;
import ccsah.frozen.iot.domain.dao.AlarmRecordDaoSpec;
import ccsah.frozen.iot.domain.dto.AlarmRecordDto;
import ccsah.frozen.iot.domain.dto.alarm.AlarmDto;
import ccsah.frozen.iot.domain.entity.AlarmRecord;
import ccsah.frozen.iot.domain.entity.Device;
import ccsah.frozen.iot.domain.knowledge.DeviceRunState;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 15:58
 * DESC
 */
@Service
public class AlarmRecordServiceImpl implements AlarmRecordService {

    @Autowired
    private AlarmRecordDao alarmRecordDao;

    @Autowired
    private BaseService baseService;

    @Autowired
    private MessageProviderService messageProviderService;

    /**
     * DATE 2019/12/6 16:07
     * DESC 写入AlarmRecord记录
     */
    @Override
    @Transactional
    public void addAlarmRecord(AlarmDto alarmMessage) {
        Device device = baseService.getDeviceByImeiCode(alarmMessage.getDeviceCode());
        if (device == null) {
            throw new ServiceException(571, ExceptionCode.DEVICE571);
        }
        Timestamp nowTime = Timestamp.valueOf(LocalDateTime.now());
        AlarmRecord.create(
                nowTime,
                alarmMessage.getAlarmContent(),
                baseService.getDeviceById(device.getId()));
        resolve(alarmMessage, nowTime);
    }

    /**
     * DATE 2019/12/31 9:05
     * DESC
     */
    private void resolve(AlarmDto alarmMessage, Timestamp nowTime) {
        Device device = baseService.getDeviceByImeiCode(alarmMessage.getDeviceCode());
        String fireState = alarmMessage.getAlarmContent().substring(AlarmContentOffset.fireStateOffset, AlarmContentOffset.fireStateOffset + 3);
        int baseVoltage = Integer.parseInt(alarmMessage.getAlarmContent().substring(AlarmContentOffset.baseVoltageOffset, AlarmContentOffset.baseVoltageOffset + 4));
        int signalIntensity = Integer.parseInt(alarmMessage.getAlarmContent().substring(AlarmContentOffset.signalIntensityOffset, AlarmContentOffset.signalIntensityOffset + 2));
        if (fireState.equals("010")) {
            fireState = DeviceRunState.TURN_ALARM.toString();
            messageProviderService.sendAlarmMessageDetail(
                    device.getDeviceCode(),
                    alarmMessage.getAlarmContent(),
                    fireState,
                    baseVoltage,
                    signalIntensity,
                    nowTime.getTime());
        }
        if (fireState.equals("000")) {
            fireState = DeviceRunState.RETURN_TO_NORMAL_WORK.toString();
            messageProviderService.sendAlarmMessageDetail(
                    device.getDeviceCode(),
                    alarmMessage.getAlarmContent(),
                    fireState,
                    baseVoltage,
                    signalIntensity,
                    nowTime.getTime());
        }
    }

    /**
     * DATE 2019/12/13 17:15
     * DESC 删除AlarmRecord记录
     */
    @Override
    @Transactional
    public AlarmRecordDto deleteAlarmRecord(String id) {
        AlarmRecord alarmRecord = getAlarmRecordById(id);
        alarmRecord.deleteLogical();
        return getAlarmRecordDto(alarmRecord);
    }

    /**
     * DATE 2019/12/13 17:31
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<AlarmRecordDto> listAlarmRecordByParameter(
            String deviceId,
            long startQueryTime,
            long endQueryTime,
            Pageable pageRequest) {
        Device device = StringUtil.isNullOrEmpty(deviceId) ? null : baseService.getDeviceById(deviceId);
        Specification<AlarmRecord> querySpec = AlarmRecordDaoSpec.getVariableSpec(device, startQueryTime, endQueryTime);
        Page<AlarmRecord> alarmRecordList = alarmRecordDao.findAll(querySpec, pageRequest);
        List<AlarmRecordDto> alarmRecordDtoList = getAlarmRecordDto(alarmRecordList.getContent());
        return new PageData<>(alarmRecordDtoList, (int) alarmRecordList.getTotalElements());
    }

    /**
     * DATE 2019/12/13 17:14
     * DESC
     */
    private AlarmRecord getAlarmRecordById(String id) {
        AlarmRecord alarmRecord = alarmRecordDao.findAlarmRecordByIdAndIsDeletedFalse(id);
        if (alarmRecord == null) {
            throw new ServiceException(531, ExceptionCode.AlARM_RECORD531);
        }
        return alarmRecord;
    }

    private AlarmRecordDto getAlarmRecordDto(AlarmRecord alarmRecord) {
        return new AlarmRecordDto(
                alarmRecord.getId(),
                alarmRecord.getAlarmTime(),
                alarmRecord.getDevice().getDeviceCode());
    }

    private List<AlarmRecordDto> getAlarmRecordDto(List<AlarmRecord> alarmRecordList) {
        return alarmRecordList.stream().map(this::getAlarmRecordDto).collect(Collectors.toList());
    }
}
