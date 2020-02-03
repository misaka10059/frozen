package ccsah.frozen.iot.common.message;

import ccsfr.message.BaseMessageProvider;
import ccsfr.message.activemq.ActiveMQMode;
import ccsfr.message.activemq.ActiveMQPoster;
import org.springframework.stereotype.Component;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/31 8:40
 * DESC
 */
@Component
@ActiveMQPoster(destination = "frozen_alarm_message_detail", mode = ActiveMQMode.TOPIC)
public class TopicMessageDetailProvider extends BaseMessageProvider {
}
