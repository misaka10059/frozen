package ccsah.frozen.iot.domain.dto;

import ccsah.frozen.iot.domain.dto.device.DeviceListDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 11:07
 * DESC
 */
@Getter
@Setter
public class AreaDto implements Serializable {

    private String id;

    private String parentId;

    private String areaCode;

    private String areaName;

    List<AreaDto> areaDtoList;

    List<DeviceListDto> deviceListDtoList;

    public AreaDto(String id, String parentId, String areaCode, String areaName) {
        this.setId(id);
        this.setParentId(parentId);
        this.setAreaCode(areaCode);
        this.setAreaName(areaName);
    }

    public AreaDto(String id, String parentId, String areaCode, String areaName, List<AreaDto> areaDtoList, List<DeviceListDto> deviceListDtoList) {
        this.setId(id);
        this.setParentId(parentId);
        this.setAreaCode(areaCode);
        this.setAreaName(areaName);
        this.setAreaDtoList(areaDtoList);
        this.setDeviceListDtoList(deviceListDtoList);
    }
}
