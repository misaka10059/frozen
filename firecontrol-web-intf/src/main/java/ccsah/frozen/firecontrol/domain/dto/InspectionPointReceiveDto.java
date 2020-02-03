package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.firecontrol.domain.knowledge.InspectionPointState;
import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2020/1/2 11:25
 * DESC 用于接收NFC设备发送来的巡检数据信息
 */
@Getter
@Setter
public class InspectionPointReceiveDto {

    private String deviceCode;

    private String inspectorId;

    private InspectionPointState inspectionPointState;

}
