package ccsah.frozen.iot.domain.entity;

import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 10:26
 * DESC
 */
@Entity
@Table(name = "contact")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Contact extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Vendor vendor;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "wechat")
    private String wechat;

    public static Contact create(Vendor vendor, String contactName, String contactGender, String contactNumber, String contactEmail, String wechatNumber) {
        Contact contact = new Contact();
        contact.setVendor(vendor);
        contact.setContactName(contactName);
        contact.setGender(contactGender);
        contact.setPhoneNumber(contactNumber);
        contact.setEmail(contactEmail);
        contact.setWechat(wechatNumber);
        session().persist(contact);
        return contact;
    }
}
