package ccsah.frozen.firecontrol.controller;

import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.domain.knowledge.DeviceState;
import ccsah.frozen.firecontrol.service.EquipmentService;
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
 * DATE 2019/12/25
 * DESC
 */
@Api(value = "/device")
@RequestMapping(value = "/equipment")
@RestController
public class EquipmentController extends BaseApiController {

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(value = "/add_equipment", method = RequestMethod.POST)
    @ApiOperation(value = "添加设备")
    public Object addEquipment(@RequestParam("device_code") String deviceCode,
                               @RequestParam("alarm_area_id") String alarmAreaId,
                               @RequestParam("device_state") DeviceState deviceState) {
        return ResponseData.ok(equipmentService.addEquipment(deviceCode, alarmAreaId, deviceState));
    }

    @RequestMapping(value = "/delete_equipment", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除设备")
    public Object deleteEquipment(@RequestParam("equipment_id") String equipmentId) {
        return ResponseData.ok(equipmentService.deleteEquipment(equipmentId));
    }

    @RequestMapping(value = "/update_equipment_state", method = RequestMethod.POST)
    @ApiOperation(value = "手动改变设备的状态：NORMAL_WORK,ALARMING，UNDER_REPAIR")
    public Object updateEquipmentState(@RequestParam("device_code") String deviceCode,
                                       @RequestParam("device_state") DeviceState deviceState) {
        return ResponseData.ok(equipmentService.updateEquipmentState(deviceCode, deviceState));
    }

    @RequestMapping(value = "/get_smoke_detector_detail", method = RequestMethod.POST)
    @ApiOperation(value = "获取烟感设备的详细信息")
    public Object getSmokeDetectorDetail(@RequestParam("device_code") String deviceCode) {
        return ResponseData.ok(equipmentService.getSmokeDetectorDetail(deviceCode));
    }

    @RequestMapping(value = "/list_equipment_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "device_code为设备的编号  \n" +
                    "device_state为设备的状态  \n" +
                    "alarm_area_id为设备所属报警区域的id  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为分页的偏移量  \n" +
                    "limit为分页每页显示的记录数")
    public Object listEquipmentByParameter(@RequestParam(value = "device_code", required = false) String deviceCode,
                                           @RequestParam(value = "device_state") DeviceState deviceState,
                                           @RequestParam(value = "alarm_area_id", required = false) String alarmAreaId,
                                           @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                           @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                           @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                           @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.DESC, "deviceCode"));
        return ResponseData.ok(
                equipmentService.listEquipmentByParameter(
                        deviceCode,
                        deviceState,
                        alarmAreaId,
                        startQueryTime,
                        endQueryTime,
                        pageRequest));
    }

    @RequestMapping(value = "/list_equipment_can_be_added_by_alarm_area_id", method = RequestMethod.GET)
    @ApiOperation(
            value = "查询指定地区下可添加的设备列表",
            notes = "指定地区为AlarmArea,可添加的设备列表是查询AlarmArea对应的Area中所拥有的设备，返回在AlarmArea中未添加的设备列表")
    public Object listEquipmentCanBeAddedByAlarmAreaId(@RequestParam("alarm_area_id") String alarmAreaId) {
        return ResponseData.ok(equipmentService.listEquipmentCanBeAddedByAlarmAreaId(alarmAreaId));
    }
}
