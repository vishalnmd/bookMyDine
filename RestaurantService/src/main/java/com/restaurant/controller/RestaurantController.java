package com.restaurant.controller;

import com.restaurant.dto.RestaurantRequest;
import com.restaurant.dto.RestaurantResponse;
import com.restaurant.service.interfaces.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
    private final IRestaurantService restaurantService;

    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantResponse> saveRestaurant(@RequestBody RestaurantRequest request) {
        logger.info("saveRestaurant restaurant:{}", request);

        RestaurantResponse response = restaurantService.addRestaurant(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        logger.info("getAllRestaurant restaurants");

        List<RestaurantResponse> responseList = restaurantService.getAllRestaurant();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable("id") long id) {
        logger.info("getRestaurantById restaurants");

        RestaurantResponse responseList = restaurantService.getRestaurantById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseList);
    }

    @PatchMapping
    public ResponseEntity<RestaurantResponse> updateRestaurant(@RequestBody RestaurantRequest request) {
        logger.info("updateRestaurant restaurant:{}", request);

        RestaurantResponse response =  restaurantService.updateRestaurantStatus(request.getId(), request.getStatus());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
