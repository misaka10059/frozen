package ccsah.frozen.firecontrol.common.message;

import ccsah.frozen.firecontrol.domain.dto.AlarmRecordDetailDto;
import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.knowledge.DeviceState;
import ccsah.frozen.firecontrol.service.AlarmRecordService;
import ccsah.frozen.firecontrol.service.BaseService;
import ccsah.frozen.firecontrol.service.EquipmentService;
import ccsah.frozen.firecontrol.service.MessageAlarmProviderService;
import ccsah.frozen.iot.domain.dto.alarm.AlarmDetailDto;
import ccsah.frozen.iot.domain.knowledge.DeviceRunState;
import ccsfr.message.BaseMessageListener;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/31 9:37
 * DESC 用于监听报警的详细信息，并根据内容判断是否为报警信息
 */
@Component
public class TopicMessageDetailListener extends BaseMessageListener {

    @Autowired
    private Gson gson;

    @Autowired
    private BaseService baseService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AlarmRecordService alarmRecordService;

    @Autowired
    private MessageAlarmProviderService alarmProviderService;

    @Override
    @JmsListener(destination = "frozen_alarm_message_detail")
    public void onMessage(String message) {
        System.out.println("========================================");
        System.out.println("activemq receive messageDetail: " + message);
        System.out.println("========================================");

        AlarmDetailDto alarmDetailDto = gson.fromJson(message, AlarmDetailDto.class);
        Device device = baseService.getEquipmentByCode(alarmDetailDto.getDeviceCode());
        if (alarmDetailDto.getFireState().equals(DeviceRunState.TURN_ALARM.toString())) {
            equipmentService.updateEquipmentState(alarmDetailDto.getDeviceCode(), DeviceState.ALARMING);
            AlarmRecordDetailDto alarmRecordDetailDto = alarmRecordService.addAlarmRecord(
                    device.getId(),
                    alarmDetailDto.getAlarmContent(),
                    alarmDetailDto.getAlarmTime());
            alarmProviderService.sendAlarmMessage(alarmRecordDetailDto.getAlarmRecordId(),
                    alarmDetailDto.getDeviceCode(),
                    alarmDetailDto.getAlarmContent(),
                    alarmDetailDto.getFireState(),
                    Integer.parseInt(alarmDetailDto.getBaseVoltage()),
                    Integer.parseInt(alarmDetailDto.getSignalIntensity()),
                    alarmDetailDto.getAlarmTime());
        }
        if (alarmDetailDto.getFireState().equals(DeviceRunState.RETURN_TO_NORMAL_WORK.toString())) {
            equipmentService.updateEquipmentState(alarmDetailDto.getDeviceCode(), DeviceState.NORMAL_WORK);
        }
    }
}
