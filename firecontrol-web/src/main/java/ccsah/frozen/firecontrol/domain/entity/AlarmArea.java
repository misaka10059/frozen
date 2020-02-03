package ccsah.frozen.firecontrol.domain.entity;

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
 * DATE 2019/12/18 15:53
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class AlarmArea extends BaseEntity {

    @Column
    private String areaCode;

    @Column
    private String areaName;

    @Column
    private String parentId;

    public static AlarmArea create(String areaCode, String areaName, String parentId) {
        AlarmArea alarmArea = new AlarmArea();
        alarmArea.setAreaCode(areaCode);
        alarmArea.setAreaName(areaName);
        alarmArea.setParentId(parentId);
        session().persist(alarmArea);
        return alarmArea;
    }
}
