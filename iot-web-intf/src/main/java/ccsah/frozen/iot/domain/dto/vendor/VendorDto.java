package ccsah.frozen.iot.domain.dto.vendor;

import ccsah.frozen.iot.domain.dto.contact.ContactDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 11:40
 * DESC 用于显示Vendor详细信息的数据传输对象
 */
@Getter
@Setter
public class VendorDto {

    private String vendorId;

    private String vendorName;

    private String vendorAddress;

    private List<ContactDto> contactDtoList;

    public VendorDto(String vendorId, String vendorName, String vendorAddress, List<ContactDto> contactDtoList) {
        this.setVendorId(vendorId);
        this.setVendorName(vendorName);
        this.setVendorAddress(vendorAddress);
        this.setContactDtoList(contactDtoList);
    }
}
