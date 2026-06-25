package com.fa.trade_exe_service.service;

import com.fa.trade_exe_service.broker.BrokerClient;
import com.fa.trade_exe_service.broker.BrokerResponse;
import com.fa.trade_exe_service.entity.OrderSide;
import com.fa.trade_exe_service.entity.OrderStatus;
import com.fa.trade_exe_service.entity.TradeOrder;
import com.fa.trade_exe_service.kafka.producer.TradeExecutedProducer;
import com.fa.trade_exe_service.kafka.producer.TradeRejectedProducer;
import com.fa.trade_exe_service.repository.TradeOrderRepository;
import com.fin.commo_event_lib.events.EventVersion;
import com.fin.commo_event_lib.events.TradeExecutedEvent;
import com.fin.commo_event_lib.events.TradeRejectedEvent;
import com.fin.commo_event_lib.events.TradeRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TradeExecutionService {

    private final TradeOrderRepository repository;
    private final BrokerClient brokerClient;
    private final TradeExecutedProducer executedProducer;
    private final TradeRejectedProducer rejectedProducer;

    public void processTrade(
            TradeRequestEvent request) {

        TradeOrder order = TradeOrder.builder()
                .orderId(request.getEventId())
                .portfolioId(request.getPortfolioId())
                .symbol(request.getSymbol())
                .quantity(request.getQuantity())
                .requestedPrice(request.getPrice())
                .side(OrderSide.valueOf(request.getSide()))
                .status(OrderStatus.PENDING)
                .build();
        System.out.println("TradeOrder received saving to db starting 1"+order);

      //  repository.save(order);

        BrokerResponse brokerResponse =
                brokerClient.executeTrade(request);

        if (brokerResponse.isSuccess()) {

            order.setStatus(OrderStatus.EXECUTED);
            order.setExecutedPrice(
                    brokerResponse.getExecutionPrice());
            order.setExecutedAt(LocalDateTime.now());
            System.out.println("TradeOrder received saving to db 2 :  "+order);
            repository.save(order);

            TradeExecutedEvent event =
                    TradeExecutedEvent.builder()
                            .eventId(request.getEventId())
                            .orderId(request.getEventId())
                            .portfolioId(request.getPortfolioId())
                            .symbol(request.getSymbol())
                            .quantity(request.getQuantity())
                            .executionPrice(
                                    brokerResponse.getExecutionPrice())
                            .side(request.getSide())
                            .version(EventVersion.V1)
                            .build();

            executedProducer.publishExecutedTrade(event);

        } else {

            order.setStatus(OrderStatus.REJECTED);
            order.setRejectionReason(
                    brokerResponse.getRejectionReason());

            repository.save(order);

            TradeRejectedEvent event =
                    TradeRejectedEvent.builder()
                            .eventId(request.getEventId())
                            .orderId(request.getEventId())
                            .portfolioId(request.getPortfolioId())
                            .symbol(request.getSymbol())
                            .reason(
                                    brokerResponse.getRejectionReason())
                            .version(EventVersion.V1)
                            .build();

            rejectedProducer.publishRejectedTrade(event);
        }
    }
}
