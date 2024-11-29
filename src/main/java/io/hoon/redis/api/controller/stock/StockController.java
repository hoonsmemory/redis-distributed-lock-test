package io.hoon.redis.api.controller.stock;

import io.hoon.redis.api.service.stock.StockService;
import io.hoon.redis.api.service.stock.response.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockService stockService;

    @GetMapping("/api/v1/stocks")
    public List<StockResponse> findAll() {
        return stockService.findAll();
    }
}
