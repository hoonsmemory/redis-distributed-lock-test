package io.hoon.redis.domain.stock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
    Stock findByProductId(Long productId);
    List<Stock> findAll();
}
