package com.fa.trade_exe_service.kafka.producer;


import com.fin.commo_event_lib.events.TradeExecutedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeExecutedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishExecutedTrade(
            TradeExecutedEvent event) {

        kafkaTemplate.send(
                "trade-executed",
                event.getOrderId(),
                event
        );
    }
}
