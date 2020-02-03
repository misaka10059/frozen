package ccsah.frozen.iot.domain.dto.vendor;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/11 10:36
 * DESC 用于显示Vendor列表信息的数据传输对象
 */
@Getter
@Setter
public class VendorSimpleDto {

    private String vendorId;

    private String vendorName;

    public VendorSimpleDto(String vendorId, String vendorName) {
        this.setVendorId(vendorId);
        this.setVendorName(vendorName);
    }
}
