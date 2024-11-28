package io.hoon.redis.api.controller.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.hoon.redis.api.service.order.request.OrderCreateServiceRequest;
import io.hoon.redis.api.service.order.request.ProductPurchase;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonRootName("orders")
public class OrderCreateRequest {
    @JsonProperty("orders") // JSON 필드 이름과 매핑
    private List<ProductPurchase> productPurchases;

    @Builder
    private OrderCreateRequest(List<ProductPurchase> productPurchases) {
        if (productPurchases == null || productPurchases.isEmpty()) {
            throw new IllegalArgumentException("상품 정보는 최소 하나 이상이어야 합니다.");
        }
        this.productPurchases = productPurchases;
    }

    public List<ProductPurchase> getProductPurchases() {
        return productPurchases;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder()
                                        .productPurchases(productPurchases)
                                        .build();
    }
}
