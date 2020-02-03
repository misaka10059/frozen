package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.message.TopicMessageDetailProvider;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 14:14
 * DESC
 */
@Service
public class MessageProviderServiceImpl implements MessageProviderService {

    @Autowired
    private TopicMessageDetailProvider topicMessageDetailProvider;

    /**
     * DATE 2019/12/31 8:42
     * DESC
     */
    @Override
    public void sendAlarmMessageDetail(String deviceCode,
                                       String alarmContent,
                                       String fireState,
                                       int baseVoltage,
                                       int signalIntensity,
                                       long alarmTime) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("deviceCode", deviceCode);
        jsonObject.addProperty("alarmContent", alarmContent);
        jsonObject.addProperty("fireState", fireState);
        jsonObject.addProperty("baseVoltage", baseVoltage);
        jsonObject.addProperty("signalIntensity", signalIntensity);
        jsonObject.addProperty("alarmTime", alarmTime);
        String message = jsonObject.toString();
        System.out.println("------------------AlarmMessageDetail:" + message);
        topicMessageDetailProvider.send(message);
    }
}
