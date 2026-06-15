package com.userservice.client;

import com.userservice.user.dto.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*@FeignClient(name = "restaurant-service", url = "http://localhost:8080")
public interface RestaurantFeignClient {

    @GetMapping("/restaurants/owner/{id}")
    List<RestaurantResponse> getAllRestaurantByOwnerId(@PathVariable("id") long ownerId);
}*/

@FeignClient(name = "RESTAURANTSERVICE")
public interface RestaurantFeignClient {

    @GetMapping("/restaurants/owner/{id}")
    List<RestaurantResponse> getAllRestaurantByOwnerId(@PathVariable("id") long ownerId);
}
