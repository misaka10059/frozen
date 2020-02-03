package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.FunctionGroupService;
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
 * DATE 2019/12/10 15:11
 * DESC
 */
@Api(value = "function_group")
@RequestMapping("function_group")
@RestController
public class FunctionGroupController extends BaseApiController {

    @Autowired
    private FunctionGroupService functionGroupService;

    @RequestMapping(value = "/add_function_group", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加功能类别",
            notes = "group_name为地区名  \n" +
                    "parent_id父级地区的id,非必填，不填则作为节点'未指定功能类别'的子节点  \n" +
                    "如果要添加第一层节点，则传入的parent_id为'0'")
    public Object addFunctionGroup(@RequestParam(value = "group_name") String groupName,
                                   @RequestParam(value = "parent_id", required = false) String parentId) {
        return ResponseData.ok(functionGroupService.addFunctionGroup( groupName, parentId));
    }

    @RequestMapping(value = "/delete_function_group_by_id", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除功能类别")
    public Object deleteFunctionGroupById(@RequestParam("function_group_id") String functionGroupId) {
        return ResponseData.ok(functionGroupService.deleteFunctionGroupById(functionGroupId));
    }

    @RequestMapping(value = "/update_function_group", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新功能类别",
            notes = "id为当前需更改FunctionGroup的id  \n" +
                    "group_name为更改后的名称  \n" +
                    "parent_id为更改后的父级FunctionGroup的id，非必填，不填则作为节点'未指定功能类别'的子节点  \n" +
                    "如果要更新当前节点为第一层节点，则传入的parent_id为'0'")
    public Object updateFunctionGroup(@RequestParam("id") String id,
//                                      @RequestParam(value = "group_code") String groupCode,
                                      @RequestParam(value = "group_name") String groupName,
                                      @RequestParam(value = "parent_id", required = false) String parentId) {
        return ResponseData.ok(functionGroupService.updateFunctionGroup(id, /*groupCode,*/ groupName, parentId));
    }

    @RequestMapping(value = "/list_function_group_by_top_node", method = RequestMethod.GET)
    @ApiOperation(value = "获取顶层节点拥有的节点列表", notes = "此方法获取的列表是所有节点的入口")
    public Object listFunctionGroupByTopNode() {
        return ResponseData.ok(functionGroupService.listFunctionGroupByTopNode());
    }

    @RequestMapping(value = "/List_function_group_by_group_name", method = RequestMethod.GET)
    @ApiOperation(value = "通过group_name模糊查询FunctionGroup")
    public Object listFunctionGroupByGroupName(@RequestParam("group_name") String groupName) {
        return ResponseData.ok(functionGroupService.listFunctionGroupByGroupName(groupName));
    }

    @RequestMapping(value = "/list_function_group_tree_by_id", method = RequestMethod.GET)
    @ApiOperation(
            value = "查询此节点的树形列表",
            notes = "id为当前需展开节点的id  \n" +
                    "depth为展开的层级数，包含自身这一层级，默认展开2级")
    public Object listFunctionGroupTreeById(@RequestParam("id") String id,
                                            @RequestParam(value = "depth", defaultValue = BaseString.defaultDepth) int depth) {
        return ResponseData.ok(functionGroupService.listFunctionGroupTreeById(id, depth));
    }

    @RequestMapping(value = "list_function_group_parent", method = RequestMethod.POST)
    @ApiOperation(value = "根据当前节点查询当前节点至顶层节点之间的各个节点")
    public Object listFunctionGroupParent(@RequestParam("function_group_id") String functionGroupId) {
        return ResponseData.ok(functionGroupService.listFunctionGroupSub(functionGroupId));
    }
}
