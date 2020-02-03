package ccsah.frozen.firecontrol.controller;

import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.domain.dto.InspectionPointReceiveDto;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionPointState;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionTaskState;
import ccsah.frozen.firecontrol.service.InspectionTaskService;
import ccsfr.core.domain.PageOffsetRequest;
import ccsfr.core.web.BaseApiController;
import ccsfr.core.web.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 8:50
 * DESC
 */
@Api(value = "inspection_task")
@RequestMapping(value = "inspection_task")
@RestController
public class InspectionTaskController extends BaseApiController {

    @Autowired
    private InspectionTaskService taskService;

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ApiOperation(value = "手动生成指定巡检模板的巡检任务", notes = "task_template_id为指定巡检任务的模板id")
    public void generate(@RequestParam("task_template_id") String taskTemplateId) {
        taskService.generate(taskTemplateId);
    }

    @RequestMapping(value = "/delete_inspection_task", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除巡检任务")
    public Object deleteInspectionTask(@RequestParam("inspectionTaskId") String inspectionTaskId) {
        return ResponseData.ok(taskService.deleteTask(inspectionTaskId));
    }

    @RequestMapping(value = "/update_task_message", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新巡检任务的信息",
            notes = "inspection_task_id为指定巡检任务的id  \n" +
                    "inspection_task_state为巡检任务的状态  \n" +
                    "inspection_start_time为巡检任务的开始时间  \n" +
                    "inspection_end_time为巡检任务完成的时间  \n" +
                    "inspection_result为巡检的结果  \n" +
                    "additional_message为额外的信息")
    public Object updateTaskMessage(@RequestParam("inspection_task_id") String inspectionTaskId,
                                    @RequestParam("inspection_task_state") InspectionTaskState taskState,
                                    @RequestParam("inspection_start_time") long startTime,
                                    @RequestParam("inspection_end_time") long endTime,
                                    @RequestParam("inspection_result") String inspectionResult,
                                    @RequestParam("additional_message") String additionalMessage) {
        return ResponseData.ok(taskService.updateTaskMessage(
                inspectionTaskId,
                taskState,
                startTime,
                endTime,
                inspectionResult,
                additionalMessage));
    }

    @RequestMapping(value = "/receive_point_message", method = RequestMethod.POST)
    @ApiOperation(value = "")
    public Object receivePointMessage(@RequestBody InspectionPointReceiveDto pointReceiveDto) {
        return ResponseData.ok(taskService.receivePointMessage(pointReceiveDto));
    }

    @RequestMapping(value = "list_by_parameter", method = RequestMethod.POST)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "inspector_id为巡检人员的id  \n" +
                    "inspection_task_number为巡检任务的编号  \n" +
                    "inspection_content为巡检内容  \n" +
                    "inspection_task_state为巡检任务的状态  \n" +
                    "inspection_result为巡检的结果  \n" +
                    "additional_message为巡检的额外信息  \n" +
                    "offset为分页的偏移量  \n" +
                    "limit为分页每页显示的记录数")
    public Object listInspectionTaskByParameter(@RequestParam(value = "inspector_id", required = false) String inspectorId,
                                                @RequestParam(value = "inspection_task_number", required = false) String inspectionTaskNumber,
                                                @RequestParam(value = "inspection_content", required = false) String inspectionContent,
                                                @RequestParam(value = "inspection_task_state") InspectionTaskState inspectionTaskState,
                                                @RequestParam(value = "inspection_result", required = false) String inspectionResult,
                                                @RequestParam(value = "additional_message", required = false) String additionalMessage,
                                                @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                                @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.DESC, "ctime"));
        return ResponseData.ok(taskService.listInspectionTaskByParameter(
                inspectorId,
                inspectionTaskNumber,
                inspectionContent,
                inspectionTaskState,
                inspectionResult,
                additionalMessage,
                pageRequest));
    }

    @RequestMapping(value = "get_inspection_task_detail", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条巡检任务的详细信息")
    public Object getInspectionTaskDetail(@RequestParam("inspection_task_id") String inspectionTaskId) {
        return ResponseData.ok(taskService.getInspectionTaskDetail(inspectionTaskId));
    }
}
