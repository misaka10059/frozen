package ccsah.frozen.firecontrol.domain.dto;

import ccsah.frozen.firecontrol.domain.knowledge.InspectionTaskState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 11:32
 * DESC
 */
@Getter
@Setter
public class InspectionTaskDetailDto {

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

    List<InspectionPointDto> pointDtoList;

    public InspectionTaskDetailDto(String inspectionTaskId,
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
                                   String additionalMessage,
                                   List<InspectionPointDto> pointDtoList) {
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
        this.setPointDtoList(pointDtoList);
    }
}
