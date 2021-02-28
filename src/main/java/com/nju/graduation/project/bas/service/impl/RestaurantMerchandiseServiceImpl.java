package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.domain.RestaurantMerchandise;
import com.nju.graduation.project.bas.mapper.RestaurantMerchandiseMapper;
import com.nju.graduation.project.bas.service.RestaurantMerchandiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shanhe
 * @className RestaurantMerchandiseServiceImpl
 * @date 2021-02-27 19:48
 **/
@Service
public class RestaurantMerchandiseServiceImpl implements RestaurantMerchandiseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantMerchandiseServiceImpl.class);

    @Autowired
    private RestaurantMerchandiseMapper restaurantMerchandiseMapper;

    @Override
    public List<RestaurantMerchandise> listRestaurantMerchandiseByPage(int restaurantId, int pageNum, int pageSize) {
        try {
            return restaurantMerchandiseMapper.listRestaurantMerchandiseByPage(restaurantId, pageNum*pageSize, pageSize);
        } catch (Exception e) {
            LOGGER.error("RestaurantMerchandiseServiceImpl.listRestaurantMerchandiseByPage error, restaurantId={}, pageNum={}, pageSize={}\nerrorMessage={}", restaurantId, pageNum, pageSize, e.getMessage());
            return null;
        }
    }

    @Override
    public int countPageNum(int restaurantId, int pageSize) {
        try {
            return (int)Math.ceil(Double.valueOf(restaurantMerchandiseMapper.countSumByRestaurantId(restaurantId))/Double.valueOf(pageSize));
        } catch (Exception e) {
            LOGGER.error("RestaurantMerchandiseServiceImpl.countPageNum error, restaurantId={}, pageSize={}\nerrorMessage={}", restaurantId, pageSize, e.getMessage());
            return 0;
        }
    }

    @Override
    public int createNewMerchandise(RestaurantMerchandise restaurantMerchandise) {
        try {
            return restaurantMerchandiseMapper.insert(restaurantMerchandise);
        } catch (Exception e) {
            LOGGER.error("RestaurantMerchandiseServiceImpl.createNewMerchandise error, restaurantMerchandise={}\nerrorMessage={}", restaurantMerchandise, e.getMessage());
            return 0;
        }
    }

    @Override
    public int modifySelective(RestaurantMerchandise restaurantMerchandise) {
        try {
            return restaurantMerchandiseMapper.updateByPrimaryKeySelective(restaurantMerchandise);
        } catch (Exception e) {
            LOGGER.error("RestaurantMerchandiseServiceImpl.modifySelective error, restaurantMerchandise={}\nerrorMessage={}", restaurantMerchandise, e.getMessage());
            return 0;
        }
    }

    @Override
    public int removeByMerchandiseId(int merchandiseId) {
        try {
            return restaurantMerchandiseMapper.deleteByPrimaryKey(merchandiseId);
        } catch (Exception e) {
            LOGGER.error("RestaurantMerchandiseServiceImpl.removeByMerchandiseId error, merchandiseId={}\nerrorMessage={}", merchandiseId, e.getMessage());
            return 0;
        }
    }
}
