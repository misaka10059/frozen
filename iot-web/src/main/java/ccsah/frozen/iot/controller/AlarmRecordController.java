package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.dto.alarm.AlarmDto;
import ccsah.frozen.iot.service.AlarmRecordService;
import ccsfr.core.domain.PageOffsetRequest;
import ccsfr.core.web.BaseApiController;
import ccsfr.core.web.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/13 16:56
 * DESC
 */
@Api(value = "alarm_record")
@RequestMapping("alarm_record")
@RestController
public class AlarmRecordController extends BaseApiController {

    @Autowired
    private AlarmRecordService alarmRecordService;

    @RequestMapping(value = "/receiveAlarmRecord", method = RequestMethod.POST)
    @ApiOperation(value = "")
    public void receiveAlarmRecord(@RequestBody AlarmDto alarmMessage) {
        alarmRecordService.addAlarmRecord(alarmMessage);
    }

    /*@RequestMapping(value = "/resolve", method = RequestMethod.POST)
    public void resolve(@RequestBody AlarmDto message) {
        alarmRecordService.resolve(message);
    }*/

    @RequestMapping(value = "/delete_alarm_record", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除报警记录")
    public Object deleteAlarmRecord(@RequestParam("alarm_record_id") String alarmRecordId) {
        return ResponseData.ok(alarmRecordService.deleteAlarmRecord(alarmRecordId));
    }

    @RequestMapping(value = "/list_alarm_record_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "device_id为设备的id  \n" +
                    "start_query_time查询的开始时间  \n" +
                    "end_query_time查询的结束时间  \n" +
                    "offset为偏移量，从0开始，默认为0  \n" +
                    "limit为每页显示的记录数，默认为5")
    public Object listAlarmRecordByParameter(@RequestParam(value = "device_id", required = false) String deviceId,
                                             @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                             @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                             @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                             @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.DESC, "alarmTime"));
        return ResponseData.ok(alarmRecordService.listAlarmRecordByParameter(deviceId, startQueryTime, endQueryTime, pageRequest));
    }
}
