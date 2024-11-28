package io.hoon.redis.api.service.product;

import io.hoon.redis.api.service.order.request.ProductPurchase;
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

    public List<Product> findProductsBy(List<ProductPurchase> purchases) {
        List<Long> productIds = purchases.stream()
                                         .map(ProductPurchase::getProductId)
                                         .collect(Collectors.toList());
        return productRepository.findAllByIdIn(productIds);
    }
}
