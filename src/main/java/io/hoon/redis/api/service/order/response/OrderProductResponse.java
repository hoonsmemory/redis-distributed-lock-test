package io.hoon.redis.api.service.order.response;

import io.hoon.redis.domain.orderproduct.OrderProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductResponse {

    private long id;
    private String name;
    private int price;
    private int quantity;
    private int totalPrice;

    @Builder
    private OrderProductResponse(long id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

    public static OrderProductResponse of(OrderProduct orderProduct) {
        return OrderProductResponse.builder()
                                   .id(orderProduct.getProduct().getId())
                                   .name(orderProduct.getProduct().getName())
                                   .price(orderProduct.getProduct().getPrice())
                                   .quantity(orderProduct.getQuantity())
                                   .build();
    }
}
