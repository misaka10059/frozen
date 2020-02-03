package ccsah.frozen.firecontrol.controller;

import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.domain.knowledge.ConfirmedType;
import ccsah.frozen.firecontrol.service.AlarmRecordService;
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
 * DATE 2019/12/27 12:44
 * DESC
 */
@Api(value = "alarm_record")
@RequestMapping(value = "alarm_record")
@RestController
public class AlarmRecordController extends BaseApiController {

    @Autowired
    private AlarmRecordService recordService;

    @RequestMapping(value = "add_alarmRecord", method = RequestMethod.POST)
    @ApiOperation(
            value = "手动测试添加报警记录",
            notes = "device_id为报警设备的id  \n" +
                    "alarm_content为报警内容  \n" +
                    "alarm_time为报警时间的毫秒数  ")
    public Object addAlarmRecord(@RequestParam("device_id") String deviceId,
                                 @RequestParam("alarm_content") String alarmContent,
                                 @RequestParam("alarm_time") long alarmTime) {
        return ResponseData.ok(recordService.addAlarmRecord(deviceId, alarmContent, alarmTime));
    }

    @RequestMapping(value = "delete_alarm_record", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除报警记录")
    public Object deleteAlarmRecord(@RequestParam("alarm_record_id") String alarmRecordId) {
        return ResponseData.ok(recordService.deletedAlarmRecord(alarmRecordId));
    }

    @RequestMapping(value = "update_alarm_record", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新报警记录信息",
            notes = "alarm_record_id为报警记录的id  \n" +
                    "alarm_receiver_id为此条报警记录接收人的id  \n" +
                    "alarm_receive_time为接收人接收到报警记录时的时间的毫秒数  \n" +
                    "confirmed_person_id为确认报警区域现场情况的员工id  \n" +
                    "confirmed_type为报警区域现场情况确认结果的类型  \n" +
                    "confirmed_result为报警区域现场情况的确认结果的额外信息  \n" +
                    "confirmed_time为报警区域现场情况的确认时间  \n" +
                    "event_scene为报警区域确认的现场的其他信息")
    public Object updateAlarmRecord(@RequestParam("alarm_record_id") String alarmRecordId,
                                    @RequestParam("alarm_receiver_id") String alarmReceiverId,
                                    @RequestParam("alarm_receive_time") long alarmReceiveTime,
                                    @RequestParam("confirmed_person_id") String confirmedPersonId,
                                    @RequestParam("confirmed_type") ConfirmedType confirmedType,
                                    @RequestParam("confirmed_result") String confirmedResult,
                                    @RequestParam("confirmed_time") long confirmedTime,
                                    @RequestParam(value = "event_scene", required = false) String eventScene) {
        return ResponseData.ok(recordService.updateAlarmRecordMessage(
                alarmRecordId,
                alarmReceiverId,
                alarmReceiveTime,
                confirmedPersonId,
                confirmedType,
                confirmedResult,
                confirmedTime,
                eventScene));
    }

    @RequestMapping(value = "/update_alarm_receive_message", method = RequestMethod.POST)
    @ApiOperation(
            value = "单独更新对报警记录确认的信息",
            notes = "alarm_record_id为报警记录的id  \n" +
                    "alarm_receiver_id为此条报警记录接收人的id  \n" +
                    "alarm_receive_time为接收人接收到报警记录时的时间的毫秒数")
    public Object updateAlarmReceiveMessage(@RequestParam("alarm_record_id") String alarmRecordId,
                                            @RequestParam("alarm_receiver_id") String alarmReceiverId,
                                            @RequestParam("alarm_receive_time") long alarmReceiveTime) {
        return ResponseData.ok(recordService.updateAlarmReceiveMessage(
                alarmRecordId,
                alarmReceiverId,
                alarmReceiveTime));
    }

    @RequestMapping(value = "/update_alarm_confirmed_message", method = RequestMethod.POST)
    @ApiOperation(
            value = "单独更新对报警现场确认的信息",
            notes = "alarm_record_id为报警记录的id  \n" +
                    "confirmed_person_id为确认报警区域现场情况的员工id  \n" +
                    "confirmed_type为报警区域现场情况确认结果的类型  \n" +
                    "confirmed_result为报警区域现场情况的确认结果的额外信息  \n" +
                    "confirmed_time为报警区域现场情况的确认时间  \n" +
                    "event_scene为报警区域确认的现场的其他信息")
    public Object updateAlarmConfirmedMessage(@RequestParam("alarm_record_id") String alarmRecordId,
                                              @RequestParam("confirmed_person_id") String confirmedPersonId,
                                              @RequestParam("confirmed_type") ConfirmedType confirmedType,
                                              @RequestParam("confirmed_result") String confirmedResult,
                                              @RequestParam("confirmed_time") long confirmedTime,
                                              @RequestParam(value = "event_scene", required = false) String eventScene) {
        return ResponseData.ok(recordService.updateAlarmConfirmedMessage(
                alarmRecordId,
                confirmedPersonId,
                confirmedType,
                confirmedResult,
                confirmedTime,
                eventScene));
    }

    @RequestMapping(value = "get_alarm_record_detail", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条报警记录详细信息")
    public Object getAlarmRecordRecordDetail(@RequestParam("alarm_record_id") String alarmRecordId) {
        return ResponseData.ok(recordService.getAlarmRecordDetail(alarmRecordId));
    }

    @RequestMapping(value = "list_alarm_record_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "device_id为报警设备的id  \n" +
                    "alarm_content为报警内容  \n" +
                    "alarm_receiver_id为报警记录接收人的id  \n" +
                    "confirmed_person_id为确认报警区域现场情况的员工id  \n" +
                    "confirmed_result为报警区域现场情况的确认结果  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为分页的偏移量  \n" +
                    "limit为分页每页显示的记录数")
    public Object listAlarmRecordByParameter(@RequestParam(value = "device_id", required = false) String deviceId,
                                             @RequestParam(value = "alarm_content", required = false) String alarmContent,
                                             @RequestParam(value = "alarm_receiver_id", required = false) String alarmReceiverId,
                                             @RequestParam(value = "confirmed_person_id", required = false) String confirmedPersonId,
                                             @RequestParam(value = "confirmed_result", required = false) String confirmedResult,
                                             @RequestParam(value = "start_query_time", defaultValue = "0") long startQueryTime,
                                             @RequestParam(value = "end_query_time", defaultValue = "0") long endQueryTime,
                                             @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                             @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.ASC, "alarmTime"));
        return ResponseData.ok(recordService.listAlarmRecordByParameter(
                deviceId,
                alarmContent,
                alarmReceiverId,
                confirmedPersonId,
                confirmedResult,
                startQueryTime,
                endQueryTime,
                pageRequest));
    }
}
