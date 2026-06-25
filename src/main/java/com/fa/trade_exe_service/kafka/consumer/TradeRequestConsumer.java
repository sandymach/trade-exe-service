package com.fa.trade_exe_service.kafka.consumer;


import com.fa.trade_exe_service.service.TradeExecutionService;
import com.fin.commo_event_lib.events.TradeRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeRequestConsumer {

    private final TradeExecutionService service;

    @KafkaListener(
            topics = "trade-requests",
            groupId = "trade-execution-group")
    public void consume(
            TradeRequestEvent event) {
        System.out.println("Trade request received "+event);
        service.processTrade(event);
    }
}
