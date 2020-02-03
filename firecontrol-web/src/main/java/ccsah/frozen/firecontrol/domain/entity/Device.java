package ccsah.frozen.firecontrol.domain.entity;

import ccsah.frozen.firecontrol.domain.knowledge.DeviceState;
import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 15:56
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
    private AlarmArea alarmArea;

    @Column
    private String deviceCode;

    @Column
    @Enumerated(EnumType.STRING)
    private DeviceState deviceState;

    public static Device create(String deviceCode, AlarmArea alarmArea, DeviceState deviceState) {
        Device device = new Device();
        device.setDeviceCode(deviceCode);
        device.setAlarmArea(alarmArea);
        device.setDeviceState(deviceState);
        session().persist(device);
        return device;
    }
}
