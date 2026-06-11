package com.fa.trade_exe_service.repository;

import com.fa.trade_exe_service.entity.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeOrderRepository
        extends JpaRepository<TradeOrder, Long> {

    Optional<TradeOrder> findByOrderId(String orderId);
}
