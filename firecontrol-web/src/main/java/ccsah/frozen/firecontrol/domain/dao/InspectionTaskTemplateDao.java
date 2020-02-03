package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.InspectionTaskTemplate;
import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionTaskTemplateDao extends JpaRepository<InspectionTaskTemplate, String> {

    InspectionTaskTemplate findInspectionTaskTemplateByTemplateNumberAndIsDeletedFalse(String templateNumber);

    InspectionTaskTemplate findInspectionTaskTemplateByInspectionContentAndIsDeletedFalse(String inspectionContent);

    InspectionTaskTemplate findInspectionTaskTemplateByIdAndIsDeletedFalse(String id);

    List<InspectionTaskTemplate> findByTemplateStateAndIsDeletedFalseOrderByTemplateNumber(TemplateState templateState);

    Page<InspectionTaskTemplate> findAll(Specification<InspectionTaskTemplate> querySpec, Pageable pageRequest);
}
