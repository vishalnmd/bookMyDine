package com.userservice.user.dto;

import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private long id;
    private String name;
    private String address;
    private String foodType;
    private long ownerId;
    private String restaurantStatus;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String contactNo;
    private String status;
}
