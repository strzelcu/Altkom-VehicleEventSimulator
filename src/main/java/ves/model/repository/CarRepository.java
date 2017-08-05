package ves.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ves.model.Car;

import javax.ejb.Stateless;

/**
 * Klasa CarRepository odpowiedzialna jest za współpracę z relacyjną bazą danych
 * podczas dodawania, edycji i usuwania pojazdów
 */
@Repository
@Stateless
public interface CarRepository extends JpaRepository<Car, Integer> {
}
