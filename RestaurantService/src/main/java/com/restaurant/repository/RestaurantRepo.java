package com.restaurant.repository;

import com.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByOwnerId(long id);
}
