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
 * DATE 2019/12/5 10:49
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Department extends BaseEntity {
    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "parent_id")
    private String parentId;

    public static Department create(String departmentName, String parentId) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        department.setParentId(parentId);
        session().persist(department);
        return department;
    }
}
