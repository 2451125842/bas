package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.domain.Restaurant;
import com.nju.graduation.project.bas.mapper.RestaurantMapper;
import com.nju.graduation.project.bas.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shanhe
 * @className RestaurantServiceImpl
 * @date 2021-02-27 21:51
 **/
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);


    @Autowired
    private RestaurantMapper restaurantMapper;

    @Override
    public int modifySelective(Restaurant restaurant) {
        try {
            return restaurantMapper.updateByPrimaryKeySelective(restaurant);
        } catch (Exception e) {
            LOGGER.error("RestaurantServiceImpl.modifySelective error, restaurant={}\nerrorMessage={}", restaurant, e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Restaurant> listAllByManagerId(int managerId) {
        try {
            return restaurantMapper.listAllRestaurantByManagerId(managerId);
        } catch (Exception e) {
            LOGGER.error("RestaurantServiceImpl.listAllByManagerId error, managerId={}\nerrorMessage={}", managerId, e.getMessage());
            return null;
        }
    }

    @Override
    public Restaurant findByRestaurantId(int restaurantId) {
        try {
            return restaurantMapper.selectByPrimaryKey(restaurantId);
        } catch (Exception e) {
            LOGGER.error("RestaurantServiceImpl.findByRestaurantId error, restaurantId={}\nerrorMessage={}", restaurantId, e.getMessage());
            return null;
        }
    }

    @Override
    public int newRestaurant(Restaurant restaurant) {
        try {
            return restaurantMapper.insert(restaurant);
        } catch (Exception e) {
            LOGGER.error("RestaurantServiceImpl.newRestaurant error, restaurant={}\nerrorMessage={}", restaurant, e.getMessage());
            return 0;
        }
    }
}
