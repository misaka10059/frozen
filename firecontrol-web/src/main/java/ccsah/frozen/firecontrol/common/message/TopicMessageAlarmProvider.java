package ccsah.frozen.firecontrol.common.message;

import ccsfr.message.BaseMessageProvider;
import ccsfr.message.activemq.ActiveMQMode;
import ccsfr.message.activemq.ActiveMQPoster;
import org.springframework.stereotype.Component;

/**
 * AUTHOR MisakaNetwork
 * DATE 2020/1/7 15:35
 * DESC
 */
@Component
@ActiveMQPoster(destination = "frozen_firecontrol_alarm",mode = ActiveMQMode.TOPIC)
public class TopicMessageAlarmProvider extends BaseMessageProvider {
}
