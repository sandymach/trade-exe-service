package com.fa.trade_exe_service.broker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerResponse {

    private boolean success;

    private BigDecimal executionPrice;

    private String rejectionReason;
}
