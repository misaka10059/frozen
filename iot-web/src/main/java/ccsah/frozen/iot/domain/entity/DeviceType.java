package ccsah.frozen.iot.domain.entity;

import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 10:45
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class DeviceType extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Vendor vendor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private DataSource dataSource;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private FunctionGroup functionGroup;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_type")
    private String productType;

    @Column
    private String abbreviation;

    public static DeviceType create(String productName,
                                    String productType,
                                    String abbreviation,
                                    Vendor vendor,
                                    DataSource dataSource,
                                    FunctionGroup functionGroup) {
        DeviceType deviceType = new DeviceType();
        deviceType.setProductName(productName);
        deviceType.setProductType(productType);
        deviceType.setAbbreviation(abbreviation);
        deviceType.setVendor(vendor);
        deviceType.setDataSource(dataSource);
        deviceType.setFunctionGroup(functionGroup);
        session().persist(deviceType);
        return deviceType;
    }
}
