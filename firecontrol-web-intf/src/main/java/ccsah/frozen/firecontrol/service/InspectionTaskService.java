package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.domain.dto.InspectionPointDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionPointReceiveDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskDetailDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskDto;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionPointState;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionTaskState;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InspectionTaskService {

    void check();

    void generate(String taskTemplateId);

    InspectionTaskDto deleteTask(String inspectionTaskId);

    InspectionTaskDto updateTaskMessage(String inspectionTaskId,
                                        InspectionTaskState taskState,
                                        long startTime,
                                        long endTime,
                                        String inspectionResult,
                                        String additionalMessage);

    /*InspectionPointDto updatePointMessage(String pointId,
                                          InspectionPointState pointState,
                                          long inspectionTime);*/

    List<InspectionPointDto> receivePointMessage(InspectionPointReceiveDto pointReceiveDto);

    PageData<InspectionTaskDto> listInspectionTaskByParameter(String inspectorId,
                                                              String inspectionTaskNumber,
                                                              String inspectionContent,
                                                              InspectionTaskState inspectionTaskState,
                                                              String inspectionResult,
                                                              String additionalMessage,
                                                              Pageable pageRequest);

    InspectionTaskDetailDto getInspectionTaskDetail(String inspectionTaskId);

}
