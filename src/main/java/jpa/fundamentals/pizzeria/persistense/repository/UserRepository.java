package jpa.fundamentals.pizzeria.persistense.repository;

import jpa.fundamentals.pizzeria.persistense.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
