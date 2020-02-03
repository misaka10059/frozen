package ccsah.frozen.iot.domain.dto.device;

import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 14:02
 * DESC 用于显示Device详细信息的数据传输对象
 */
@Getter
@Setter
public class DeviceDto implements Serializable {

    private String deviceId;

    private String deviceCode;

    private String manufacturerCode;

    private String imeiCode;

    private DeviceEnableState enableState;

    private DeviceOnlineState onlineState;

    private String deviceTypeId;

    private String productName;

    private String productType;

    private String vendorId;

    private String vendorName;

    private String dataSourceId;

    private String dataSourceName;

    private String functionGroupId;

    private String functionGroupName;

    private String projectId;

    private String projectCode;

    private String projectName;

    private String departmentId;

    private String departmentName;

    private String areaId;

    private String areaCode;

    private String areaName;

    public DeviceDto(String deviceId,
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
                     String areaName) {
        this.setDeviceId(deviceId);
        this.setDeviceCode(deviceCode);
        this.setManufacturerCode(manufacturerCode);
        this.setImeiCode(imeiCode);
        this.setEnableState(enableState);
        this.setOnlineState(onlineState);
        this.setDeviceTypeId(deviceTypeId);
        this.setProductName(productName);
        this.setProductType(productType);
        this.setVendorId(vendorId);
        this.setVendorName(vendorName);
        this.setDataSourceId(dataSourceId);
        this.setDataSourceName(dataSourceName);
        this.setFunctionGroupId(functionGroupId);
        this.setFunctionGroupName(functionGroupName);
        this.setProjectId(projectId);
        this.setProjectCode(projectCode);
        this.setProjectName(projectName);
        this.setDepartmentId(departmentId);
        this.setDepartmentName(departmentName);
        this.setAreaId(areaId);
        this.setAreaCode(areaCode);
        this.setAreaName(areaName);
    }
}
