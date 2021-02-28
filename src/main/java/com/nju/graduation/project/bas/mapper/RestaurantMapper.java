package com.nju.graduation.project.bas.mapper;

import com.nju.graduation.project.bas.domain.Restaurant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RestaurantMapper {
    int deleteByPrimaryKey(Integer restaurantId);

    int insert(Restaurant record);

    int insertSelective(Restaurant record);

    Restaurant selectByPrimaryKey(Integer restaurantId);

    int updateByPrimaryKeySelective(Restaurant record);

    int updateByPrimaryKey(Restaurant record);

    List<Restaurant> listAllRestaurantByManagerId(@Param("managerId") int managerId);
}