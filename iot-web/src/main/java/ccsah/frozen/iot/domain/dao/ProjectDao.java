package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project, String> {

    Project findProjectByProjectNameAndIsDeletedFalse(String projectName);

    Project findProjectByProjectCodeAndIsDeletedFalse(String projectCode);

    Project findProjectByIdAndIsDeletedFalse(String projectId);

    Page<Project> findAll(Specification<Project> specification, Pageable pageRequest);

}
