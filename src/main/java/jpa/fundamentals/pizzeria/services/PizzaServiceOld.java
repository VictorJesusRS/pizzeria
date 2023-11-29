package jpa.fundamentals.pizzeria.services;

import jpa.fundamentals.pizzeria.persistense.entity.PizzaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceOld {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PizzaServiceOld(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PizzaEntity> getAll() {
        return jdbcTemplate.query("select * from pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
    }
}
