package com.userservice.user.service;

import com.userservice.client.RestaurantFeignClient;
import com.userservice.user.dto.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    public static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantFeignClient restaurantFeignClient;

    public List<RestaurantResponse> getAllRestaurantByOwnerId(long id) {
        return restaurantFeignClient.getAllRestaurantByOwnerId(id);
    }


}
