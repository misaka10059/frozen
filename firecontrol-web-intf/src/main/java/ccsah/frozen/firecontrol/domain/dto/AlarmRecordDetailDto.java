package ccsah.frozen.firecontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 11:43
 * DESC
 */
@Getter
@Setter
public class AlarmRecordDetailDto {

    private String alarmRecordId;

    private String deviceId;

    private String deviceCode;

    private String alarmContent;

    private long alarmTime;

    private String alarmReceiverId;

    private String alarmReceiverName;

    private long alarmReceiveTime;

    private String confirmedPersonId;

    private String confirmedPersonName;

    private String confirmedResult;

    private long confirmedTime;

    private String eventScene;

    public AlarmRecordDetailDto(String alarmRecordId,
                                String deviceId,
                                String deviceCode,
                                String alarmContent,
                                long alarmTime,
                                String alarmReceiverId,
                                String alarmReceiverName,
                                long alarmReceiveTime,
                                String confirmedPersonId,
                                String confirmedPersonName,
                                String confirmedResult,
                                long confirmedTime,
                                String eventScene) {
        this.setAlarmRecordId(alarmRecordId);
        this.setDeviceId(deviceId);
        this.setDeviceCode(deviceCode);
        this.setAlarmContent(alarmContent);
        this.setAlarmTime(alarmTime);
        this.setAlarmReceiverId(alarmReceiverId);
        this.setAlarmReceiverName(alarmReceiverName);
        this.setAlarmReceiveTime(alarmReceiveTime);
        this.setConfirmedPersonId(confirmedPersonId);
        this.setConfirmedPersonName(confirmedPersonName);
        this.setConfirmedResult(confirmedResult);
        this.setConfirmedTime(confirmedTime);
        this.setEventScene(eventScene);
    }

}
