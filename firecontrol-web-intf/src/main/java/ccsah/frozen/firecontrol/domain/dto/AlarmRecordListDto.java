package ccsah.frozen.firecontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 11:52
 * DESC
 */
@Getter
@Setter
public class AlarmRecordListDto {

    private String alarmRecordId;

    private String deviceId;

    private String deviceCode;

    private String alarmContent;

    private long alarmTime;

    public AlarmRecordListDto(String alarmRecordId,
                              String deviceId,
                              String deviceCode,
                              String alarmContent,
                              long alarmTime) {
        this.setAlarmRecordId(alarmRecordId);
        this.setDeviceId(deviceId);
        this.setDeviceCode(deviceCode);
        this.setAlarmContent(alarmContent);
        this.setAlarmTime(alarmTime);
    }
}
