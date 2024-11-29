package io.hoon.redis.domain.orderproduct;

import io.hoon.redis.domain.order.Order;
import io.hoon.redis.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_product")
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int toTalPrice;

    @Builder
    private OrderProduct(Order order, Product product, int quantity, int price, int toTalPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.toTalPrice = toTalPrice;
    }

    // 생성 메서드: 수량과 가격을 포함
    public static OrderProduct create(Order order, Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("구매 수량은 1 이상이어야 합니다.");
        }

        return OrderProduct.builder()
                           .order(order)
                           .product(product)
                           .quantity(quantity)
                           .price(product.getPrice())
                           .toTalPrice(calculateTotalPrice(product.getPrice(), quantity))
                           .build();
    }

    private static int calculateTotalPrice(int price, int quantity) {
        return price * quantity;
    }
}

