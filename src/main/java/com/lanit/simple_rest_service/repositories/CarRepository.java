package com.lanit.simple_rest_service.repositories;

import com.lanit.simple_rest_service.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByOwnerId(long id);

    @Query(value = "SELECT count(*) as uniquevendorcount FROM (SELECT UPPER(LEFT(model, LOCATE('-', model) - 1)) as vendor  FROM cars group by vendor) as c; ", nativeQuery = true)
    Long countUniqueNames();

}
