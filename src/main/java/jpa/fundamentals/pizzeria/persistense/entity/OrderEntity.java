package jpa.fundamentals.pizzeria.persistense.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pizza_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {


    //No es necesario especificar el nombre de la columna en @Column
    //cuando sera traducido a snake_case, Spring hace la conversion automaticamente, es decir:
    //idOrder sera traducido como id_order.

    @Id
    @Column(name = "id_order", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @Column(name = "id_customer", nullable = false, length = 15)
    private String idCustomer;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "Decimal(6, 2)")
    private Double total;

    @Column(nullable = false, columnDefinition = "Char(1)")
    private Character method;

    @Column(name = "additional_notes", length = 200)
    private String additionalNotes;

    //Lazy make the relation data to be loaded only when its used
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer", insertable = false, updatable = false)
    //JsonIgnore make the column to be ignored on serialization
    @JsonIgnore
    private CustomerEntity customer;

    //Eager make the relation data to be loaded on the first order query
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @OrderBy("price DESC")
    private List<OrderItemEntity> orderItem;
}
