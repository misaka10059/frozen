package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.InspectionTask;
import ccsah.frozen.firecontrol.domain.entity.InspectionTaskTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface InspectionTaskDao extends JpaRepository<InspectionTask, String> {

    InspectionTask findByIdAndIsDeletedFalse(String taskId);

    InspectionTask findByInspectionTaskTemplateAndInspectionScheduledTimeAndIsDeletedFalse(InspectionTaskTemplate taskTemplate, Timestamp scheduledTime);

    Page<InspectionTask> findAll(Specification<InspectionTask> querySpec, Pageable pageRequest);
}