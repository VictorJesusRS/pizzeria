package jpa.fundamentals.pizzeria.services;

import jpa.fundamentals.pizzeria.persistense.entity.OrderEntity;
import jpa.fundamentals.pizzeria.persistense.projection.OrderSummary;
import jpa.fundamentals.pizzeria.persistense.repository.OrderRepository;
import jpa.fundamentals.pizzeria.services.dto.RandomOrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final Character DELIVERY = 'D';
    private final Character CARRYOUT = 'C';
    private final Character ON_SITE = 'S';
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll() {
        return (List<OrderEntity>) this.orderRepository.findAll();
    }

    public List<OrderEntity> getAllToday() {
        LocalDateTime date = LocalDate.now().atTime(0, 0);
        return (List<OrderEntity>) this.orderRepository.findAllByDateAfter(date);
    }

    public List<OrderEntity> getOutside() {
        List<Character> methods = Arrays.asList(DELIVERY, CARRYOUT);
        return (List<OrderEntity>) this.orderRepository.findAllByMethodIn(methods);
    }

    public List<OrderEntity> getCustomerOrders(String idCustomer) {
        return  (List<OrderEntity>) this.orderRepository.findCustomerOrders(idCustomer);
    }

    public OrderSummary getSummary(Long idOrder) {
        return this.orderRepository.findSummary(idOrder);
    }

    @Transactional
    public Boolean saveRandomOrder(RandomOrderDTO randomOrderDTO) {
        return this.orderRepository.saveRandomOrder(randomOrderDTO.getIdCustomer(), randomOrderDTO.getMethod());
    }
}
