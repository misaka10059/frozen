package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.AreaDto;
import ccsah.frozen.iot.domain.dto.AreaListParentDto;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 11:05
 * DESC 地区服务
 */
public interface AreaService {

    AreaDto addArea(String areaCode, String areaName, String parentId);

    AreaDto deleteArea(String areaId);

    AreaDto update(String id, String areaName, String parentId);

    List<AreaDto> listAreaByTopNode();

    List<AreaDto> listAreaByAreaName(String areaName);

    List<AreaDto> listAreaByParentAreaCode(String areaCode);

    AreaDto listAreaTreeById(String id, int depth);

    AreaListParentDto listAreaSub(String areaId);

    AreaDto listDeviceByArea(String id);

}
