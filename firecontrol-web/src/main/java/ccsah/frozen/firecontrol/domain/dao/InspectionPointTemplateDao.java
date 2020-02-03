package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.entity.InspectionPointTemplate;
import ccsah.frozen.firecontrol.domain.entity.InspectionTaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionPointTemplateDao extends JpaRepository<InspectionPointTemplate, String> {

    InspectionPointTemplate findByIdAndIsDeletedFalse(String id);

    InspectionPointTemplate findInspectionPointTemplateByInspectionTaskTemplateAndDeviceAndIsDeletedFalse(InspectionTaskTemplate taskTemplate, Device device);

    List<InspectionPointTemplate> findByInspectionTaskTemplateAndIsDeletedFalse(InspectionTaskTemplate taskTemplate);
}
