package ccsah.frozen.iot.domain.entity;

import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 10:52
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Area extends BaseEntity {

    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "parentId")
    private String parentId;

    public static Area create(String areaCode, String areaName, String parentId) {
        Area area = new Area();
        area.setAreaCode(areaCode);
        area.setAreaName(areaName);
        area.setParentId(parentId);
        session().persist(area);
        return area;
    }
}
