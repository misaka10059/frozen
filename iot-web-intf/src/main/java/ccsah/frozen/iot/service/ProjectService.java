package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.ProjectDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 16:09
 * DESC 项目服务
 */
public interface ProjectService {

    ProjectDto addProject(String projectCode, String projectName);

    ProjectDto deleteProject(String projectId);

    ProjectDto updateProject(String projectId, String projectCode, String projectName);

    PageData<ProjectDto> listProjectByParameter(String projectCode,
                                                String projectName,
                                                long startQueryTime,
                                                long endQueryTime,
                                                Pageable pageRequest);

}
