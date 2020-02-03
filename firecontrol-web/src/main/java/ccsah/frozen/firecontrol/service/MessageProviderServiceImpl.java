package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.message.TopicMessageProvider;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 10:22
 * DESC
 */
@Service
public class MessageProviderServiceImpl implements MessageProviderService {

    @Autowired
    private TopicMessageProvider messageProvider;

    @Override
    public void sendInspectionMessage(String deviceId,
                                      String deviceCode,
                                      String pointState,
                                      String alarmAreaId,
                                      String alarmAreaName) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("deviceId", deviceId);
        jsonObject.addProperty("deviceCode", deviceCode);
        jsonObject.addProperty("inspectionPointState", pointState);
        jsonObject.addProperty("alarmAreaId", alarmAreaId);
        jsonObject.addProperty("alarmAreaName", alarmAreaName);
        String pointMessage = jsonObject.toString();
        System.out.println("---------pointMessage:" + pointMessage);
        messageProvider.send(pointMessage);
    }
}
