package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.message.TopicMessageAlarmProvider;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AUTHOR MisakaNetwork
 * DATE 2020/1/7 15:38
 * DESC
 */
@Service
public class MessageAlarmProviderServiceImpl implements MessageAlarmProviderService {

    @Autowired
    private TopicMessageAlarmProvider alarmProvider;

    @Override
    public void sendAlarmMessage(String alarmRecordId,
                                 String deviceCode,
                                 String alarmContent,
                                 String fireState,
                                 int baseVoltage,
                                 int signalIntensity,
                                 long alarmTime) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("alarmRecordId", alarmRecordId);
        jsonObject.addProperty("deviceCode", deviceCode);
        jsonObject.addProperty("alarmContent", alarmContent);
        jsonObject.addProperty("fireState", fireState);
        jsonObject.addProperty("baseVoltage", baseVoltage);
        jsonObject.addProperty("signalIntensity", signalIntensity);
        jsonObject.addProperty("alarmTime", alarmTime);
        String alarmMessage = jsonObject.toString();
        System.out.println("---------alarmMessage:" + alarmMessage);
        alarmProvider.send(alarmMessage);
    }
}
