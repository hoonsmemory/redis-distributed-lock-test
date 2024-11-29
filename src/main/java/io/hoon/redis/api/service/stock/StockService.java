package io.hoon.redis.api.service.stock;

import io.hoon.redis.api.service.stock.exception.StockInsufficientException;
import io.hoon.redis.api.service.stock.response.StockResponse;
import io.hoon.redis.domain.stock.Stock;
import io.hoon.redis.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public void deductStockQuantities(long productId, int quantity) {

        // 1. 재고 데이터 조회
        Stock stock = stockRepository.findByProductId(productId);

        // 2. 재고 차감
        deductQuantity(productId, quantity, stock);
    }

    @Transactional
    protected void deductQuantity(long productId, int quantity, Stock stock) {
        if (stock.isQuantityLessThan(quantity)) {
            throw new StockInsufficientException("재고가 부족한 상품 ID: " + productId);
        }

        stock.deductQuantity(quantity);
    }

    @Transactional(readOnly = true)
    public List<StockResponse> findAll() {
        return stockRepository.findAll()
                              .stream()
                              .map(StockResponse::of)
                              .toList();
    }
}