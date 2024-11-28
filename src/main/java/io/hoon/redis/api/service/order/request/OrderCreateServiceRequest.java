package io.hoon.redis.api.service.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateServiceRequest {

    private List<ProductPurchase> productPurchases;

    @Builder
    private OrderCreateServiceRequest(List<ProductPurchase> productPurchases) {
        if (productPurchases == null || productPurchases.isEmpty()) {
            throw new IllegalArgumentException("상품 정보는 최소 하나 이상이어야 합니다.");
        }
        this.productPurchases = productPurchases;
    }
}