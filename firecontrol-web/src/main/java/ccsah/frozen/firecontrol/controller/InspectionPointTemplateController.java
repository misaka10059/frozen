package ccsah.frozen.firecontrol.controller;

import ccsah.frozen.firecontrol.service.InspectionPointTemplateService;
import ccsfr.core.web.BaseApiController;
import ccsfr.core.web.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/25 16:27
 * DESC
 */
@Api(value = "inspection_point_template")
@RequestMapping(value = "inspection_point_template")
@RestController
public class InspectionPointTemplateController extends BaseApiController {

    @Autowired
    private InspectionPointTemplateService pointTemplateService;

    @RequestMapping(value = "/add_point_template", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加巡检点模板",
            notes = "为指定巡检任务模板添加新的的巡检点模板  \n" +
                    "task_template_id为巡检任务模板的id  \n" +
                    "deviceIdList为设备对应id的List")
    public Object addPointTemplate(@RequestParam("task_template_id") String taskTemplateId,
                                   @RequestBody List<String> deviceIdList) {
        return ResponseData.ok(pointTemplateService.addPointTemplate(taskTemplateId, deviceIdList));
    }

    @RequestMapping(value = "/delete_point_template", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除巡检点模板")
    public Object deletePointTemplate(@RequestParam("point_template_id") String pointTemplateId) {
        return ResponseData.ok(pointTemplateService.deletePointTemplate(pointTemplateId));
    }

}
