package ccsah.frozen.iot.domain.dto.contact;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/11 15:15
 * DESC 用于显示Contact列表信息的数据传输对象
 */
@Getter
@Setter
public class ContactSimpleDto {

    private String contactId;

    private String contactName;

    private String contactPhoneNumber;

    public ContactSimpleDto(String contactId, String contactName, String contactPhoneNumber) {
        this.setContactId(contactId);
        this.setContactName(contactName);
        this.setContactPhoneNumber(contactPhoneNumber);
    }
}
