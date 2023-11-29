package jpa.fundamentals.pizzeria.services;

import jpa.fundamentals.pizzeria.persistense.entity.PizzaEntity;
import jpa.fundamentals.pizzeria.persistense.repository.PizzaPagSortRepository;
import jpa.fundamentals.pizzeria.persistense.repository.PizzaRepository;
import jpa.fundamentals.pizzeria.services.dto.UpdatePizzaPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public Page<PizzaEntity> getAll(Integer page, Integer elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return this.pizzaPagSortRepository.findAll(pageRequest);
    }

    public Page<PizzaEntity> getAvailable(Integer page, Integer elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }

    public PizzaEntity getByName(String name) {
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("Pizza not found"));
    }

    public List<PizzaEntity> getByIngredient(String ingredient) {
        return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithoutIngredient(String ingredient) {
        return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getCheapest(Double price) {
        return  this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    /**
     * Get a Pizza by its Id
     * @param idPizza id assigned to the pizza
     * @return PizzaEntity
     * @author Victor
     */
    public PizzaEntity getById( Long idPizza) {
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza) {
        return this.pizzaRepository.save(pizza);
    }

    public Boolean exist(Long idPizza) {
        return this.pizzaRepository.existsById(idPizza);
    }

    public void delete(Long idPizza) {
        this.pizzaRepository.deleteById(idPizza);
    }

    //noRollbackFor it used for let pass son exception without rolling back the transaction
    //@Transactional(noRollbackFor = EmailApiException.class)
    @Transactional
    public void updatePrice(UpdatePizzaPriceDTO dto) {
        this.pizzaRepository.updatePrice(dto);
    }

}
