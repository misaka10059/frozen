package ccsah.frozen.firecontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/23 8:43
 * DESC
 */
@Getter
@Setter
public class AlarmAreaTreeDto implements Serializable {

    private String alarmAreaId;

    private String areaCode;

    private String areaName;

    private String parentId;

    private List<AlarmAreaTreeDto> alarmAreaTreeDtoList;

    public AlarmAreaTreeDto(String alarmAreaId, String areaCode, String areaName, String parentId) {
        this.setAlarmAreaId(alarmAreaId);
        this.setAreaCode(areaCode);
        this.setAreaName(areaName);
        this.setParentId(parentId);
    }

    public AlarmAreaTreeDto(String alarmAreaId, String areaCode, String areaName, String parentId, List<AlarmAreaTreeDto> alarmAreaTreeDtoList) {
        this.setAlarmAreaId(alarmAreaId);
        this.setAreaCode(areaCode);
        this.setAreaName(areaName);
        this.setParentId(parentId);
        this.setAlarmAreaTreeDtoList(alarmAreaTreeDtoList);
    }

}
