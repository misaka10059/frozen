package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.dao.AreaDao;
import ccsah.frozen.iot.domain.dto.AreaDto;
import ccsah.frozen.iot.domain.dto.AreaListParentDto;
import ccsah.frozen.iot.domain.dto.device.DeviceListDto;
import ccsah.frozen.iot.domain.entity.Area;
import ccsah.frozen.iot.domain.entity.Device;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 11:05
 * DESC 地区服务
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    BaseService baseService;

    /**
     * DATE 2019/12/6 11:10
     * DESC 添加Area
     */
    @Override
    @Transactional
    public AreaDto addArea(String areaCode, String areaName, String parentId) {
        parentId = baseService.getParentIdOnArea(parentId);
        if (getAreaByAreaNameAndParentId(areaName, parentId) != null) {
            throw new ServiceException(540, ExceptionCode.AREA540);
        }
        Area area = Area.create(areaCode, areaName, parentId);
        return getAreaDto(area);
    }

    /**
     * DATE 2019/12/11 15:50
     * DESC 删除指定id的Area及其下属所有Area
     */
    @Override
    @Transactional
    public AreaDto deleteArea(String areaId) {
        Area area = baseService.getAreaById(areaId);
        area.deleteLogical();
        List<Area> areaList = getAreaListByParentId(areaId);
        if (areaList.isEmpty()) {
            return getAreaDto(area);
        }
        List<AreaDto> areaDtoList = areaList.stream()
                .map(a -> deleteArea(a.getId()))
                .collect(Collectors.toList());
        return getAreaDto(area, areaDtoList, null);
    }

    /**
     * DATE 2019/12/10 11:38
     * DESC 更新Area信息
     */
    @Override
    @Transactional
    public AreaDto update(String id, String areaName, String parentId) {
        Area area = baseService.getAreaById(id);
        area.setAreaName(areaName);
        area.setParentId(baseService.getParentIdOnArea(parentId));
        return getAreaDto(area);
    }

    /**
     * DATE 2019/12/10 11:42
     * DESC 获取顶层节点拥有的节点列表
     */
    @Override
    public List<AreaDto> listAreaByTopNode() {
        return getAreaDto(getAreaListByParentId(BaseString.baseId));
    }

    /**
     * DATE 2019/12/10 11:34
     * DESC 通过area_name模糊查询Area
     */
    @Override
    public List<AreaDto> listAreaByAreaName(String areaName) {
        return getAreaDto(getAreaListByAreaNameLike(areaName));
    }

    /**
     * DATE 2019/12/20 16:51
     * DESC 查询AreaCode对应的地区作为父级地区的所有子级地区列表
     */
    @Override
    public List<AreaDto> listAreaByParentAreaCode(String areaCode) {
        String parentId = areaDao.findAreaByAreaCodeAndIsDeletedFalse(areaCode).getId();
        return getAreaDto(getAreaListByParentId(parentId));
    }

    /**
     * DATE 2019/12/9 11:21
     * DESC 获取指定Area及下属所有Area记录，并使用depth指定展开层级
     */
    @Override
    public AreaDto listAreaTreeById(String id, int depth) {
        Area area = baseService.getAreaById(id);
        List<Area> areaList = getAreaListByParentId(id);
        if (depth <= 1 || areaList.isEmpty()) {
            return getAreaDto(area);
        }
        List<AreaDto> areaDtoList = areaList.stream()
                .map(a -> listAreaTreeById(a.getId(), depth - 1))
                .collect(Collectors.toList());
        return getAreaDto(area, areaDtoList, null);
    }

    /**
     * DATE 2019/12/26 16:50
     * DESC 根据当前节点查询当前节点至顶层节点之间的各个节点
     */
    private AreaListParentDto listAreaParent(String areaId) {
        Area area = baseService.getAreaById(areaId);
        if (area.getParentId().equals(BaseString.baseId)) {
            return getAreaListParentDto(area);
        }
        AreaListParentDto newDto = listAreaParent(area.getParentId());
        return new AreaListParentDto(
                area.getId(),
                area.getParentId(),
                area.getAreaCode(),
                area.getAreaName(),
                newDto);
    }

    /**
     * DATE 2019/12/26 17:29
     * DESC 反转查询listAreaParent的节点列表，使层级结构为由大到小
     */
    @Override
    public AreaListParentDto listAreaSub(String areaId) {
        AreaListParentDto parentDto = listAreaParent(areaId);
        AreaListParentDto prev = null;
        AreaListParentDto now = parentDto;
        while (now != null) {
            AreaListParentDto next = now.getAreaDto();
            now.setAreaDto(prev);
            prev = now;
            now = next;
        }
        return prev;
    }

    /**
     * DATE 2019/12/9 16:22
     * DESC 获取指定Area及下属所有Area记录及每个Area对应的DeviceList
     */
    @Override
    public AreaDto listDeviceByArea(String id) {
        Area area = baseService.getAreaById(id);
        List<Area> areaList = getAreaListByParentId(id);
        List<Device> deviceList = baseService.getDeviceListByArea(baseService.getAreaById(id));
        if (areaList.isEmpty()) {
            return getAreaDto(area, null, baseService.getDeviceListDto(deviceList));
        }
        List<AreaDto> areaDtoList = areaList.stream()
                .map(a -> listDeviceByArea(a.getId()))
                .collect(Collectors.toList());
        return getAreaDto(area, areaDtoList, baseService.getDeviceListDto(deviceList));
    }

    /**
     * DATE 2019/12/16 10:19
     * DESC
     */
    private Area getAreaByAreaNameAndParentId(String areaName, String parentId) {
        return areaDao.findAreaByAreaNameAndParentIdAndIsDeletedFalse(areaName, parentId);
    }

    /**
     * DATE 2019/12/16 10:22
     * DESC
     */
    private List<Area> getAreaListByAreaNameLike(String areaName) {
        return areaDao.findAreasByAreaNameLikeAndIsDeletedFalse("%" + areaName + "%");
    }

    /**
     * DATE 2019/12/16 10:15
     * DESC
     */
    private List<Area> getAreaListByParentId(String parentId) {
        return areaDao.findAreasByParentIdAndIsDeletedFalse(parentId);
    }

    private AreaDto getAreaDto(Area area) {
        return new AreaDto(area.getId(), area.getParentId(), area.getAreaCode(), area.getAreaName());
    }

    private List<AreaDto> getAreaDto(List<Area> areaList) {
        return areaList.stream()
                .map(this::getAreaDto)
                .collect(Collectors.toList());
    }

    private AreaDto getAreaDto(Area area, List<AreaDto> areaDtoList, List<DeviceListDto> deviceListDtoList) {
        return new AreaDto(area.getId(), area.getParentId(), area.getAreaCode(), area.getAreaName(), areaDtoList, deviceListDtoList);
    }

    private AreaListParentDto getAreaListParentDto(Area area) {
        return new AreaListParentDto(
                area.getId(),
                area.getParentId(),
                area.getAreaCode(),
                area.getAreaName());
    }
}
