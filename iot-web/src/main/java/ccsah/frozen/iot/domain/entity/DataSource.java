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
 * DATE 2019/12/5 10:39
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class DataSource extends BaseEntity {

    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "management_platform")
    private String managementPlatform;

    public static DataSource create(String sourceName, String managementPlatform) {
        DataSource dataSource = new DataSource();
        dataSource.setSourceName(sourceName);
        dataSource.setManagementPlatform(managementPlatform);
        session().persist(dataSource);
        return dataSource;
    }
}
