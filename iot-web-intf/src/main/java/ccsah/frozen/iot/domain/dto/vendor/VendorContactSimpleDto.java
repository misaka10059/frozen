package ccsah.frozen.iot.domain.dto.vendor;

import ccsah.frozen.iot.domain.dto.contact.ContactSimpleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/11 15:23
 * DESC 用于显示Vendor和对应的Contact列表信息的数据传输对象
 */
@Getter
@Setter
public class VendorContactSimpleDto {

    private String vendorId;

    private String vendorName;

    private String vendorAddress;

    private List<ContactSimpleDto> contactSimpleDtoList;

    public VendorContactSimpleDto(String vendorId, String vendorName, String vendorAddress, List<ContactSimpleDto> contactSimpleDtoList) {
        this.setVendorId(vendorId);
        this.setVendorName(vendorName);
        this.setVendorAddress(vendorAddress);
        this.setContactSimpleDtoList(contactSimpleDtoList);
    }
}
