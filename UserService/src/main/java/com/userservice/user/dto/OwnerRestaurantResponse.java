package com.userservice.user.dto;

import com.userservice.user.common.enums.Roles;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OwnerRestaurantResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Roles role;
    private String phoneNumber;
    private List<RestaurantResponse> restaurantList;
}
