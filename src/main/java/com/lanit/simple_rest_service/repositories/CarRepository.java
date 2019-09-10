package com.lanit.simple_rest_service.repositories;

import com.lanit.simple_rest_service.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByOwnerId(long id);

}
