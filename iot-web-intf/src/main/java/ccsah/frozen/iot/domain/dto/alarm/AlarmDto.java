package ccsah.frozen.iot.domain.dto.alarm;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 14:52
 * DESC
 */
@Getter
@Setter
public class AlarmDto {

    private String deviceCode; //TODO 之后AlarmDot中deviceCode应修改为imeiCode

    private String alarmContent;

    private long alarmTime;

}
