package com.nju.graduation.project.bas.service;

import com.nju.graduation.project.bas.domain.RestaurantMerchandise;

import java.util.List;

/**
 * @author shanhe
 * @className RestaurantMerchandiseService
 * @date 2021-02-27 17:06
 **/
public interface RestaurantMerchandiseService {
    List<RestaurantMerchandise> listRestaurantMerchandiseByPage(int restaurantId, int pageNum, int pageSize);

    int countPageNum(int restaurantId, int pageSize);

    int createNewMerchandise(RestaurantMerchandise restaurantMerchandise);

    int modifySelective(RestaurantMerchandise restaurantMerchandise);

    int removeByMerchandiseId(int merchandiseId);
}
