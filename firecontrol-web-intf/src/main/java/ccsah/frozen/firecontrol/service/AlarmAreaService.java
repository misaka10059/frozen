package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.domain.dto.AlarmAreaDto;
import ccsah.frozen.firecontrol.domain.dto.AlarmAreaTreeDto;
import ccsah.frozen.iot.domain.dto.AreaDto;

import java.util.List;

public interface AlarmAreaService {

    AlarmAreaDto addAlarmArea(String areaCode, String areaName, String parentId);

    AlarmAreaTreeDto deleteAlarmArea(String alarmAreaId);

    AlarmAreaDto updateAlarmArea(String alarmAreaId, String areaName, String parentId);

    List<AlarmAreaDto> listAlarmAreaByTopNode();

    AlarmAreaTreeDto listAlarmAreaTreeById(String alarmAreaId, int depth);

    List<AreaDto> listAreaCompareOnTopNode();

    List<AreaDto> listAreaCanBeAdded(String alarmAreaId);
}
