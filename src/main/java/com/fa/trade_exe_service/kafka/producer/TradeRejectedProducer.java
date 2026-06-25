package com.fa.trade_exe_service.kafka.producer;

import com.fin.commo_event_lib.events.TradeRejectedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeRejectedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishRejectedTrade(
            TradeRejectedEvent event) {

        kafkaTemplate.send(
                "trade-rejected",
                event.getOrderId(),
                event
        );
    }
}
