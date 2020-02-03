package ccsah.frozen.iot.domain.entity;

import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 10:54
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Device extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private DeviceType deviceType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Department department;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Area area;

    @Column(name = "device_code")
    private String deviceCode;

    @Column(name = "manufacturer_code")
    private String manufacturerCode;

    @Column(name = "imei_code")
    private String imeiCode;

    @Enumerated(value = EnumType.STRING)
    @Column
    private DeviceEnableState enableState;

    @Enumerated(value = EnumType.STRING)
    @Column
    private DeviceOnlineState onlineState;

    public static Device create(String deviceCode, String manufacturerCode, String imeiCode, DeviceEnableState enableState, DeviceOnlineState onlineState, DeviceType deviceType, Project project, Department department, Area area) {
        Device device = new Device();
        device.setDeviceCode(deviceCode);
        device.setManufacturerCode(manufacturerCode);
        device.setImeiCode(imeiCode);
        device.setEnableState(enableState);
        device.setOnlineState(onlineState);
        device.setDeviceType(deviceType);
        device.setProject(project);
        device.setDepartment(department);
        device.setArea(area);
        session().persist(device);
        return device;
    }
}
