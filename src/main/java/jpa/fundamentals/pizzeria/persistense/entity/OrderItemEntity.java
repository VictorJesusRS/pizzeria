package jpa.fundamentals.pizzeria.persistense.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@IdClass(OrderItemId.class)
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item", nullable = false)
    private Long idItem;

    @Id
    @Column(name = "id_order", nullable = false)
    private Long idOrder;

    @Column(name = "id_pizza", nullable = false)
    private Long idPizza;

    @Column(nullable = false, columnDefinition = "Decimal(2, 1)")
    private Double quantity;

    @Column(nullable = false, columnDefinition = "Decimal(5, 2)")
    private  Double price;

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", insertable = false, updatable = false)
    @JsonIgnore
    private OrderEntity order;

    @OneToOne
    @JoinColumn(name = "id_pizza", referencedColumnName = "id_pizza", insertable = false, updatable = false)
    private PizzaEntity pizza;
}
