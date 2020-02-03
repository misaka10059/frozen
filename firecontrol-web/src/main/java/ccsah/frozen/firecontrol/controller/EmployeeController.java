package ccsah.frozen.firecontrol.controller;

import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.service.EmployeeService;
import ccsfr.core.domain.PageOffsetRequest;
import ccsfr.core.web.BaseApiController;
import ccsfr.core.web.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/24 15:29
 * DESC
 */
@Api(value = "/inspectorName")
@RequestMapping(value = "/inspector")
@RestController
public class EmployeeController extends BaseApiController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/add_inspector", method = RequestMethod.POST)
    @ApiOperation(value = "添加工作人员")
    public Object addInspector(@RequestParam("inspector_name") String InspectorName) {
        return ResponseData.ok(employeeService.addEmployee(InspectorName));
    }

    @RequestMapping(value = "/delete_inspector", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除工作人员")
    public Object deleteEmployee(@RequestParam("inspector_id") String inspectorId) {
        return ResponseData.ok(employeeService.deleteEmployee(inspectorId));
    }

    @RequestMapping(value = "/list_inspector_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "inspector_name为员工姓名  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为分页的偏移量  \n" +
                    "limit为分页每页显示的记录数")
    public Object listInspectorByParameter(@RequestParam(value = "inspector_name", required = false) String inspectorName,
                                           @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                           @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                           @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                           @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.DESC, "ctime"));
        return ResponseData.ok(
                employeeService.listEmployeeByParameter(inspectorName, startQueryTime, endQueryTime, pageRequest));
    }
}
