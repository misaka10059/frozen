package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.DataSourceService;
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
 * DATE 2019/12/10 14:31
 * DESC
 */
@Api(value = "data_source", description = "数据源模块")
@RequestMapping("data_source")
@RestController
public class DataSourceController extends BaseApiController {

    @Autowired
    private DataSourceService dataSourceService;

    @RequestMapping(value = "/add_data_source", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加DataSource",
            notes = "source_name为数据源的名称  \n" +
                    "management_platform为管理平台地址")
    public Object addDataSource(@RequestParam("source_name") String sourceName,
                                @RequestParam("management_platform") String managementPlatform) {
        return ResponseData.ok(dataSourceService.addDataSource(sourceName, managementPlatform));
    }

    @RequestMapping(value = "/delete_data_source", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除DataSource")
    public Object deleteDataSource(@RequestParam("data_source_id") String dataSourceId) {
        return ResponseData.ok(dataSourceService.deleteDataSource(dataSourceId));
    }

    @RequestMapping(value = "/update_data_source", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新DataSource",
            notes = "data_source_id为需更新的DataSource的id  \n" +
                    "source_name为数据源名称  \n" +
                    "managementPlatform为管理平台地址")
    public Object updateDataSource(@RequestParam(value = "data_source_id") String dataSourceId,
                                   @RequestParam(value = "source_name") String sourceName,
                                   @RequestParam(value = "management_platform", required = false) String managementPlatform) {
        return ResponseData.ok(dataSourceService.updateDataSource(dataSourceId, sourceName, managementPlatform));
    }

    @RequestMapping(value = "/list_data_source_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "source_name为数据源名称  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为偏移量，从0开始，默认为0  \n" +
                    "limit为从偏移量开始的记录的条数，默认为5")
    public Object listDataSourceByParameter(@RequestParam(value = "source_name", required = false) String sourceName,
                                            @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                            @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                            @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                            @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.DESC, "sourceName", "ctime"));
        return ResponseData.ok(
                dataSourceService.listDataSourceByParameter(
                        sourceName,
                        startQueryTime,
                        endQueryTime,
                        pageRequest));
    }
}
