package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.ProjectService;
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
 * DATE 2019/12/11 17:15
 * DESC
 */
@Api(value = "project", description = "项目模块")
@RequestMapping("project")
@RestController
public class ProjectController extends BaseApiController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "add_project", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加项目",
            notes = "project_code为项目代号  \n" +
                    "project_name为项目名称")
    public Object addProject(@RequestParam("project_code") String projectCode,
                             @RequestParam("project_name") String projectName) {
        return ResponseData.ok(projectService.addProject(projectCode, projectName));
    }

    @RequestMapping(value = "/delete_project", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除项目")
    public Object deleteProjectById(@RequestParam("project_id") String projectId) {
        return ResponseData.ok(projectService.deleteProject(projectId));
    }

    @RequestMapping(value = "/update_project", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新项目",
            notes = "id为需更新项目的id  \n" +
                    "project_code为项目代号  \n" +
                    "project_name为项目名称 ")
    public Object updateProject(@RequestParam(value = "id") String id,
                                @RequestParam(value = "project_code") String projectCode,
                                @RequestParam(value = "project_name") String projectName) {
        return ResponseData.ok(projectService.updateProject(id, projectCode, projectName));
    }

    @RequestMapping(value = "/list_project_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "project_code为项目代号  \n" +
                    "project_name为项目名称  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为偏移量，从0开始，默认为0  \n" +
                    "limit为从偏移量开始的记录的条数，默认为5")
    public Object listProjectByParameter(@RequestParam(value = "project_code", required = false) String projectCode,
                                         @RequestParam(value = "project_name", required = false) String projectName,
                                         @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                         @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                         @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                         @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.ASC, "projectCode", "projectName"));
        return ResponseData.ok(projectService.listProjectByParameter(projectCode, projectName, startQueryTime, endQueryTime, pageRequest));
    }
}
