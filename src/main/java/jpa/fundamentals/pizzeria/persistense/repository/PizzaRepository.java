package jpa.fundamentals.pizzeria.persistense.repository;

import jpa.fundamentals.pizzeria.persistense.entity.PizzaEntity;
import jpa.fundamentals.pizzeria.services.dto.UpdatePizzaPriceDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends CrudRepository<PizzaEntity, Long> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();
    //Get the first element of the query
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);
    //Get the first 3 element of the query
    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(Double price);

    int countByVeganTrue();

    @Query(value =
            "UPDATE pizza " +
            "SET price = :#{#newPizzaPrice.newPrice} " +
            "WHERE id_pizza = :#{#newPizzaPrice.idPizza}", nativeQuery = true)
    @Modifying
    void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDTO newPizzaPrice);
}
