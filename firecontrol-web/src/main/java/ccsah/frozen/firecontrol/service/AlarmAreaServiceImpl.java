package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.domain.dao.AlarmAreaDao;
import ccsah.frozen.firecontrol.domain.dto.AlarmAreaDto;
import ccsah.frozen.firecontrol.domain.dto.AlarmAreaTreeDto;
import ccsah.frozen.firecontrol.domain.entity.AlarmArea;
import ccsah.frozen.iot.domain.dto.AreaDto;
import ccsah.frozen.iot.service.AreaService;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 18:56
 * DESC 报警区域
 */
@Service
public class AlarmAreaServiceImpl implements AlarmAreaService {

    @Autowired
    private AlarmAreaDao alarmAreaDao;

    @Resource
    private AreaService areaService;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/23 8:41
     * DESC 添加报警区域
     */
    @Override
    @Transactional
    public AlarmAreaDto addAlarmArea(String areaCode, String areaName, String parentId) {
        if (getAlarmAreaByNameAndParentId(areaName, parentId) != null) {
            throw new ServiceException(550, ExceptionCode.ALARM_AREA550);
        }
        AlarmArea alarmArea = AlarmArea.create(areaCode, areaName, parentId);
        return getAlarmAreaDto(alarmArea);
    }

    /**
     * DATE 2019/12/23 9:05
     * DESC 删除报警区域
     */
    @Override
    @Transactional
    public AlarmAreaTreeDto deleteAlarmArea(String alarmAreaId) {
        AlarmArea alarmArea = alarmAreaDao.findAlarmAreaByIdAndIsDeletedFalse(alarmAreaId);
        alarmArea.deleteLogical();
        List<AlarmArea> alarmAreaList = getAlarmAreaListByParentId(alarmAreaId);
        if (alarmAreaList.isEmpty()) {
            return getAlarmAreaTreeDto(alarmArea);
        }
        List<AlarmAreaTreeDto> alarmAreaTreeDtoList = alarmAreaList.stream()
                .map(a -> deleteAlarmArea(a.getId()))
                .collect(Collectors.toList());
        return getAlarmAreaTreeDto(alarmArea, alarmAreaTreeDtoList);
    }

    /**
     * DATE 2019/12/24 9:17
     * DESC 更新报警区域
     */
    @Override
    @Transactional
    public AlarmAreaDto updateAlarmArea(String alarmAreaId, String areaName, String parentId) {
        AlarmArea alarmArea = baseService.getAlarmAreaById(alarmAreaId);
        alarmArea.setAreaName(areaName);
        alarmArea.setParentId(parentId);
        return getAlarmAreaDto(alarmArea);
    }

    /**
     * DATE 2019/12/24 9:11
     * DESC 列出顶层节点下拥有的节点列表
     */
    @Override
    public List<AlarmAreaDto> listAlarmAreaByTopNode() {
        return getAlarmAreaDto(getAlarmAreaListByParentId(BaseString.baseId));
    }

    /**
     * DATE 2019/12/23 8:59
     * DESC 展开指定AlarmArea的层级结构，depth指定层级
     */
    @Override
    public AlarmAreaTreeDto listAlarmAreaTreeById(String alarmAreaId, int depth) {
        AlarmArea alarmArea = baseService.getAlarmAreaById(alarmAreaId);
        List<AlarmArea> alarmAreaList = getAlarmAreaListByParentId(alarmAreaId);
        if (depth <= 1 || alarmAreaList.isEmpty()) {
            return getAlarmAreaTreeDto(alarmArea);
        }
        List<AlarmAreaTreeDto> alarmAreaTreeDtoList = alarmAreaList.stream()
                .map(a -> listAlarmAreaTreeById(a.getId(), depth - 1))
                .collect(Collectors.toList());
        return getAlarmAreaTreeDto(alarmArea, alarmAreaTreeDtoList);
    }

    /**
     * DATE 2019/12/24 9:29
     * DESC 列出顶层节点下可添加的AlarmArea列表
     */
    @Override
    public List<AreaDto> listAreaCompareOnTopNode() {
        List<AreaDto> areaDtoList = areaService.listAreaByTopNode();
        List<AlarmAreaDto> alarmAreaDtoList = listAlarmAreaByTopNode();
        return getNewAreaDtoList(areaDtoList, alarmAreaDtoList);
    }

    /**
     * DATE 2019/12/24 9:06
     * DESC 获取指定节点下可添加的报警区域
     */
    @Override
    public List<AreaDto> listAreaCanBeAdded(String alarmAreaId) {
        AlarmArea alarmArea = baseService.getAlarmAreaById(alarmAreaId);
        List<AreaDto> areaDtoList = areaService.listAreaByParentAreaCode(alarmArea.getAreaCode());
        List<AlarmAreaDto> alarmAreaDtoList = getAlarmAreaDto(getAlarmAreaListByParentId(alarmAreaId));
        return getNewAreaDtoList(areaDtoList, alarmAreaDtoList);
    }

    /**
     * DATE 2019/12/24 9:29
     * DESC
     */
    private List<AreaDto> getNewAreaDtoList(List<AreaDto> areaDtoList, List<AlarmAreaDto> alarmAreaDtoList) {
        List<AreaDto> newList = new ArrayList<>();
        for (AreaDto areaDto : areaDtoList) {
            boolean state = false;
            for (AlarmAreaDto alarmAreaDto : alarmAreaDtoList) {
                if (areaDto.getAreaCode().equals(alarmAreaDto.getAreaCode())) {
                    state = true;
                }
            }
            if (!state) {
                newList.add(areaDto);
            }
        }
        return newList;
    }

    /**
     * DATE 2019/12/24 10:06
     * DESC
     */
    private AlarmArea getAlarmAreaByNameAndParentId(String areaName, String parentId) {
        return alarmAreaDao.findAlarmAreaByAreaNameAndParentIdAndIsDeletedFalse(areaName, parentId);
    }

    /**
     * DATE 2019/12/23 8:49
     * DESC
     */
    private List<AlarmArea> getAlarmAreaListByParentId(String parentId) {
        return alarmAreaDao.findAlarmAreasByParentIdAndIsDeletedFalse(parentId);
    }

    private AlarmAreaDto getAlarmAreaDto(AlarmArea alarmArea) {
        return new AlarmAreaDto(
                alarmArea.getId(),
                alarmArea.getAreaCode(),
                alarmArea.getAreaName(),
                alarmArea.getParentId());
    }

    private List<AlarmAreaDto> getAlarmAreaDto(List<AlarmArea> alarmAreaList) {
        return alarmAreaList.stream().map(this::getAlarmAreaDto).collect(Collectors.toList());
    }

    private AlarmAreaTreeDto getAlarmAreaTreeDto(AlarmArea alarmArea) {
        return new AlarmAreaTreeDto(
                alarmArea.getId(),
                alarmArea.getAreaCode(),
                alarmArea.getAreaName(),
                alarmArea.getParentId());
    }

    private AlarmAreaTreeDto getAlarmAreaTreeDto(AlarmArea alarmArea, List<AlarmAreaTreeDto> alarmAreaTreeDtoList) {
        return new AlarmAreaTreeDto(
                alarmArea.getId(),
                alarmArea.getAreaCode(),
                alarmArea.getAreaName(),
                alarmArea.getParentId(),
                alarmAreaTreeDtoList);
    }
}