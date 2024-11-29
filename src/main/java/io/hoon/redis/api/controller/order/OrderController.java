package io.hoon.redis.api.controller.order;

import io.hoon.redis.api.controller.order.request.OrderCreateRequest;
import io.hoon.redis.api.service.order.OrderService;
import io.hoon.redis.api.service.order.response.OrderResponse;
import io.hoon.redis.domain.orderproduct.PurchaseSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return orderService.createOrder(request.toServiceRequest(), registeredDateTime);
    }

    @GetMapping("/api/v1/orders")
    public List<PurchaseSummary> findPurchaseSummary() {
        return orderService.findPurchaseSummary();
    }
}
