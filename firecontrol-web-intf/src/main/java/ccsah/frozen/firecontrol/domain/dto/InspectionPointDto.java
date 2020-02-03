package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.firecontrol.domain.knowledge.InspectionPointState;
import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 11:33
 * DESC
 */
@Getter
@Setter
public class InspectionPointDto {

    private String inspectionPointId;

    private String inspectionTaskId;

    private String deviceId;

    private String deviceCode;

    private InspectionPointState inspectionPointState;

    private long inspectionTime;

    public InspectionPointDto(String inspectionPointId,
                              String inspectionTaskId,
                              String deviceId,
                              String deviceCode,
                              InspectionPointState inspectionPointState,
                              long inspectionTime) {
        this.setInspectionPointId(inspectionPointId);
        this.setInspectionTaskId(inspectionTaskId);
        this.setDeviceId(deviceId);
        this.setDeviceCode(deviceCode);
        this.setInspectionPointState(inspectionPointState);
        this.setInspectionTime(inspectionTime);
    }

}
