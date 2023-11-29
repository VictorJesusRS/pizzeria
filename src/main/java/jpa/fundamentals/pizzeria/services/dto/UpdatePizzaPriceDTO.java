package jpa.fundamentals.pizzeria.services.dto;

import lombok.Data;

@Data
public class UpdatePizzaPriceDTO {
    private Long idPizza;
    private Double newPrice;
}
