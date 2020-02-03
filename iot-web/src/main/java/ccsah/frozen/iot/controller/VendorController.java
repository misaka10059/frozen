package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.VendorService;
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
 * DATE 2019/12/10 10:56
 * DESC
 */
@Api(value = "vendor", description = "供应商模块")
@RequestMapping("vendor")
@RestController
public class VendorController extends BaseApiController {

    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "/add_vendor", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加一条vendor记录",
            notes = "vendor_name为供应商名称  \n" +
                    "vendor_address为供应商地址，选填，不填则为'未指定地区'")
    public Object addVendor(@RequestParam(value = "vendor_name") String vendorName,
                            @RequestParam(value = "vendor_address", required = false) String vendorAddress) {
        return ResponseData.ok(vendorService.addVendor(vendorName, vendorAddress));
    }

    @RequestMapping(value = "/delete_vendor", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除一条vendor记录")
    public Object deleteVendor(@RequestParam("vendor_id") String vendorId) {
        return ResponseData.ok(vendorService.deleteVendor(vendorId));
    }

    @RequestMapping(value = "/update_vendor", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新vendor信息",
            notes = "vendor_id为需更新的vendor的id  \n" +
                    "vendor_name为保存的供应商名称  \n" +
                    "vendor_address为保存的供应商地址，选填，不填则为'未指定地区'  \n")
    public Object updateVendor(@RequestParam(value = "vendor_id") String vendorId,
                               @RequestParam(value = "vendor_name") String vendorName,
                               @RequestParam(value = "vendor_address", required = false) String vendorAddress) {
        return ResponseData.ok(vendorService.updateVendor(vendorId, vendorName, vendorAddress));
    }

    @RequestMapping(value = "/get_vendor_detail_by_id", method = RequestMethod.GET)
    @ApiOperation(value = "获取一条vendor记录的详细信息")
    public Object getVendorDetailById(@RequestParam("id") String id) {
        return ResponseData.ok(vendorService.getVendorDetailById(id));
    }

    @RequestMapping(value = "/list_vendor_by_vendor_name", method = RequestMethod.GET)
    @ApiOperation(value = "通过供应商名称查询获得供应商列表", notes = "只返回供应商的id和name列表，不分页")
    public Object listVendorByVendorName(@RequestParam("vendor_name") String vendorName) {
        return ResponseData.ok(vendorService.listVendorByVendorName(vendorName));
    }

    @RequestMapping(value = "/list_vendor_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "vendor_name为供应商名称  \n" +
                    "vendor_address为供应商地址  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为偏移量，从0开始，默认为0  \n" +
                    "limit为从偏移量开始的记录的条数，默认为5")
    public Object listVendorByParameter(@RequestParam(value = "vendor_name", required = false) String vendorName,
                                        @RequestParam(value = "vendor_address", required = false) String vendorAddress,
                                        @RequestParam(value = "start_query_time", required = false, defaultValue = "0") long startQueryTime,
                                        @RequestParam(value = "end_query_time", required = false, defaultValue = "0") long endQueryTime,
                                        @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                        @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.ASC, "vendorName", "ctime"));
        return ResponseData.ok(vendorService.listVendorByParameter(vendorName, vendorAddress, startQueryTime, endQueryTime, pageRequest));
    }
}
