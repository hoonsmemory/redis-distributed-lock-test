package io.hoon.redis.api.service.order;

import io.hoon.redis.api.service.order.request.OrderCreateServiceRequest;
import io.hoon.redis.api.service.order.request.ProductPurchase;
import io.hoon.redis.api.service.order.response.OrderResponse;
import io.hoon.redis.api.service.product.ProductService;
import io.hoon.redis.api.service.stock.StockService;
import io.hoon.redis.domain.order.Order;
import io.hoon.redis.domain.order.OrderRepository;
import io.hoon.redis.domain.orderproduct.OrderProductRepository;
import io.hoon.redis.domain.orderproduct.PurchaseSummary;
import io.hoon.redis.domain.product.Product;
import io.hoon.redis.domain.product.ProductStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;
    private final StockService stockService;
    private final RedissonClient redissonClient;

    @Transactional
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<ProductPurchase> purchases = request.getProductPurchases();

        // 1. 구매 가능한 상품 조회
        List<Product> products = productService.findAllByIdInAndStatus(purchases, ProductStatus.AVAILABLE);

        // 2. 재고 차감
        purchases.stream()
                 .forEach(this::deductStockQuantitiesWithLock);

        // 3. 주문
        Order order = Order.create(products, purchases, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantitiesWithLock(ProductPurchase purchase) {
        String stockCheckLockId = "stock_check_lock_" + purchase.getProductId();
        RLock stockCheckLock = redissonClient.getLock(stockCheckLockId);
        try {
            if (stockCheckLock.tryLock(20, 10, TimeUnit.SECONDS)) {
                stockService.deductStockQuantities(purchase.getProductId(), purchase.getQuantity());
            } else {
                log.info("{} 락을 획득하지 못 했습니다.", stockCheckLockId);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            stockCheckLock.unlock();
        }
    }

    private void deductStockQuantities(ProductPurchase purchase) {
        stockService.deductStockQuantities(purchase.getProductId(), purchase.getQuantity());
    }

    @Transactional(readOnly = true)
    public List<PurchaseSummary> findPurchaseSummary() {
        return orderProductRepository.findPurchaseSummary();
    }

}