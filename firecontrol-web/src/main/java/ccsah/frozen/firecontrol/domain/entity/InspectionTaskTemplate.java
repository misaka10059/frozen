package ccsah.frozen.firecontrol.domain.entity;

import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 18:36
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class InspectionTaskTemplate extends BaseEntity {

    @Column(name = "template_state")
    @Enumerated(EnumType.STRING)
    private TemplateState templateState;

    @Column(name = "template_number")
    private String templateNumber;

    @Column(name = "inspection_content")
    private String inspectionContent;

    @Column(name = "inspection_frequency")
    @Enumerated(EnumType.STRING)
    private FrequencyTypes inspectionFrequency;

    @Column(name = "additional_fields")
    private String additionalFields;

    @Column(name = "hour_time")
    private int hourTime;

    @Column(name = "minute_time")
    private int minuteTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "inspector_id")
    private Employee inspector;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "inspectionTaskTemplate")
    private List<InspectionPointTemplate> pointTemplateList;

    public static InspectionTaskTemplate create(TemplateState templateState,
                                                String templateNumber,
                                                String inspectionContent,
                                                FrequencyTypes inspectionFrequency,
                                                String additionalFields,
                                                int hourTime,
                                                int minuteTime,
                                                Employee inspector) {
        InspectionTaskTemplate inspectionTaskTemplate = new InspectionTaskTemplate();
        inspectionTaskTemplate.setTemplateState(templateState);
        inspectionTaskTemplate.setTemplateNumber(templateNumber);
        inspectionTaskTemplate.setInspectionContent(inspectionContent);
        inspectionTaskTemplate.setInspectionFrequency(inspectionFrequency);
        inspectionTaskTemplate.setAdditionalFields(additionalFields);
        inspectionTaskTemplate.setHourTime(hourTime);
        inspectionTaskTemplate.setMinuteTime(minuteTime);
        inspectionTaskTemplate.setInspector(inspector);
        session().persist(inspectionTaskTemplate);
        return inspectionTaskTemplate;
    }
}
