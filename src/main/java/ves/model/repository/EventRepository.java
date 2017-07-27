package ves.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ves.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
