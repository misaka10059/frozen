package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.iot.domain.dto.device.DeviceDto;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2020/1/7 11:15
 * DESC
 */
@Getter
@Setter
public class SmokeDetectorDto extends DeviceDto {

    private int baseVoltage; //底座电压,单位毫伏

    private int signalIntensity; //信号强度

    public SmokeDetectorDto(String deviceId,
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
                            int baseVoltage,
                            int signalIntensity) {
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
        this.baseVoltage = baseVoltage;
        this.signalIntensity = signalIntensity;
    }
}
