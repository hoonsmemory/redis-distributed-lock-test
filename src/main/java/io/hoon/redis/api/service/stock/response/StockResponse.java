package io.hoon.redis.api.service.stock.response;

import io.hoon.redis.domain.stock.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StockResponse {
    private Long id;
    private Long productId;
    private int quantity;

    @Builder
    private StockResponse(Long id, Long productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static StockResponse of(Stock stock) {
        return StockResponse.builder()
                            .id(stock.getId())
                            .productId(stock.getProduct()
                                            .getId())
                            .quantity(stock.getQuantity())
                            .build();
    }
}
