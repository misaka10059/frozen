package ccsah.frozen.iot.domain.dto.alarm;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/30 17:48
 * DESC
 */
@Getter
@Setter
public class AlarmDetailDto {

    private String deviceCode;

    private String alarmContent; //报警内容的编码串，解码后为以下3个字段的信息

    private String fireState; //火警状态：TURN_ALARM表示发生火警，RETURN_TO_NORMAL_WORK表示恢复正常

    private String baseVoltage; //底座电压,单位毫伏

    private String signalIntensity; //信号强度

    private long alarmTime;  //报警时间
}
