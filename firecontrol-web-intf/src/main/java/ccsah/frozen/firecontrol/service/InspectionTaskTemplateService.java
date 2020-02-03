package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.domain.dto.InspectionTaskTemplateDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskTemplatePointDto;
import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InspectionTaskTemplateService {

    InspectionTaskTemplatePointDto addTaskTemplate(TemplateState templateState,
                                                   String templateNumber,
                                                   String inspectionContent,
                                                   FrequencyTypes inspectionFrequency,
                                                   String additionalFields,
                                                   int hourTime,
                                                   int minuteTime,
                                                   String inspectorId,
                                                   List<String> deviceIdList);

    InspectionTaskTemplateDto deleteTaskTemplate(String taskTemplateId);

    InspectionTaskTemplateDto updateTaskTemplate(String taskTemplateId,
                                                 TemplateState templateState,
                                                 String templateNumber,
                                                 String inspectionContent,
                                                 FrequencyTypes inspectionFrequency,
                                                 String additionalFields,
                                                 int hourTime,
                                                 int minuteTime,
                                                 String inspectorId);

    PageData<InspectionTaskTemplatePointDto> listTaskTemplateByParameter(TemplateState templateState,
                                                                         String templateNumber,
                                                                         String inspectionContent,
                                                                         FrequencyTypes inspectionFrequency,
                                                                         String inspectorId,
                                                                         long startQueryTime,
                                                                         long endQueryTime,
                                                                         Pageable pageRequest);
}