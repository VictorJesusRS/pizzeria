package jpa.fundamentals.pizzeria.persistense.repository;

import jpa.fundamentals.pizzeria.persistense.entity.OrderEntity;
import jpa.fundamentals.pizzeria.persistense.projection.OrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);
    List<OrderEntity> findAllByMethodIn(List<Character> methods);

    @Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id", nativeQuery = true)
    List<OrderEntity> findCustomerOrders(@Param("id") String idCustomer);

    @Query(value =
            "SELECT po.id_order AS idOrder, cu.name AS customerName, po.date AS orderDate, " +
            "       po.total AS orderTotal, GROUP_CONCAT(pi.name) AS pizzaNames " +
            "FROM   pizza_order po " +
            "   INNER JOIN customer cu ON po.id_customer = cu.id_customer " +
            "   INNER JOIN order_item oi ON po.id_order = oi.id_order " +
            "   INNER JOIN pizza pi ON oi.id_pizza = pi.id_pizza " +
            "WHERE  po.id_order = :idOrder " +
            "GROUP BY po.id_order, cu.name, po.date, po.total", nativeQuery = true)
    OrderSummary findSummary(@Param("idOrder") Long idOrder);

    @Procedure(value = "take_random_pizza_order", outputParameterName = "order_taken")
    boolean saveRandomOrder(@Param("id_customer") String idCustomer, @Param("method") String method);
}
