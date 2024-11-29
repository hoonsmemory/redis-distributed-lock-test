package io.hoon.redis.domain.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("""
       SELECT new io.hoon.redis.domain.orderproduct.PurchaseSummary(p.id, SUM(op.quantity))
       FROM OrderProduct op
       JOIN op.product p
       GROUP BY p.id
       """)
    List<PurchaseSummary> findPurchaseSummary();
}