package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/19 10:44
 * DESC
 */
@Getter
@Setter
public class InspectionTaskTemplateDto implements Serializable {

    private String taskTemplateId;

    private TemplateState templateState;

    private String templateNumber;

    private String inspectionContent;

    private FrequencyTypes inspectionFrequency;

    private String additionalFields;

    private int hourTime;

    private int minuteTime;

    private String inspectorId;

    private String inspectorName;

    private List<InspectionPointTemplateSingleDto> pointTemplateSingleDtoList;

    public InspectionTaskTemplateDto(String taskTemplateId,
                                     TemplateState templateState,
                                     String templateNumber,
                                     String inspectionContent,
                                     FrequencyTypes inspectionFrequency,
                                     String additionalFields,
                                     int hourTime,
                                     int minuteTime,
                                     String inspectorId,
                                     String inspectorName) {
        this.setTaskTemplateId(taskTemplateId);
        this.setTemplateState(templateState);
        this.setTemplateNumber(templateNumber);
        this.setInspectionContent(inspectionContent);
        this.setInspectionFrequency(inspectionFrequency);
        this.setAdditionalFields(additionalFields);
        this.setHourTime(hourTime);
        this.setMinuteTime(minuteTime);
        this.setInspectorId(inspectorId);
        this.setInspectorName(inspectorName);
    }

    public InspectionTaskTemplateDto(String taskTemplateId,
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
        this.setTaskTemplateId(taskTemplateId);
        this.setTemplateState(templateState);
        this.setTemplateNumber(templateNumber);
        this.setInspectionContent(inspectionContent);
        this.setInspectionFrequency(inspectionFrequency);
        this.setAdditionalFields(additionalFields);
        this.setHourTime(hourTime);
        this.setMinuteTime(minuteTime);
        this.setInspectorId(inspectorId);
        this.setInspectorName(inspectorName);
        this.setPointTemplateSingleDtoList(pointTemplateSingleDtoList);
    }

}
