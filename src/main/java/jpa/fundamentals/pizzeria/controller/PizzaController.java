package jpa.fundamentals.pizzeria.controller;

import jpa.fundamentals.pizzeria.persistense.entity.PizzaEntity;
import jpa.fundamentals.pizzeria.services.PizzaService;
import jpa.fundamentals.pizzeria.services.dto.UpdatePizzaPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam( defaultValue = "0") Integer page,
                                                    @RequestParam( defaultValue = "8") Integer elements) {
        return ResponseEntity.ok(this.pizzaService.getAll(page, elements));
    }
    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getById(@PathVariable Long idPizza) {
        return ResponseEntity.ok(this.pizzaService.getById(idPizza));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getById(@RequestParam( defaultValue = "0") Integer page,
                                                     @RequestParam( defaultValue = "8") Integer elements,
                                                     @RequestParam( defaultValue = "price") String sortBy,
                                                     @RequestParam( defaultValue = "ASC") String sortDirection
                                                     ) {
        return ResponseEntity.ok(this.pizzaService.getAvailable(page, elements, sortBy, sortDirection));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getById(@PathVariable String name) {
        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }

    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getByIngredient(@PathVariable String ingredient) {
        return  ResponseEntity.ok(this.pizzaService.getByIngredient(ingredient));
    }

    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithoutIngredient(@PathVariable String ingredient) {
        return  ResponseEntity.ok(this.pizzaService.getWithoutIngredient(ingredient));
    }

    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable Double price) {
        return  ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }

    @PostMapping
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza) {
        if (pizza.getIdPizza() == null || this.pizzaService.exist(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza) {
        if (pizza.getIdPizza() != null && this.pizzaService.exist(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/price")
    public ResponseEntity<Void> update(@RequestBody UpdatePizzaPriceDTO dto) {
        if (this.pizzaService.exist(dto.getIdPizza())){
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable Long idPizza) {
        if (this.pizzaService.exist(idPizza)) {
            this.pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
