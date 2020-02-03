package ccsah.frozen.firecontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2020/1/3 16:56
 * DESC
 */
@Getter
@Setter
public class InspectionPointMessageDto {

    private String deviceId;

    private String deviceCode;

    private String inspectionPointState;

    private String alarmAreaId;

    private String alarmAreaName;
}
