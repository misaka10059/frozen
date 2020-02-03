package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 16:06
 * DESC
 */
@Getter
@Setter
public class ProjectDto {

    private String projectId;

    private String projectCode;

    private String projectName;

    public ProjectDto(String projectId, String projectCode, String projectName) {
        this.setProjectId(projectId);
        this.setProjectCode(projectCode);
        this.setProjectName(projectName);
    }
}
