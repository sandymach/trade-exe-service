package com.fa.trade_exe_service.broker;

import com.fin.commo_event_lib.events.TradeRequestEvent;
import org.springframework.stereotype.Component;

@Component
public class BrokerClient {

    public BrokerResponse executeTrade(
            TradeRequestEvent request) {

        return BrokerResponse.builder()
                .success(true)
                .executionPrice(request.getPrice())
                .build();
    }
}
