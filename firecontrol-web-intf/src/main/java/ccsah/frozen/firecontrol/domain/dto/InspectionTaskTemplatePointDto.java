package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2020/1/3 13:04
 * DESC
 */
@Getter
@Setter
public class InspectionTaskTemplatePointDto extends InspectionTaskTemplateDto {

    private List<InspectionPointTemplateSingleDto> pointTemplateSingleDtoList;

    public InspectionTaskTemplatePointDto(String taskTemplateId,
                                          TemplateState templateState,
                                          String templateNumber,
                                          String inspectionContent,
                                          FrequencyTypes inspectionFrequency,
                                          String additionalFields,
                                          int hourTime,
                                          int minuteTime,
                                          String inspectorId,
                                          String inspectorName,
                                          List<InspectionPointTemplateSingleDto> pointTemplateSingleDtoList) {
        super(taskTemplateId,
                templateState,
                templateNumber,
                inspectionContent,
                inspectionFrequency,
                additionalFields,
                hourTime,
                minuteTime,
                inspectorId,
                inspectorName);
        this.setPointTemplateSingleDtoList(pointTemplateSingleDtoList);
    }
}
