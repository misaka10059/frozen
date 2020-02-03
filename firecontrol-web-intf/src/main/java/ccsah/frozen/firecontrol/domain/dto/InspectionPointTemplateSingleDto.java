package ccsah.frozen.firecontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/25 16:48
 * DESC
 */
@Getter
@Setter
public class InspectionPointTemplateSingleDto {

    private String pointTemplateId;

    private String taskTemplateId;

    private String deviceId;

    private String deviceCode;

    public InspectionPointTemplateSingleDto(String pointTemplateId,
                                            String taskTemplateId,
                                            String deviceId,
                                            String deviceCode) {
        this.setPointTemplateId(pointTemplateId);
        this.setTaskTemplateId(taskTemplateId);
        this.setDeviceId(deviceId);
        this.setDeviceCode(deviceCode);
    }
}