package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 11:29
 * DESC 用于显示DeviceType详细信息的数据传输对象
 */
@Getter
@Setter
public class DeviceTypeDto {

    private String deviceTypeId;

    private String productName;

    private String productType;

    private String abbreviation;

    private String vendorId;

    private String vendorName;

    private String dataSourceId;

    private String sourceName;

    private String functionGroupId;

    private String groupName;

    public DeviceTypeDto(String deviceTypeId,
                         String productName,
                         String productType,
                         String abbreviation,
                         String vendorId,
                         String vendorName,
                         String dataSourceId,
                         String sourceName,
                         String functionGroupId,
                         String groupName) {
        this.setDeviceTypeId(deviceTypeId);
        this.setProductName(productName);
        this.setProductType(productType);
        this.setAbbreviation(abbreviation);
        this.setVendorId(vendorId);
        this.setVendorName(vendorName);
        this.setDataSourceId(dataSourceId);
        this.setSourceName(sourceName);
        this.setFunctionGroupId(functionGroupId);
        this.setGroupName(groupName);
    }
}
