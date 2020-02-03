package ccsah.frozen.firecontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/20 15:33
 * DESC
 */
@Getter
@Setter
public class AlarmAreaDto implements Serializable {

    private String alarmAreaId;

    private String areaCode;

    private String alarmAreaName;

    private String parentId;

    public AlarmAreaDto(String alarmAreaId, String areaCode, String alarmAreaName, String parentId) {
        this.setAlarmAreaId(alarmAreaId);
        this.setAreaCode(areaCode);
        this.setAlarmAreaName(alarmAreaName);
        this.setParentId(parentId);
    }

}
