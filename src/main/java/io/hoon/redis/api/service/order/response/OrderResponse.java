package io.hoon.redis.api.service.order.response;

import io.hoon.redis.domain.order.Order;
import io.hoon.redis.api.service.product.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {

    private Long id;
    private LocalDateTime registeredDateTime;
    private int totalPrice;
    private List<OrderProductResponse> products;

    @Builder
    private OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<OrderProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                            .id(order.getId())
                            .registeredDateTime(order.getRegisteredDateTime())
                            .totalPrice(order.getOrderProducts().stream().mapToInt(orderProduct -> orderProduct.getToTalPrice()).sum())
                            .products(order.getOrderProducts()
                                                .stream()
                                                .map(OrderProductResponse::of)
                                                .collect(Collectors.toList())
                            )
                            .build();
    }

}
