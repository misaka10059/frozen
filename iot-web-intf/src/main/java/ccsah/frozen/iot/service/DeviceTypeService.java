package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.DeviceTypeDto;
import ccsah.frozen.iot.domain.dto.DeviceTypeListDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 11:29
 * DESC 设备类型服务
 */
public interface DeviceTypeService {

    DeviceTypeDto addDeviceType(String productName,
                                String productType,
                                String abbreviation,
                                String vendorId,
                                String dataSourceId,
                                String functionGroupId);

    DeviceTypeDto deleteDeviceType(String id);

    DeviceTypeDto updateDeviceType(String id,
                                   String productName,
                                   String productType,
                                   String abbreviation,
                                   String vendorId,
                                   String dataSourceId,
                                   String functionGroupId);

    DeviceTypeDto getDeviceTypeDetailById(String deviceId);

    PageData<DeviceTypeListDto> listDeviceTypeByParameter(String productName,
                                                          String productType,
                                                          String abbreviation,
                                                          String vendorId,
                                                          String dataSourceId,
                                                          String functionGroupId,
                                                          long startQueryTime,
                                                          long endQueryTime,
                                                          Pageable pageRequest);

}
