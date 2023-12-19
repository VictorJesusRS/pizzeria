package jpa.fundamentals.pizzeria.controller;

import jpa.fundamentals.pizzeria.persistense.entity.OrderEntity;
import jpa.fundamentals.pizzeria.persistense.projection.OrderSummary;
import jpa.fundamentals.pizzeria.services.OrderService;
import jpa.fundamentals.pizzeria.services.dto.RandomOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private  final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<List<OrderEntity>> getAll() {
        return ResponseEntity.ok(this.orderService.getAll());
    }

    @GetMapping("/today")
    public ResponseEntity<List<OrderEntity>> getAllToday() {
        return ResponseEntity.ok(this.orderService.getAllToday());
    }

    @GetMapping("/outside")
    public ResponseEntity<List<OrderEntity>> getOutside() {
        return ResponseEntity.ok(this.orderService.getOutside());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderEntity>> getCustomerOrders(@PathVariable String id) {
        return ResponseEntity.ok(this.orderService.getCustomerOrders(id));
    }

    @GetMapping("/summary/{idOrder}")
    public ResponseEntity<OrderSummary> getSummary(@PathVariable Long idOrder) {
        return ResponseEntity.ok(this.orderService.getSummary(idOrder));
    }

    @GetMapping("/random")
    public ResponseEntity<Boolean> randomOrder(@RequestBody RandomOrderDTO dto) {
        return ResponseEntity.ok(this.orderService.saveRandomOrder(dto));
    }
}
