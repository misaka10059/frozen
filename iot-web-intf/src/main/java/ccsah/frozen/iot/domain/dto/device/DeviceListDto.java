package ccsah.frozen.iot.domain.dto.device;

import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/16 14:12
 * DESC 用于显示Device列表信息的数据传输对象
 */
@Getter
@Setter
public class DeviceListDto implements Serializable {

    private String deviceId;

    private String deviceCode;

    private DeviceEnableState enableState;

    private DeviceOnlineState onlineState;

    private String deviceTypeId;

    private String productName;

    private String dataSourceId;

    private String dataSourceName;

    private String functionGroupId;

    private String functionGroupName;

    private String projectId;

    private String projectName;

    private String departmentId;

    private String departmentName;

    private String areaId;

    private String areaName;

    public DeviceListDto(String deviceId,
                         String deviceCode,
                         DeviceEnableState enableState,
                         DeviceOnlineState onlineState,
                         String deviceTypeId,
                         String productName,
                         String dataSourceId,
                         String dataSourceName,
                         String functionGroupId,
                         String functionGroupName,
                         String projectId,
                         String projectName,
                         String departmentId,
                         String departmentName,
                         String areaId,
                         String areaName) {
        this.setDeviceId(deviceId);
        this.setDeviceCode(deviceCode);
        this.setEnableState(enableState);
        this.setOnlineState(onlineState);
        this.setDeviceTypeId(deviceTypeId);
        this.setProductName(productName);
        this.setDataSourceId(dataSourceId);
        this.setDataSourceName(dataSourceName);
        this.setFunctionGroupId(functionGroupId);
        this.setFunctionGroupName(functionGroupName);
        this.setProjectId(projectId);
        this.setProjectName(projectName);
        this.setDepartmentId(departmentId);
        this.setDepartmentName(departmentName);
        this.setAreaId(areaId);
        this.setAreaName(areaName);
    }

}
