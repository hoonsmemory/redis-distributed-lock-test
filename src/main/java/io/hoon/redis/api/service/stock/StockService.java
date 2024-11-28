package io.hoon.redis.api.service.stock;

import io.hoon.redis.api.service.order.request.ProductPurchase;
import io.hoon.redis.api.service.stock.exception.StockInsufficientException;
import io.hoon.redis.domain.stock.Stock;
import io.hoon.redis.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public void deductStockQuantities(List<ProductPurchase> purchases) {
        // 1. 재고 데이터 조회
        Map<Long, Stock> stockMap = createStockMapBy(purchases);

        // 2. 재고 차감
        for (ProductPurchase purchase : purchases) {
            Stock stock = stockMap.get(purchase.getProductId());
            int quantity = purchase.getQuantity();

            if (stock.isQuantityLessThan(quantity)) {
                throw new StockInsufficientException("재고가 부족한 상품 ID: " + purchase.getProductId());
            }

            stock.deductQuantity(quantity);
        }
    }

    private Map<Long, Stock> createStockMapBy(List<ProductPurchase> purchases) {
        List<Long> productIds = purchases.stream()
                                         .map(ProductPurchase::getProductId)
                                         .collect(Collectors.toList());
        List<Stock> stocks = stockRepository.findAllByProductIdIn(productIds);

        return stocks.stream()
                     .collect(Collectors.toMap(Stock::getId, s -> s));
    }
}
