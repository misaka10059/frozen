package ccsah.frozen.iot.domain.dto.contact;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 11:37
 * DESC 用于显示Contact详细信息的数据传输对象
 */
@Getter
@Setter
public class ContactDto {

    private String contactId;

    private String contactName;

    private String contactGender;

    private String contactNumber;

    private String contactEmail;

    private String wechatNumber;

    private String vendorId;

    private String vendorName;

    public ContactDto(String contactId,
                      String contactName,
                      String contactGender,
                      String contactNumber,
                      String contactEmail,
                      String wechatNumber,
                      String vendorId,
                      String vendorName) {
        this.setContactId(contactId);
        this.setContactName(contactName);
        this.setContactGender(contactGender);
        this.setContactNumber(contactNumber);
        this.setContactEmail(contactEmail);
        this.setWechatNumber(wechatNumber);
        this.setVendorId(vendorId);
        this.setVendorName(vendorName);
    }
}
