package io.hoon.redis.domain.orderproduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PurchaseSummary {
    private Long productId;
    private Long totalQuantity;
}