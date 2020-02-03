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
 * DATE 2019/12/5 10:47
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Project extends BaseEntity {
    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "project_name")
    private String projectName;

    public static Project create(String projectCode, String projectName) {
        Project project = new Project();
        project.setProjectCode(projectCode);
        project.setProjectName(projectName);
        session().persist(project);
        return project;
    }
}
