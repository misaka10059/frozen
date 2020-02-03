package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.AlarmRecordDto;
import ccsah.frozen.iot.domain.dto.alarm.AlarmDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 15:58
 * DESC
 */
public interface AlarmRecordService {

    void addAlarmRecord(AlarmDto alarmMessage);

    AlarmRecordDto deleteAlarmRecord(String id);

    PageData<AlarmRecordDto> listAlarmRecordByParameter(
            String deviceId,
            long startQueryTime,
            long endQueryTime,
            Pageable pageRequest);
}
