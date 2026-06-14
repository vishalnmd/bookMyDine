package com.restaurant.dto;

import com.restaurant.enums.FoodType;
import com.restaurant.enums.RestaurantStatus;
import com.restaurant.enums.Status;
import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest{

    private long id;
    private String name;
    private String address;
    private FoodType foodType;
    private long ownerId;
    private RestaurantStatus restaurantStatus;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String contactNo;
    private Status status;
}
