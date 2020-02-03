package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.device.CameraDeviceDto;
import ccsah.frozen.iot.domain.dto.device.DeviceDto;
import ccsah.frozen.iot.domain.dto.device.DeviceListDto;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableStateQuery;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineStateQuery;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 14:02
 * DESC 设备服务
 */
public interface DeviceService {

    DeviceDto addDevice(String manufacturerCode,
                        String imeiCode,
                        DeviceEnableState enableState,
                        DeviceOnlineState onlineState,
                        String deviceTypeId,
                        String projectId,
                        String departmentId,
                        String areaId);

    DeviceDto deleteDevice(String deviceId);

    DeviceDto updateDevice(String deviceId,
                           String manufacturerCode,
                           String imeiCode,
                           DeviceEnableState enableState,
                           DeviceOnlineState onlineState,
                           String deviceTypeId,
                           String projectId,
                           String departmentId,
                           String areaId);

    DeviceDto getDeviceDetailById(String deviceId);

    DeviceDto getDeviceDetailByDeviceCode(String deviceCode);

    CameraDeviceDto getDeviceCameraDtoById(String deviceId);

    PageData<DeviceListDto> listDeviceByParameter(String deviceCode,
                                                  String manufacturerCode,
                                                  String imeiCode,
                                                  DeviceEnableStateQuery enableStateQuery,
                                                  DeviceOnlineStateQuery onlineStateQuery,
                                                  long startQueryTime,
                                                  long endQueryTime,
                                                  String deviceTypeId,
                                                  String projectId,
                                                  String departmentId,
                                                  String areaId,
                                                  Pageable pageRequest);

    List<DeviceListDto> listDeviceByAreaCode(String areaCode);

}