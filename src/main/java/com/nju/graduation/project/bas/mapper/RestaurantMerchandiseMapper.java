package com.nju.graduation.project.bas.mapper;

import com.nju.graduation.project.bas.domain.RestaurantMerchandise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RestaurantMerchandiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RestaurantMerchandise record);

    int insertSelective(RestaurantMerchandise record);

    RestaurantMerchandise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RestaurantMerchandise record);

    int updateByPrimaryKey(RestaurantMerchandise record);

    List<RestaurantMerchandise> listRestaurantMerchandiseByPage(@Param("restaurantId") int restaurantId,
                                                                @Param("star") int star,
                                                                @Param("size") int size);

    int countSumByRestaurantId(int restaurantId);
}