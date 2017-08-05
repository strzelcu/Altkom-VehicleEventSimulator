package ves.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ves.model.Event;

import javax.ejb.Stateless;

/**
 * Klasa EventRepository odpowiedzialna jest za współpracę z relacyjną bazą danych
 * podczas dodawania, edycji i usuwania zdarzeń
 */
@Repository
@Stateless
public interface EventRepository extends JpaRepository<Event, Integer> {

}
