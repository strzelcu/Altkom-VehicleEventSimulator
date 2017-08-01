package ves.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ves.model.Car;

import java.util.List;
import javax.ejb.Stateless;

@Repository
@Stateless
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByTypeAndMake(String type, String make);
}
