package ccsah.frozen.firecontrol.domain.entity;

import ccsah.frozen.firecontrol.domain.knowledge.InspectionPointState;
import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 18:31
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class InspectionPoint extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private InspectionTask inspectionTask;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Device device;

    @Column(name = "inspection_state")
    @Enumerated(EnumType.STRING)
    private InspectionPointState pointState;

    @Column
    private Timestamp inspectionTime;

    public static InspectionPoint create(InspectionTask inspectionTask, Device device, InspectionPointState pointState, Timestamp inspectionTime) {
        InspectionPoint inspectionPoint = new InspectionPoint();
        inspectionPoint.setInspectionTask(inspectionTask);
        inspectionPoint.setDevice(device);
        inspectionPoint.setPointState(pointState);
        inspectionPoint.setInspectionTime(inspectionTime);
        session().persist(inspectionPoint);
        return inspectionPoint;
    }
}
