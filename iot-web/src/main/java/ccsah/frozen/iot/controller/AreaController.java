package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.AreaService;
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
 * DATE 2019/12/10 11:11
 * DESC
 */
@Api(value = "area", description = "区域模块")
@RequestMapping("area")
@RestController
public class AreaController extends BaseApiController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/add_area", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加某一地区",
            notes = "area_code为地区编号  \n" +
                    "area_name为地区名  \n" +
                    "parent_id父级地区的id,非必填，不填则作为节点'未指定地区'的子节点  \n" +
                    "如果要添加第一层节点，则传入的parent_id为'0'")
    public Object addArea(@RequestParam(value = "area_code") String areaCode,
                          @RequestParam(value = "area_name") String areaName,
                          @RequestParam(value = "parent_id", required = false) String parentId) {
        return ResponseData.ok(areaService.addArea(areaCode, areaName, parentId));
    }

    @RequestMapping(value = "/delete_area", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除指定id的地区及其下属所有地区", notes = "返回删除的地区的树形结构")
    public Object deleteArea(@RequestParam("area_id") String areaId) {
        return ResponseData.ok(areaService.deleteArea(areaId));
    }

    @RequestMapping(value = "/update_area", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新地区信息",
            notes = "id为当前需更改地区的id  \n" +
                    "area_name为更改后的名称  \n" +
                    "parent_id为更改后的父级地区的id，非必填，不填则作为节点'未指定地区'的子节点  \n" +
                    "如果要更新当前节点为第一层节点，则传入的parent_id为'0'")
    public Object updateArea(@RequestParam(value = "id") String id,
                             @RequestParam(value = "area_name") String areaName,
                             @RequestParam(value = "parent_id", required = false) String parentId) {
        return ResponseData.ok(areaService.update(id, areaName, parentId));
    }

    @RequestMapping(value = "/list_area_by_top_node", method = RequestMethod.GET)
    @ApiOperation(value = "获取顶层节点拥有的节点列表", notes = "此方法获取的列表是所有节点的入口")
    public Object listAreaByTopNode() {
        return ResponseData.ok(areaService.listAreaByTopNode());
    }

    @RequestMapping(value = "/get_area_by_area_name", method = RequestMethod.GET)
    @ApiOperation(value = "通过area_name模糊查询Area")
    public Object getAreaByAreaName(@RequestParam("area_name") String areaName) {
        return ResponseData.ok(areaService.listAreaByAreaName(areaName));
    }

    @RequestMapping(value = "/list_area_tree_by_id", method = RequestMethod.GET)
    @ApiOperation(
            value = "查询此节点的树形列表",
            notes = "id为当前需展开节点的id  \n" +
                    "depth为展开的层级数，包含自身这一层级，默认展开2级")
    public Object listAreaTreeById(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "depth", defaultValue = BaseString.defaultDepth) int depth) {
        return ResponseData.ok(areaService.listAreaTreeById(id, depth));
    }

    @RequestMapping(value = "/list_area_parent", method = RequestMethod.GET)
    @ApiOperation(value = "根据当前节点查询当前节点至顶层节点之间的各个节点")
    public Object listAreaParent(@RequestParam("area_id") String areaId) {
        return ResponseData.ok(areaService.listAreaSub(areaId));
    }

    @RequestMapping(value = "/list_device_by_area", method = RequestMethod.GET)
    @ApiOperation(value = "/list_device_by_area", notes = "查询指定Area及下属所有Area拥有的设备列表")
    public Object listDeviceByArea(@RequestParam("area_id") String areaId) {
        return ResponseData.ok(areaService.listDeviceByArea(areaId));
    }


}
