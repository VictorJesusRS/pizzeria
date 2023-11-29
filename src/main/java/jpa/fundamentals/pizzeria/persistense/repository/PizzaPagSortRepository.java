package jpa.fundamentals.pizzeria.persistense.repository;

import jpa.fundamentals.pizzeria.persistense.entity.PizzaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaPagSortRepository extends ListPagingAndSortingRepository<PizzaEntity, Long> {
    Page<PizzaEntity> findByAvailableTrue(Pageable pageable);
}
