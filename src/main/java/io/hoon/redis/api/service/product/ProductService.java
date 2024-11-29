package io.hoon.redis.api.service.product;

import io.hoon.redis.api.service.order.request.ProductPurchase;
import io.hoon.redis.api.service.product.exception.ProductNotFoundException;
import io.hoon.redis.domain.product.Product;
import io.hoon.redis.domain.product.ProductRepository;
import io.hoon.redis.domain.product.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public List<Product> findAllByIdInAndStatus(List<ProductPurchase> purchases, ProductStatus status) {
        List<Long> productIds = purchases.stream()
                                         .map(ProductPurchase::getProductId)
                                         .collect(Collectors.toList());

        List<Product> products = productRepository.findAllByIdInAndStatus(productIds, status);

        // 판매중이지 않은 상품 ID 확인
        List<Long> missingProductIds = productIds.stream()
                                                 .filter(id -> products.stream()
                                                                       .noneMatch(product -> product.getId().equals(id)))
                                                 .toList();

        // 구매예정인 상품 중 판매하지 않는 상품이 있을 경우 예외 발생
        if (!missingProductIds.isEmpty()) {
            throw new ProductNotFoundException("다음 상품은 판매 중인 상품이 아닙니다. : " + missingProductIds);
        }

        return products;
    }
}
