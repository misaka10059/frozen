package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableStateQuery;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineStateQuery;
import ccsah.frozen.iot.service.DeviceService;
import ccsfr.core.domain.PageOffsetRequest;
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
 * DATE 2019/12/12 13:56
 * DESC
 */
@Api(value = "device", description = "设备模块")
@RequestMapping("device")
@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/add_device", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加设备",
            notes = "manufacturer_code为厂商定义的设备编号，选填，不填为空  \n" +
                    "imei_code为国际移动设备识别码，选填，不填为空  \n" +
                    "enable_state为设备启用状态  \n" +
                    "online_state为设备在线状态  \n" +
                    "device_type_id为设备类型id，选填，不填则为'未指定设备类型'的id  \n" +
                    "project_id为所属的项目的id，选填，不填则为'未指定项目'的id  \n" +
                    "department_id为所属的部门的id，选填，不填则为'未指定部门'的id  \n" +
                    "area_id为所属地区的id，选填，不填则为'未指定地区'的id")
    public Object addDevice(@RequestParam(value = "manufacturer_code", required = false) String manufacturerCode,
                            @RequestParam(value = "imei_code", required = false) String imeiCode,
                            @RequestParam(value = "enable_state") DeviceEnableState enableState,
                            @RequestParam(value = "online_state") DeviceOnlineState onlineState,
                            @RequestParam(value = "device_type_id", required = false) String deviceTypeId,
                            @RequestParam(value = "project_id", required = false) String projectId,
                            @RequestParam(value = "department_id", required = false) String departmentId,
                            @RequestParam(value = "area_id", required = false) String areaId) {
        return ResponseData.ok(deviceService.addDevice(manufacturerCode, imeiCode, enableState, onlineState, deviceTypeId, projectId, departmentId, areaId));
    }

    @RequestMapping(value = "/delete_device", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除设备")
    public Object deleteDevice(@RequestParam("device_id") String deviceId) {
        return ResponseData.ok(deviceService.deleteDevice(deviceId));
    }

    @RequestMapping(value = "/update_device", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新设备",
            notes = "device_id为需更新设备的id" +
                    "manufacturer_code为厂商定义的设备编号，选填，不填为空  \n" +
                    "imei_code为国际移动设备识别码，选填，不填为空  \n" +
                    "enable_state为设备启用状态  \n" +
                    "online_state为设备在线状态  \n" +
                    "device_type_id为设备类型id，选填，不填则为'未指定设备类型'的id  \n" +
                    "project_id为所属的项目的id，选填，不填则为'未指定项目'的id  \n" +
                    "department_id为所属的部门的id，选填，不填则为'未指定部门'的id  \n" +
                    "area_id为所属地区的id，选填，不填则为'未指定地区'的id")
    public Object updateDevice(@RequestParam(value = "device_id") String deviceId,
                               @RequestParam(value = "manufacturer_code", required = false) String manufacturerCode,
                               @RequestParam(value = "imei_code", required = false) String imeiCode,
                               @RequestParam(value = "enable_state") DeviceEnableState enableState,
                               @RequestParam(value = "online_state") DeviceOnlineState onlineState,
                               @RequestParam(value = "device_type_id", required = false) String deviceTypeId,
                               @RequestParam(value = "project_id", required = false) String projectId,
                               @RequestParam(value = "department_id", required = false) String departmentId,
                               @RequestParam(value = "area_id", required = false) String areaId) {
        return ResponseData.ok(deviceService.updateDevice(deviceId, manufacturerCode, imeiCode, enableState, onlineState, deviceTypeId, projectId, departmentId, areaId));
    }

    @RequestMapping(value = "/get_device_detail_by_id", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条设备的详细信息")
    public Object getDeviceDetailById(@RequestParam("device_id") String deviceId) {
        return ResponseData.ok(deviceService.getDeviceDetailById(deviceId));
    }

    @RequestMapping(value = "/get_device_camera_by_id", method = RequestMethod.POST)
    @ApiOperation(value = "获得摄像头设备的详细信息", notes = "与Device相比，添加了'播放地址'信息")
    public Object getDeviceCameraById(@RequestParam("device_id") String deviceId) {
        return ResponseData.ok(deviceService.getDeviceCameraDtoById(deviceId));
    }

    @RequestMapping(value = "/list_device_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "device_code为自定义的设备编号  \n" +
                    "manufacturer_code为厂商定义的设备编号，选填  \n" +
                    "imei_code为国际移动设备识别码，选填  \n" +
                    "is_enable为设备启用状态  \n" +
                    "is_online为设备在线状态  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "device_type_id为设备类型id，选填  \n" +
                    "project_id为所属的项目的id，选填  \n" +
                    "department_id为所属的部门的id，选填  \n" +
                    "area_id为所属地区的id，选填  \n" +
                    "offset为偏移量，从0开始，默认为0  \n" +
                    "limit为从偏移量开始的记录的条数，默认为5")
    public Object listDeviceByParameter(@RequestParam(value = "device_code", required = false) String deviceCode,
                                        @RequestParam(value = "manufacturer_code", required = false) String manufacturerCode,
                                        @RequestParam(value = "imei_code", required = false) String imeiCode,
                                        @RequestParam(value = "enable_state") DeviceEnableStateQuery enableStateQuery,
                                        @RequestParam(value = "online_state") DeviceOnlineStateQuery onlineStateQuery,
                                        @RequestParam(value = "device_type_id", required = false) String deviceTypeId,
                                        @RequestParam(value = "project_id", required = false) String projectId,
                                        @RequestParam(value = "department_id", required = false) String departmentId,
                                        @RequestParam(value = "area_id", required = false) String areaId,
                                        @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                        @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                        @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                        @RequestParam(value = "size", defaultValue = BaseString.defaultLimit) int size) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, size, new Sort(Sort.Direction.ASC, "deviceCode"));
        return ResponseData.ok(deviceService.listDeviceByParameter(deviceCode, manufacturerCode, imeiCode, enableStateQuery, onlineStateQuery, startQueryTime, endQueryTime, deviceTypeId, projectId, departmentId, areaId, pageRequest));
    }
}
