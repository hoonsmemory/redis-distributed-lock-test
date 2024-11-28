package io.hoon.redis.api.service.order;

import io.hoon.redis.api.service.order.request.OrderCreateServiceRequest;
import io.hoon.redis.api.service.order.request.ProductPurchase;
import io.hoon.redis.api.service.order.response.OrderResponse;
import io.hoon.redis.api.service.product.ProductService;
import io.hoon.redis.api.service.stock.StockService;
import io.hoon.redis.domain.order.Order;
import io.hoon.redis.domain.order.OrderRepository;
import io.hoon.redis.domain.product.Product;
import io.hoon.redis.domain.product.ProductStatus;
import io.hoon.redis.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final StockService stockService;

    @Transactional
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<ProductPurchase> purchases = request.getProductPurchases();

        // 1. 구매 상품 조회
        List<Product> products = productService.findProductsBy(purchases);

        // 2. 재고 차감
        stockService.deductStockQuantities(purchases);

        // 3. 주문
        Order order = Order.create(products, purchases, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

}