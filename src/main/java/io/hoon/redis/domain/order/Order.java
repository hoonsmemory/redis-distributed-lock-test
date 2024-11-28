package io.hoon.redis.domain.order;

import io.hoon.redis.api.service.order.request.ProductPurchase;
import io.hoon.redis.domain.orderproduct.OrderProduct;
import io.hoon.redis.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    private Order(OrderStatus orderStatus, List<Product> products, List<ProductPurchase> purchases, LocalDateTime registeredDateTime) {
        this.orderStatus = orderStatus;
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
                                     .map(product -> OrderProduct.create(this, product, purchases.stream()
                                                                                                 .filter(purchase -> purchase.getProductId()
                                                                                                                             .equals(product.getId()))
                                                                                                 .findFirst()
                                                                                                 .map(ProductPurchase::getQuantity)
                                                                                                 .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다.")))
                                     )
                                     .collect(Collectors.toList());
    }

    public static Order create(List<Product> products, List<ProductPurchase> purchases, LocalDateTime registeredDateTime) {
        return Order.builder()
                    .orderStatus(OrderStatus.INIT)
                    .purchases(purchases)
                    .products(products)
                    .registeredDateTime(registeredDateTime)
                    .build();
    }
}
