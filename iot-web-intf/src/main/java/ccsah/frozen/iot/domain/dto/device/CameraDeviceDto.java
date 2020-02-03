package ccsah.frozen.iot.domain.dto.device;

import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/31 11:08
 * DESC
 */
@Getter
@Setter
public class CameraDeviceDto extends DeviceDto {

    private String playAddress;

    public CameraDeviceDto(String deviceId,
                           String deviceCode,
                           String manufacturerCode,
                           String imeiCode,
                           DeviceEnableState enableState,
                           DeviceOnlineState onlineState,
                           String deviceTypeId,
                           String productName,
                           String productType,
                           String vendorId,
                           String vendorName,
                           String dataSourceId,
                           String dataSourceName,
                           String functionGroupId,
                           String functionGroupName,
                           String projectId,
                           String projectCode,
                           String projectName,
                           String departmentId,
                           String departmentName,
                           String areaId,
                           String areaCode,
                           String areaName,
                           String playAddress) {
        super(
                deviceId,
                deviceCode,
                manufacturerCode,
                imeiCode,
                enableState,
                onlineState,
                deviceTypeId,
                productName,
                productType,
                vendorId,
                vendorName,
                dataSourceId,
                dataSourceName,
                functionGroupId,
                functionGroupName,
                projectId,
                projectCode,
                projectName,
                departmentId,
                departmentName,
                areaId,
                areaCode,
                areaName);
        this.setPlayAddress(playAddress);
    }
}
