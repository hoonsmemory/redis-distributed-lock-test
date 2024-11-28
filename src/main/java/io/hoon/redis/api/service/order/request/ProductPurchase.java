package io.hoon.redis.api.service.order.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductPurchase {

    private Long productId;
    private int quantity;

    public ProductPurchase(Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("구매 수량은 1 이상이어야 합니다.");
        }
        this.productId = productId;
        this.quantity = quantity;
    }
}