package com.restaurant.service;

import com.restaurant.common.exceptions.ResourceNotFoundException;
import com.restaurant.dto.RestaurantRequest;
import com.restaurant.dto.RestaurantResponse;
import com.restaurant.entity.Restaurant;
import com.restaurant.enums.Status;
import com.restaurant.mapper.RestaurantMapper;
import com.restaurant.repository.RestaurantRepo;
import com.restaurant.service.interfaces.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    private final RestaurantRepo restaurantRepo;

    @Override
    public RestaurantResponse addRestaurant(RestaurantRequest restaurant) {
        Restaurant restaurantEntity = RestaurantMapper.toEntity(restaurant);
        restaurantRepo.save(restaurantEntity);

        return RestaurantMapper.toResponse(restaurantEntity);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurant() {
        List<Restaurant> restaurants = restaurantRepo.findAll();

        return restaurants.stream()
                .map(RestaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        return RestaurantMapper.toResponse(restaurant);
    }

    @Override
    public RestaurantResponse updateRestaurantStatus(long restaurantId, Status status) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        restaurant.setStatus(status);
        restaurantRepo.save(restaurant);
        return RestaurantMapper.toResponse(restaurant);
    }
}
