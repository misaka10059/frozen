package ccsah.frozen.firecontrol.domain.entity;

import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 18:49
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class InspectionPointTemplate extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private InspectionTaskTemplate inspectionTaskTemplate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Device device;

    public static InspectionPointTemplate create(InspectionTaskTemplate inspectionTaskTemplate, Device device) {
        InspectionPointTemplate inspectionPointTemplate = new InspectionPointTemplate();
        inspectionPointTemplate.setInspectionTaskTemplate(inspectionTaskTemplate);
        inspectionPointTemplate.setDevice(device);
        session().persist(inspectionPointTemplate);
        return inspectionPointTemplate;
    }
}
