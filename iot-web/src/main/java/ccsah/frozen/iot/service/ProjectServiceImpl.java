package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.domain.dao.ProjectDao;
import ccsah.frozen.iot.domain.dao.ProjectDaoSpec;
import ccsah.frozen.iot.domain.dto.ProjectDto;
import ccsah.frozen.iot.domain.entity.Project;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 16:09
 * DESC 项目服务
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/5 16:12
     * DESC 添加Project
     */
    @Override
    @Transactional
    public ProjectDto addProject(String projectCode, String projectName) {
        Project project = getProjectByProjectCode(projectCode);
        if (project != null) {
            throw new ServiceException(600, ExceptionCode.PROJECT600);
        }
        project = Project.create(projectCode, projectName);
        return getProjectDto(project);
    }

    /**
     * DATE 2019/12/12 8:31
     * DESC 删除Project
     */
    @Override
    @Transactional
    public ProjectDto deleteProject(String projectId) {
        Project project = baseService.getProjectById(projectId);
        project.deleteLogical();
        return getProjectDto(project);
    }

    /**
     * DATE 2019/12/12 8:44
     * DESC 更新Project
     */
    @Override
    @Transactional
    public ProjectDto updateProject(String projectId, String projectCode, String projectName) {
        Project project = baseService.getProjectById(projectId);
        Project anotherProject = getProjectByProjectCode(projectCode);
        if (anotherProject != null && !anotherProject.getId().equals(project.getId())) {
            throw new ServiceException(602, ExceptionCode.PROJECT602);
        }
        project.setProjectCode(projectCode);
        project.setProjectName(projectName);
        return getProjectDto(project);
    }

    /**
     * DATE 2019/12/12 9:05
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<ProjectDto> listProjectByParameter(String projectCode, String projectName, long startQueryTime, long endQueryTime, Pageable pageRequest) {
        Specification<Project> querySpec = ProjectDaoSpec.getVariableSpec(
                projectCode,
                projectName,
                startQueryTime,
                endQueryTime);
        Page<Project> projectList = projectDao.findAll(querySpec, pageRequest);
        List<ProjectDto> projectDtoList = getProjectDto(projectList.getContent());
        return new PageData<>(projectDtoList, (int) projectList.getTotalElements());
    }

    /**
     * DATE 2019/12/16 10:32
     * DESC
     */
    private Project getProjectByProjectCode(String projectCode) {
        return projectDao.findProjectByProjectCodeAndIsDeletedFalse(projectCode);
    }

    private ProjectDto getProjectDto(Project project) {
        return new ProjectDto(project.getId(), project.getProjectCode(), project.getProjectName());
    }

    private List<ProjectDto> getProjectDto(List<Project> projectList) {
        return projectList.stream().map(this::getProjectDto).collect(Collectors.toList());
    }
}
