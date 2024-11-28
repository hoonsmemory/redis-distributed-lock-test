package io.hoon.redis.api.service.product.response;

import io.hoon.redis.domain.product.Product;
import io.hoon.redis.domain.product.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private Long id;
    private ProductStatus status;
    private String name;
    private int price;

    @Builder
    private ProductResponse(Long id, ProductStatus status, String name, int price) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                              .id(product.getId())
                              .status(product.getStatus())
                              .name(product.getName())
                              .price(product.getPrice())
                              .build();
    }

}
