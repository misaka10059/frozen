package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.firecontrol.domain.knowledge.DeviceState;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/20 10:31
 * DESC
 */
@Getter
@Setter
public class EquipmentDto implements Serializable {

    private String deviceId;

    private String alarmAreaName;

    private String deviceCode;

    private DeviceState deviceState;

    public EquipmentDto(String deviceId, String alarmAreaName, String deviceCode, DeviceState deviceState) {
        this.setDeviceId(deviceId);
        this.setAlarmAreaName(alarmAreaName);
        this.setDeviceCode(deviceCode);
        this.setDeviceState(deviceState);
    }

}
