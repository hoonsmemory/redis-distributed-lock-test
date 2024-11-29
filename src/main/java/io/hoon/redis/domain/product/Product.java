package io.hoon.redis.domain.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Builder
    private Product(String name, int price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.status = ProductStatus.AVAILABLE; // 기본 상태는 판매중
    }

    public void updateDetails(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void markOutOfStock() {
        this.status = ProductStatus.OUT_OF_STOCK;
    }

    public void markAvailable() {
        this.status = ProductStatus.AVAILABLE;
    }
}
