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
 * DATE 2019/12/5 10:42
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class FunctionGroup extends BaseEntity {

    @Column
    private String groupCode;

    @Column
    private String groupName;

    @Column
    private String parentId;

    public static FunctionGroup create(String groupCode, String groupName, String parentId) {
        FunctionGroup functionGroup = new FunctionGroup();
        functionGroup.setGroupCode(groupCode);
        functionGroup.setGroupName(groupName);
        functionGroup.setParentId(parentId);
        session().persist(functionGroup);
        return functionGroup;
    }
}
