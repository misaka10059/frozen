package ccsah.frozen.firecontrol.controller;

import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import ccsah.frozen.firecontrol.service.InspectionTaskTemplateService;
import ccsfr.core.domain.PageOffsetRequest;
import ccsfr.core.web.BaseApiController;
import ccsfr.core.web.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/25 15:15
 * DESC
 */
@Api(value = "inspection_task_template")
@RequestMapping(value = "inspection_task_template")
@RestController
public class InspectionTaskTemplateController extends BaseApiController {

    @Autowired
    private InspectionTaskTemplateService taskTemplateService;

    @RequestMapping(value = "/add_task_template", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加巡检模板",
            notes = "template_state为巡检模板的状态  \n" +
                    "template_number为巡检模板的编号  \n" +
                    "inspection_content为巡检模板的巡检内容  \n" +
                    "inspection_frequency为巡检频率：每天一次，每周一次，每月一次  \n" +
                    "additional_fields为巡检频率对应的时间，每天对应0，每周对应1-7，每月对应0-28'  \n" +
                    "hour_time为巡检时间的小时时间  \n" +
                    "minute_time为巡检时间的分钟时间  \n" +
                    "inspector_id为巡检人员的id  \n" +
                    "device_id_list为巡检点设备id的List")
    public Object addTaskTemplate(@RequestParam("template_state") TemplateState templateState,
                                  @RequestParam("template_number") String templateNumber,
                                  @RequestParam("inspection_content") String inspectionContent,
                                  @RequestParam("inspection_frequency") FrequencyTypes inspectionFrequency,
                                  @RequestParam("additional_fields") String additionalFields,
                                  @RequestParam("hour_time") int hourTime,
                                  @RequestParam("minute_time") int minuteTime,
                                  @RequestParam("inspector_id") String inspectorId,
                                  @RequestBody List<String> deviceIdList) {
        return ResponseData.ok(taskTemplateService.addTaskTemplate(
                templateState,
                templateNumber,
                inspectionContent,
                inspectionFrequency,
                additionalFields,
                hourTime,
                minuteTime,
                inspectorId,
                deviceIdList));
    }

    @RequestMapping(value = "/delete_task_template", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除巡检模板")
    public Object deleteTaskTemplate(@RequestParam("task_template_id") String taskTemplateId) {
        return ResponseData.ok(taskTemplateService.deleteTaskTemplate(taskTemplateId));
    }

    @RequestMapping(value = "/update_task_template", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新巡检模板的信息",
            notes = "task_template_id为对应巡检模板的id  \n" +
                    "template_state为巡检模板的状态  \n" +
                    "template_number为巡检模板的编号  \n" +
                    "inspection_content为巡检模板的巡检内容  \n" +
                    "inspection_frequency为巡检频率：每天一次，每周一次，每月一次  \n" +
                    "additional_fields为巡检频率对应的时间，每天对应0，每周对应1-7，每月对应0-28'  \n" +
                    "hour_time为巡检时间的小时时间  \n" +
                    "minute_time为巡检时间的分钟时间  \n" +
                    "inspector_id为巡检人员的id")
    public Object updateTaskTemplate(@RequestParam("task_template_id") String taskTemplateId,
                                     @RequestParam("template_state") TemplateState templateState,
                                     @RequestParam("template_number") String templateNumber,
                                     @RequestParam("inspection_content") String inspectionContent,
                                     @RequestParam("inspection_frequency") FrequencyTypes inspectionFrequency,
                                     @RequestParam("additional_fields") String additionalFields,
                                     @RequestParam("hour_time") int hourTime,
                                     @RequestParam("minute_time") int minuteTime,
                                     @RequestParam("inspector_id") String inspectorId) {
        return ResponseData.ok(taskTemplateService.updateTaskTemplate(
                taskTemplateId,
                templateState, templateNumber,
                inspectionContent,
                inspectionFrequency,
                additionalFields,
                hourTime,
                minuteTime,
                inspectorId));
    }

    @RequestMapping(value = "/list_task_template_by_parameter", method = RequestMethod.POST)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "template_state为巡检模板的状态  \n" +
                    "template_number为巡检模板的编号  \n" +
                    "inspection_content为巡检模板的巡检内容  \n" +
                    "inspection_frequency为巡检频率：每天一次，每周一次，每月一次  \n" +
                    "inspector_id为巡检人员的id  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为分页的偏移量  \n" +
                    "limit为分页每页显示的记录数")
    public Object listTaskTemplateByParameter(@RequestParam(value = "template_state") TemplateState templateState,
                                              @RequestParam(value = "template_number", required = false) String templateNumber,
                                              @RequestParam(value = "inspection_content", required = false) String inspectionContent,
                                              @RequestParam(value = "inspection_frequency") FrequencyTypes inspectionFrequency,
                                              @RequestParam(value = "inspector_id", required = false) String inspectorId,
                                              @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                              @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                              @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                              @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.ASC, "templateNumber"));
        return ResponseData.ok(taskTemplateService.listTaskTemplateByParameter(
                templateState,
                templateNumber,
                inspectionContent,
                inspectionFrequency,
                inspectorId,
                startQueryTime,
                endQueryTime,
                pageRequest));
    }
}
