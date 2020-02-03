package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 16:41
 * DESC
 */
@Getter
@Setter
public class AreaListParentDto {

    private String areaId;

    private String parentId;

    private String areaCode;

    private String areaName;

    private AreaListParentDto areaDto;

    public AreaListParentDto(String areaId, String parentId, String areaCode, String areaName) {
        this.setAreaId(areaId);
        this.setParentId(parentId);
        this.setAreaCode(areaCode);
        this.setAreaName(areaName);
    }

    public AreaListParentDto(String areaId, String parentId, String areaCode, String areaName, AreaListParentDto areaListParentDto) {
        this.setAreaId(areaId);
        this.setParentId(parentId);
        this.setAreaCode(areaCode);
        this.setAreaName(areaName);
        this.setAreaDto(areaListParentDto);
    }
}
