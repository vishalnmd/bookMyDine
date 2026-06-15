package com.restaurant.service.interfaces;

import com.restaurant.dto.RestaurantRequest;
import com.restaurant.dto.RestaurantResponse;
import com.restaurant.enums.Status;

import java.util.List;

public interface IRestaurantService {

    public RestaurantResponse addRestaurant(RestaurantRequest restaurant);
    public List<RestaurantResponse> getAllRestaurant();
    public RestaurantResponse getRestaurantById(Long id);
    public RestaurantResponse updateRestaurantStatus(long restaurantId, Status status);
    public List<RestaurantResponse> getRestaurantByOwnerId(long ownerId);
}
