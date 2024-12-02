package io.hoon.redis.api.service.stock;

import io.hoon.redis.api.service.stock.exception.StockInsufficientException;
import io.hoon.redis.api.service.stock.response.StockResponse;
import io.hoon.redis.domain.stock.Stock;
import io.hoon.redis.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deductStockQuantities(long productId, int quantity) {
        // 1. 재고 데이터 조회
        Stock stock = stockRepository.findByProductId(productId);

        // 2. 재고 차감
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