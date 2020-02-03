package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.firecontrol.domain.knowledge.InspectionTaskState;
import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 11:09
 * DESC
 */
@Getter
@Setter
public class InspectionTaskDto {

    private String inspectionTaskId;

    private String inspectionTaskTemplateId;

    private String inspectorId;

    private String inspectorName;

    private String inspectionTaskNumber;

    private String inspectionContent;

    private InspectionTaskState inspectionTaskState;

    private long inspectionScheduledTime;

    private long inspectionStartTime;

    private long inspectionEndTime;

    private String inspectionResult;

    private String additionalMessage;

    public InspectionTaskDto(String inspectionTaskId,
                             String inspectionTaskTemplateId,
                             String inspectorId,
                             String inspectorName,
                             String inspectionTaskNumber,
                             String inspectionContent,
                             InspectionTaskState inspectionTaskState,
                             long inspectionScheduledTime,
                             long inspectionStartTime,
                             long inspectionEndTime,
                             String inspectionResult,
                             String additionalMessage) {
        this.setInspectionTaskId(inspectionTaskId);
        this.setInspectionTaskTemplateId(inspectionTaskTemplateId);
        this.setInspectorId(inspectorId);
        this.setInspectorName(inspectorName);
        this.setInspectionTaskNumber(inspectionTaskNumber);
        this.setInspectionContent(inspectionContent);
        this.setInspectionTaskState(inspectionTaskState);
        this.setInspectionScheduledTime(inspectionScheduledTime);
        this.setInspectionStartTime(inspectionStartTime);
        this.setInspectionEndTime(inspectionEndTime);
        this.setInspectionResult(inspectionResult);
        this.setAdditionalMessage(additionalMessage);
    }
}
