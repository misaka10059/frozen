package ccsah.frozen.firecontrol.common.message;

import ccsfr.message.BaseMessageProvider;
import ccsfr.message.activemq.ActiveMQMode;
import ccsfr.message.activemq.ActiveMQPoster;
import org.springframework.stereotype.Component;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 16:18
 * DESC
 */
@Component
@ActiveMQPoster(destination = "frozen_inspection", mode = ActiveMQMode.TOPIC)
public class TopicMessageProvider extends BaseMessageProvider {
}
