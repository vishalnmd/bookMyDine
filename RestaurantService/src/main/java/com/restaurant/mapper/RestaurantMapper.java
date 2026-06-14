package com.restaurant.mapper;

import com.restaurant.dto.RestaurantRequest;
import com.restaurant.dto.RestaurantResponse;
import com.restaurant.entity.Restaurant;

public class RestaurantMapper {

    public static Restaurant toEntity(RestaurantRequest restaurantRequest) {
        return Restaurant.builder()
                .id(restaurantRequest.getId())
                .name(restaurantRequest.getName())
                .address(restaurantRequest.getAddress())
                .foodType(restaurantRequest.getFoodType())
                .ownerId(restaurantRequest.getOwnerId())
                .openingTime(restaurantRequest.getOpeningTime())
                .closingTime(restaurantRequest.getClosingTime())
                .restaurantStatus(restaurantRequest.getRestaurantStatus())
                .contactNo(restaurantRequest.getContactNo())
                .status(restaurantRequest.getStatus())
                .build();
    }

    public static RestaurantResponse toResponse(Restaurant  restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .foodType(restaurant.getFoodType())
                .ownerId(restaurant.getOwnerId())
                .openingTime(restaurant.getOpeningTime())
                .closingTime(restaurant.getClosingTime())
                .restaurantStatus(restaurant.getRestaurantStatus())
                .contactNo(restaurant.getContactNo())
                .status(restaurant.getStatus())
                .build();
    }
}
