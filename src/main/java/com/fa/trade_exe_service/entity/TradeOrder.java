package com.fa.trade_exe_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    private Long portfolioId;

    private String symbol;

    private Long quantity;

    private BigDecimal requestedPrice;

    private BigDecimal executedPrice;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String rejectionReason;

    private LocalDateTime createdAt;

    private LocalDateTime executedAt;

    @Version
    private Long version;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
