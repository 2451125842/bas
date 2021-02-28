package com.nju.graduation.project.bas.service;

import com.nju.graduation.project.bas.domain.Restaurant;

import java.util.List;

/**
 * @author shanhe
 * @className RestaurantService
 * @date 2021-02-27 20:19
 **/
public interface RestaurantService {
    int modifySelective(Restaurant restaurant);

    List<Restaurant> listAllByManagerId(int managerId);

    Restaurant findByRestaurantId(int restaurantId);

    int newRestaurant(Restaurant restaurant);
}
