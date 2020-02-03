package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.domain.dto.AlarmRecordDetailDto;
import ccsah.frozen.firecontrol.domain.dto.AlarmRecordListDto;
import ccsah.frozen.firecontrol.domain.knowledge.ConfirmedType;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlarmRecordService {

    AlarmRecordDetailDto addAlarmRecord(String deviceId,
                                        String alarmContent,
                                        long alarmTime);

    AlarmRecordDetailDto deletedAlarmRecord(String alarmRecordId);

    AlarmRecordDetailDto updateAlarmRecordMessage(String alarmRecordId,
                                                  String alarmReceiverId,
                                                  long alarmReceiveTime,
                                                  String confirmedPersonId,
                                                  ConfirmedType confirmedType,
                                                  String confirmedResult,
                                                  long confirmedTime,
                                                  String eventScene);

    AlarmRecordDetailDto updateAlarmReceiveMessage(String alarmRecordId,
                                                   String alarmReceiverId,
                                                   long alarmReceiveTime);

    AlarmRecordDetailDto updateAlarmConfirmedMessage(String alarmRecordId,
                                                     String confirmedPersonId,
                                                     ConfirmedType confirmedType,
                                                     String confirmedResult,
                                                     long confirmedTime,
                                                     String eventScene);

    AlarmRecordDetailDto getAlarmRecordDetail(String alarmRecordId);

    List<AlarmRecordListDto> listAlarmRecordByEquipment(String equipmentId);

    PageData<AlarmRecordListDto> listAlarmRecordByParameter(String deviceId,
                                                            String alarmContent,
                                                            String alarmReceiverId,
                                                            String confirmedPersonId,
                                                            String confirmedResult,
                                                            long startQueryTime,
                                                            long endQueryTime,
                                                            Pageable pageRequest);
}
