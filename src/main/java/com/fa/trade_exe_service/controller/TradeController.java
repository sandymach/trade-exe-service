package com.fa.trade_exe_service.controller;

import com.fa.trade_exe_service.repository.TradeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeOrderRepository repository;

    @GetMapping("/{orderId}")
    public Object getTrade(
            @PathVariable String orderId) {

        return repository.findByOrderId(orderId);
    }
}
