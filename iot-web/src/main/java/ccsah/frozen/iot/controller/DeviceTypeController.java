package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.DeviceTypeService;
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
 * DATE 2019/12/12 9:23
 * DESC
 */
@Api(value = "device_type", description = "设备类型模块")
@RequestMapping("device_type")
@RestController
public class DeviceTypeController extends BaseApiController {

    @Autowired
    private DeviceTypeService deviceTypeService;

    @RequestMapping(value = "/add_device_type", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加设备类型",
            notes = "product_name为产品名称  \n" +
                    "product_type为产品型号  \n" +
                    "abbreviation为名称的缩写，如烟感可为YG，摄像头可为SX，NFC可为NC  \n" +
                    "vendor_id为对应厂商的id，选填，不填则为'未指定厂商'  \n" +
                    "data_source_id为对应数据源的id，选填，不填则为'未指定数据源'  \n" +
                    "function_group_id，选填，不填则为'未指定功能类别'  \n")
    public Object addDeviceType(@RequestParam(value = "product_name") String productName,
                                @RequestParam(value = "product_type") String productType,
                                @RequestParam(value = "abbreviation") String abbreviation,
                                @RequestParam(value = "vendor_id", required = false) String vendorId,
                                @RequestParam(value = "data_source_id", required = false) String dataSourceId,
                                @RequestParam(value = "function_group_id", required = false) String functionGroupId) {
        return ResponseData.ok(deviceTypeService.addDeviceType(productName, productType, abbreviation, vendorId, dataSourceId, functionGroupId));
    }

    @RequestMapping(value = "/delete_device_type", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除设备类型")
    public Object deleteDeviceType(@RequestParam("id") String id) {
        return ResponseData.ok(deviceTypeService.deleteDeviceType(id));
    }

    @RequestMapping(value = "/update_device_type", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新设备类型",
            notes = "id为需更新的设备类型的id  \n" +
                    "product_name为更新后的产品名称  \n" +
                    "product_type为更新后的产品型号  \n" +
                    "abbreviation为名称的缩写  \n" +
                    "vendor_id为更新后的供应商信息，选填，不填则供应商为'未指定厂商'  \n" +
                    "data_source_id为更新后的数据源信息，选填，不填则数据源为'未指定数据源'  \n" +
                    "function_group_id为更新后的功能组别，选填，不填则功能组别为'未指定功能组别'")
    public Object updateDeviceType(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "product_name") String productName,
                                   @RequestParam(value = "product_type") String productType,
                                   @RequestParam(value = "abbreviation") String abbreviation,
                                   @RequestParam(value = "vendor_id", required = false) String vendorId,
                                   @RequestParam(value = "data_source_id", required = false) String dataSourceId,
                                   @RequestParam(value = "function_group_id", required = false) String functionGroupId) {
        return ResponseData.ok(
                deviceTypeService.updateDeviceType(
                        id,
                        productName,
                        productType,
                        abbreviation,
                        vendorId,
                        dataSourceId,
                        functionGroupId));
    }

    @RequestMapping(value = "/get_device_type_detail_by_id", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条设备类型的详细信息")
    public Object getDeviceTypeListById(@RequestParam("device_type_id") String deviceTypeId) {
        return ResponseData.ok(deviceTypeService.getDeviceTypeDetailById(deviceTypeId));
    }

    @RequestMapping(value = "/list_device_type_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "product_name为产品名称，选填  \n" +
                    "product_type为产品型号，选填  \n" +
                    "abbreviation为名称的缩写，如烟感可为YG，摄像头可为SX，NFC可为NC  \n" +
                    "vendor_id为供应商id，选填  \n" +
                    "data_source_id为数据源id，选填  \n" +
                    "function_group_id为功能组别id，选填  \n" +
                    "start_query_time为查询的开始时间，选填  \n" +
                    "end_query_time为查询的结束时间，选填  \n" +
                    "offset为偏移量，从0开始，默认为0  \n" +
                    "limit为每页记录条数，默认为5")
    public Object listDeviceTypeByParameter(@RequestParam(value = "product_name", required = false) String productName,
                                            @RequestParam(value = "product_type", required = false) String productType,
                                            @RequestParam(value = "abbreviation", required = false) String abbreviation,
                                            @RequestParam(value = "vendor_id", required = false) String vendorId,
                                            @RequestParam(value = "data_source_id", required = false) String dataSourceId,
                                            @RequestParam(value = "function_group_id", required = false) String functionGroupId,
                                            @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                            @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                            @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                            @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.DESC, "productName", "ctime"));
        return ResponseData.ok(
                deviceTypeService.listDeviceTypeByParameter(
                        productName,
                        productType,
                        abbreviation,
                        vendorId,
                        dataSourceId,
                        functionGroupId,
                        startQueryTime,
                        endQueryTime,
                        pageRequest));
    }
}
