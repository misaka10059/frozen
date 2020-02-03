package ccsah.frozen.firecontrol.domain.entity;

import ccsah.frozen.firecontrol.domain.knowledge.InspectionTaskState;
import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 18:15
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class InspectionTask extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private InspectionTaskTemplate inspectionTaskTemplate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Employee inspector;

    @Column
    private String inspectionTaskNumber;

    @Column
    private String inspectionContent;

    @Column(name = "inspection_state")
    @Enumerated(EnumType.STRING)
    private InspectionTaskState inspectionTaskState;

    @Column
    private Timestamp inspectionScheduledTime;

    @Column
    private Timestamp inspectionStartTime;

    @Column
    private Timestamp inspectionEndTime;

    @Column
    private String inspectionResult;

    @Column
    private String additionalMessage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "inspectionTask")
    private List<InspectionPoint> inspectionPointList;

    public static InspectionTask create(InspectionTaskTemplate taskTemplate,
                                        Employee inspector,
                                        String inspectionTaskNumber,
                                        String inspectionContent,
                                        InspectionTaskState inspectionTaskState,
                                        Timestamp inspectionScheduledTime,
                                        Timestamp inspectionStartTime,
                                        Timestamp inspectionEndTime,
                                        String inspectionResult,
                                        String additionalMessage) {
        InspectionTask inspectionTask = new InspectionTask();
        inspectionTask.setInspectionTaskTemplate(taskTemplate);
        inspectionTask.setInspector(inspector);
        inspectionTask.setInspectionTaskNumber(inspectionTaskNumber);
        inspectionTask.setInspectionContent(inspectionContent);
        inspectionTask.setInspectionTaskState(inspectionTaskState);
        inspectionTask.setInspectionScheduledTime(inspectionScheduledTime);
        inspectionTask.setInspectionStartTime(inspectionStartTime);
        inspectionTask.setInspectionEndTime(inspectionEndTime);
        inspectionTask.setInspectionResult(inspectionResult);
        inspectionTask.setAdditionalMessage(additionalMessage);
        session().persist(inspectionTask);
        return inspectionTask;
    }
}
