package ccsah.frozen.iot.domain.entity;

import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 10:36
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Vendor extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vendor")
    private List<Contact> contactList;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "address")
    private String address;

    public static Vendor create(String vendorName, String address) {
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        vendor.setAddress(address);
        session().persist(vendor);
        return vendor;
    }
}
