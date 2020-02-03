package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/16 14:38
 * DESC 用于显示DeviceType列表信息的数据传输对象
 */
@Getter
@Setter
public class DeviceTypeListDto {

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

    public DeviceTypeListDto(String deviceTypeId,
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
