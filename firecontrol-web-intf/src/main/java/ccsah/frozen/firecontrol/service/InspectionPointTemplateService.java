package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.domain.dto.InspectionPointTemplateSingleDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskTemplatePointDto;

import java.util.List;

public interface InspectionPointTemplateService {

    InspectionTaskTemplatePointDto addPointTemplate(String taskTemplateId, List<String> deviceIdList);

    InspectionTaskTemplatePointDto deletePointTemplate(String pointTemplateId);
}