package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.domain.dto.EquipmentDto;
import ccsah.frozen.firecontrol.domain.dto.SmokeDetectorDto;
import ccsah.frozen.firecontrol.domain.knowledge.DeviceState;
import ccsah.frozen.iot.domain.dto.device.DeviceListDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipmentService {

    EquipmentDto addEquipment(String deviceCode, String alarmAreaId, DeviceState deviceState);

    EquipmentDto deleteEquipment(String equipmentId);

    EquipmentDto updateEquipmentState(String deviceCode, DeviceState deviceState);

    SmokeDetectorDto getSmokeDetectorDetail(String deviceCode);

    PageData<EquipmentDto> listEquipmentByParameter(String deviceCode,
                                                    DeviceState deviceState,
                                                    String alarmAreaId,
                                                    long startQueryTime,
                                                    long endQueryTime,
                                                    Pageable pageRequest);

    List<DeviceListDto> listEquipmentCanBeAddedByAlarmAreaId(String alarmAreaId);

}
