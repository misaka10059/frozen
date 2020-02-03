package ccsah.frozen.firecontrol.controller;

import ccsah.frozen.firecontrol.service.AlarmAreaService;
import ccsfr.core.web.BaseApiController;
import ccsfr.core.web.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/24 16:12
 * DESC
 */
@Api(value = "alarm_area")
@RequestMapping(value = "alarm_area")
@RestController
public class AlarmAreaController extends BaseApiController {

    @Autowired
    private AlarmAreaService alarmAreaService;

    @RequestMapping(value = "/add_alarm_area", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加报警区域",
            notes = "报警区域是根据iot中的区域来添加的  \n" +
                    "area_code为区域编号，与iot中的area相同，如果当前添加的报警区域的父级地区在iot的Area中存在，但是本身在iot中不存在，则AreaCode为父级地区的AreaCode  \n" +
                    "area_name为报警区域名称  \n" +
                    "parent_id为父级报警区域  ")
    public Object add(@RequestParam("area_code") String areaCode,
                      @RequestParam("area_name") String areaName,
                      @RequestParam("parent_id") String parentId) {
        return ResponseData.ok(alarmAreaService.addAlarmArea(areaCode, areaName, parentId));
    }

    @RequestMapping(value = "/delete_alarm_area", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除报警区域")
    public Object deleteAlarmArea(String alarmAreaId) {
        return ResponseData.ok(alarmAreaService.deleteAlarmArea(alarmAreaId));
    }

    @RequestMapping(value = "/update_alarm_area", method = RequestMethod.POST)
    @ApiOperation(value = "更新报警区域")
    public Object updateAlarmArea(@RequestParam("alarm_area_id") String alarmAreaId,
                                  @RequestParam("area_name") String areaName,
                                  @RequestParam("parent_id") String parentId) {
        return ResponseData.ok(alarmAreaService.updateAlarmArea(alarmAreaId, areaName, parentId));
    }

    @RequestMapping(value = "/list_alarm_area_by_top_node", method = RequestMethod.POST)
    @ApiOperation(value = "获取顶层节点拥有的节点列表")
    public Object listAlarmAreaByTopNode() {
        return ResponseData.ok(alarmAreaService.listAlarmAreaByTopNode());
    }

    @RequestMapping(value = "/list_alarm_area_tree_by_id", method = RequestMethod.POST)
    @ApiOperation(value = "获取报警区域的树形结构")
    public Object listAlarmAreaTreeById(@RequestParam("alarm_area_id") String alarmAreaId,
                                        @RequestParam("depth") int depth) {
        return ResponseData.ok(alarmAreaService.listAlarmAreaTreeById(alarmAreaId, depth));
    }

    @RequestMapping(value = "/list_area_compare_on_top_node", method = RequestMethod.POST)
    @ApiOperation(value = "获取顶层节点下可添加的报警区域")
    public Object listAreaCompareOnTopNode() {
        return ResponseData.ok(alarmAreaService.listAreaCompareOnTopNode());
    }

    @RequestMapping(value = "/list_area_can_be_added", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定节点下可添加的报警区域")
    public Object listAreaCanBeAdded(@RequestParam("alarm_area_id") String alarmAreaId) {
        return ResponseData.ok(alarmAreaService.listAreaCanBeAdded(alarmAreaId));
    }
}
