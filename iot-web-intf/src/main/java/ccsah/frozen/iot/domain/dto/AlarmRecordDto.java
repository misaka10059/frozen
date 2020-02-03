package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 16:00
 * DESC
 */
@Getter
@Setter
public class AlarmRecordDto {

    private String alarmRecordId;

    private String deviceCode;

    private Timestamp alarmTime;

    public AlarmRecordDto(String alarmRecordId, Timestamp alarmTime, String deviceCode) {
        this.setAlarmRecordId(alarmRecordId);
        this.setAlarmTime(alarmTime);
        this.setDeviceCode(deviceCode);
    }
}
