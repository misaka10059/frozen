package ccsah.frozen.firecontrol.common.message;

import ccsah.frozen.firecontrol.domain.dto.InspectionPointMessageDto;
import ccsfr.message.BaseMessageListener;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AUTHOR MisakaNetwork
 * DATE 2020/1/3 17:14
 * DESC
 */
@Component
public class TopicMessagePointListener extends BaseMessageListener {

    @Autowired
    private Gson gson;

    @Override
    public void onMessage(String s) {
        InspectionPointMessageDto pointMessageDto = gson.fromJson(s, InspectionPointMessageDto.class);
        System.out.println(pointMessageDto.getDeviceId());
        System.out.println(pointMessageDto.getDeviceCode());
        System.out.println(pointMessageDto.getInspectionPointState());
    }
}
