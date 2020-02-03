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
 * DATE 2019/12/18 15:49
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Employee extends BaseEntity {

    @Column
    private String employeeName;

    public static Employee create(String employeeName) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        session().persist(employee);
        return employee;
    }
}
