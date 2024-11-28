package io.hoon.redis.service.order;

import io.hoon.redis.api.service.order.OrderService;
import io.hoon.redis.api.service.order.request.ProductPurchase;
import io.hoon.redis.domain.product.Product;
import io.hoon.redis.domain.product.ProductRepository;
import io.hoon.redis.domain.product.ProductType;
import io.hoon.redis.domain.stock.Stock;
import io.hoon.redis.domain.stock.StockRepository;
import io.hoon.redis.api.service.order.request.OrderCreateServiceRequest;
import io.hoon.redis.api.service.order.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static io.hoon.redis.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct("인기 한정 상품 1", POPULAR, 15000);
        Product product2 = createProduct("인기 한정 상품 2", POPULAR, 10000);
        Product product3 = createProduct("기본 상품 1", REGULAR, 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create(product1, 10);
        Stock stock2 = Stock.create(product2, 5);
        Stock stock3 = Stock.create(product3, 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // 구매 요청: 상품 ID와 수량
        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
                                                                     .productPurchases(List.of(
                                                                             new ProductPurchase(product1.getId(), 2),
                                                                             new ProductPurchase(product2.getId(), 1),
                                                                             new ProductPurchase(product3.getId(), 3)
                                                                     ))
                                                                     .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("totalPrice")
                .isEqualTo(55000); // 총 가격: 15000 * 2 + 10000 * 1 + 5000 * 3 = 55000
        assertThat(orderResponse.getProducts())
                .hasSize(3)
                .extracting("id", "name","price", "quantity", "totalPrice")
                .containsExactlyInAnyOrder(
                        tuple(product1.getId(), "인기 한정 상품 1",15000, 2, 30000),
                        tuple(product2.getId(), "인기 한정 상품 2",10000, 1, 10000),
                        tuple(product3.getId(), "기본 상품 1",5000, 3, 15000)
                );

    }


    private Product createProduct(String name, ProductType type, int price) {
        return Product.builder()
                      .name(name)
                      .type(type)
                      .price(price)
                      .build();
    }
}