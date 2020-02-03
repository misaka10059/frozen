package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.DepartmentService;
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
 * DATE 2019/12/10 17:30
 * DESC
 */
@Api(value = "department", description = "部门模块")
@RequestMapping("department")
@RestController
public class DepartmentController extends BaseApiController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/add_department", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加某一部门",
            notes = "department_name为部门名  \n" +
                    "parent_id父级部门的id,非必填，不填则作为'未指定部门'的子节点  \n" +
                    "如果要添加第一层节点，则传入的parent_id为'0'")
    public Object addDepartment(@RequestParam(value = "department_name") String departmentName,
                                @RequestParam(value = "parent_id", required = false) String parentId) {
        return ResponseData.ok(departmentService.addDepartment(departmentName, parentId));
    }

    @RequestMapping(value = "/delete_department_by_id", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除指定id的部门及其下属的所有部门", notes = "返回删除的部门的树形结构")
    public Object deleteDepartmentById(@RequestParam("department_id") String departmentId) {
        return ResponseData.ok(departmentService.deleteDepartmentById(departmentId));
    }

    @RequestMapping(value = "/update_department", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新部门信息",
            notes = "id为当前需更改部门的id  \n" +
                    "department_name为更改后的名称  \n" +
                    "parent_id为更改后的父级部门的id，非必填，不填则作为'未指定部门'的子节点  \n" +
                    "如果要更新当前节点为第一层节点，则传入的parent_id为'0'")
    public Object updateDepartment(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "department_name") String departmentName,
                                   @RequestParam(value = "parent_id", required = false) String parentId) {
        return ResponseData.ok(departmentService.updateDepartment(id, departmentName, parentId));
    }

    @RequestMapping(value = "/list_department_by_top_node", method = RequestMethod.GET)
    @ApiOperation(value = "获取顶层节点拥有的节点列表", notes = "此方法获取的列表是所有节点的入口")
    public Object listDepartmentByTopNode() {
        return ResponseData.ok(departmentService.listDepartmentByTopNode());
    }

    @RequestMapping(value = "/list_department_by_department_name", method = RequestMethod.GET)
    @ApiOperation(value = "通过department_name模糊查询Department")
    public Object listDepartmentByDepartmentName(@RequestParam("department_name") String departmentName) {
        return ResponseData.ok(departmentService.listDepartmentByDepartmentName(departmentName));
    }

    @RequestMapping(value = "/list_department_tree_by_id", method = RequestMethod.GET)
    @ApiOperation(
            value = "查询此节点的树形列表",
            notes = "id为当前需展开节点的id  \n" +
                    "depth为展开的层级数，包含自身这一层级，默认展开2级")
    public Object listDepartmentTreeListById(@RequestParam(value = "id") String id,
                                             @RequestParam(value = "depth", defaultValue = BaseString.defaultDepth) int depth) {
        return ResponseData.ok(departmentService.listDepartmentTreeListById(id, depth));
    }

    @RequestMapping(value = "list_department_parent", method = RequestMethod.GET)
    @ApiOperation(value = "根据当前节点查询当前节点至顶层节点之间的各个节点")
    public Object listDepartmentParent(@RequestParam("department_id") String departmentId) {
        return ResponseData.ok(departmentService.listDepartmentSub(departmentId));
    }
}
