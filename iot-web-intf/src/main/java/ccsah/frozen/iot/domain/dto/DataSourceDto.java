package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 14:47
 * DESC
 */
@Getter
@Setter
public class DataSourceDto {

    String id;

    private String sourceName;

    private String managementPlatform;

    public DataSourceDto(String id, String sourceName, String managementPlatform) {
        this.setId(id);
        this.setSourceName(sourceName);
        this.setManagementPlatform(managementPlatform);
    }
}
