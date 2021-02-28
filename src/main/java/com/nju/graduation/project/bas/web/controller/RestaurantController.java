package com.nju.graduation.project.bas.web.controller;

import com.nju.graduation.project.bas.domain.Restaurant;
import com.nju.graduation.project.bas.domain.RestaurantMerchandise;
import com.nju.graduation.project.bas.domain.vo.PageVO;
import com.nju.graduation.project.bas.service.RestaurantMerchandiseService;
import com.nju.graduation.project.bas.service.RestaurantService;
import com.nju.graduation.project.bas.utils.CityUtils;
import com.nju.graduation.project.bas.utils.CommonUtils;
import com.nju.graduation.project.bas.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shanhe
 * @className RestaurantOperateController
 * @date 2021-02-27 15:17
 **/
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    private static final String APPLY_RETURN_MESSAGE = "申请已提交，请等待审核";

    @Autowired
    private RestaurantMerchandiseService restaurantMerchandiseService;
    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Object applyNewRestaurant(@RequestParam("managerId") int managerId,
                                     @RequestParam(value = "name") String name,
                                     @RequestParam(value = "headPortrait") String headPortrait,
                                     @RequestParam(value = "secondTypeId") int secondTypeId,
                                     @RequestParam(value = "restaurantPhone") String restaurantPhone,
                                     @RequestParam(value = "city") String city,
                                     @RequestParam(value = "adcode") String adcode,
                                     @RequestParam(value = "address") String address,
                                     @RequestParam(value = "longitude") String longitude,
                                     @RequestParam(value = "latitude") String latitude,
                                     @RequestParam(value = "startTime") long startTime,
                                     @RequestParam(value = "endTime") long endTime,
                                     @RequestParam(value = "businessState") int businessState,
                                     @RequestParam(value = "distributeType") int distributeType) {
        Restaurant restaurant = new Restaurant();
        try {
            //TODO 参数校验
            restaurant.setManagerId(managerId);
            restaurant.setName(name);
            restaurant.setSecondTypeId(secondTypeId);
            restaurant.setRestaurantPhone(restaurantPhone);
            restaurant.setCity(city);
            restaurant.setCityId(CityUtils.getCityId(city));
            restaurant.setAdcode(adcode);
            restaurant.setAddress(address);
            restaurant.setLongitude(longitude);
            restaurant.setLatitude(latitude);
            restaurant.setStartTime(TimeUtils.Unix2Date(startTime));
            restaurant.setEndTime(TimeUtils.Unix2Date(endTime));
            restaurant.setBusinessState(businessState);
            restaurant.setDistributeType(distributeType);
            restaurantService.newRestaurant(restaurant);
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, APPLY_RETURN_MESSAGE);
        } catch (Exception e) {
            LOGGER.error("RestaurantController.applyNewRestaurant error, restaurant={}\nerrorMessage={}", restaurant, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object listAllRestaurant(@RequestParam("managerId") int managerId) {
        try {
            List<Restaurant> list = restaurantService.listAllByManagerId(managerId);
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, list);
        } catch (Exception e) {
            LOGGER.error("RestaurantController.listAllRestaurant error, managerId={}\nerrorMessage={}", managerId, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
        }
    }

    @RequestMapping(value = "/toggle", method = RequestMethod.GET)
    public Object toggleRestaurant(int restaurantId) {
        //TODO 获取数据，封装返回，与manageAccount登陆类似，但逻辑更少
        Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);

        return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
    }

    @RequestMapping(value = "/on-off", method = RequestMethod.GET)
    public Object restaurantBusinessSwitch(@RequestParam("restaurantId") int restaurantId,
                                           @RequestParam("state") int state) {
        try {
            //TODO 参数校验
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(restaurantId);
            restaurantService.modifySelective(restaurant);
        } catch (Exception e) {
            LOGGER.error("RestaurantController.restaurantBusinessSwitch error, restaurantId={}. state={}\nerrorMessage={}", restaurantId, state, e.getMessage());
        }
        return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modifyRestaurantInfo(@RequestParam("managerId") int managerId,
                                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                       @RequestParam(value = "headPortrait", required = false, defaultValue = "") String headPortrait,
                                       @RequestParam(value = "secondTypeId", required = false, defaultValue = "-1") int secondTypeId,
                                       @RequestParam(value = "restaurantPhone", required = false, defaultValue = "") String restaurantPhone,
                                       @RequestParam(value = "city", required = false, defaultValue = "") String city,
                                       @RequestParam(value = "adcode", required = false, defaultValue = "") String adcode,
                                       @RequestParam(value = "address", required = false, defaultValue = "") String address,
                                       @RequestParam(value = "longitude", required = false, defaultValue = "") String longitude,
                                       @RequestParam(value = "latitude", required = false, defaultValue = "") String latitude,
                                       @RequestParam(value = "startTime", required = false, defaultValue = "-1") long startTime,
                                       @RequestParam(value = "endTime", required = false, defaultValue = "-1") long endTime,
                                       @RequestParam(value = "businessState", required = false, defaultValue = "-1") int businessState,
                                       @RequestParam(value = "distributeType", required = false, defaultValue = "-1") int distributeType) {
        //TODO 参数校验
        Restaurant restaurant = new Restaurant();
        restaurant.setManagerId(managerId);
        if (StringUtils.isNotBlank(name)) {
            restaurant.setName(name);
        }
        if (StringUtils.isNotBlank(headPortrait)) {
            restaurant.setHeadPortrait(headPortrait);
        }
        if (secondTypeId > 0) {
            restaurant.setSecondTypeId(secondTypeId);
        }
        if (StringUtils.isNotBlank(restaurantPhone)) {
            restaurant.setRestaurantPhone(restaurantPhone);
        }
        if (StringUtils.isNotBlank(city)) {
            restaurant.setRestaurantPhone(city);
            restaurant.setCityId(CityUtils.getCityId(city));
        }
        if (StringUtils.isNotBlank(adcode)) {
            restaurant.setAdcode(adcode);
        }
        if (StringUtils.isNotBlank(address)) {
            restaurant.setAddress(address);
        }
        if (StringUtils.isNotBlank(longitude)) {
            restaurant.setLongitude(longitude);
        }
        if (StringUtils.isNotBlank(latitude)) {
            restaurant.setLatitude(latitude);
        }
        if (startTime > 0) {
            restaurant.setStartTime(TimeUtils.Unix2Date(startTime));
        }
        if (endTime > 0) {
            restaurant.setEndTime(TimeUtils.Unix2Date(endTime));
        }
        if (businessState > 0) {
            //TODO 校验
            restaurant.setBusinessState(businessState);
        }
        if (distributeType > 0) {
            //TODO 校验
            restaurant.setDistributeType(distributeType);
        }

        try {
            int num = restaurantService.modifySelective(restaurant);
            if (num <= 0) {
                CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, "请勿重复提交");
            }
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            LOGGER.error("RestaurantController.modifyRestaurantInfo error, data={}\nerrorMessage={}", restaurant, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
        }
    }




    @RequestMapping(value = "/merchandise/list", method = RequestMethod.GET)
    public Object listMerchandiseByPage(@RequestParam("restaurantId") int restaurantId,
                                        @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        try {

            //TODO 参数校验
            List<RestaurantMerchandise> list = restaurantMerchandiseService.listRestaurantMerchandiseByPage(restaurantId, pageNum, pageSize);
            int total = restaurantMerchandiseService.countPageNum(restaurantId, pageSize);
            PageVO<RestaurantMerchandise> vo = new PageVO<>();
            vo.setList(list)
                    .setPageNum(pageNum)
                    .setTotal(total);
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, vo);
        } catch (Exception e) {
            LOGGER.error("RestaurantController.listMerchandiseByPage error, restaurantId={},pageNum={},pageSize={}\nerrorMessage={}",restaurantId, pageNum, pageSize, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/merchandise/new", method = RequestMethod.POST)
    public Object newMerchandise(@RequestParam("restaurantId") int restaurantId,
                                 @RequestParam("name") String name,
                                 @RequestParam("photoAddress") String photoAddress,
                                 @RequestParam("price") int price,
                                 @RequestParam("number") int number) {
        try {
            //TODO 参数校验
            RestaurantMerchandise restaurantMerchandise = new RestaurantMerchandise();
            restaurantMerchandise.setRestaurantId(restaurantId);
            restaurantMerchandise.setName(name);
            restaurantMerchandise.setPhotoAddress(photoAddress);
            restaurantMerchandise.setPrice(price);
            restaurantMerchandise.setNumber(number);
            restaurantMerchandiseService.createNewMerchandise(restaurantMerchandise);
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            LOGGER.error("RestaurantController.newMerchandise error, restaurantId={},name={},photoAddress={},price={},number={}\nerrorMessage={}", restaurantId, name, photoAddress, price, number, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }

    }

    @RequestMapping(value = "/merchandise/modify", method = RequestMethod.POST)
    public Object modifyMerchandise(@RequestParam("restaurantId") int restaurantId,
                                    @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                    @RequestParam(value = "photoAddress", required = false, defaultValue = "") String photoAddress,
                                    @RequestParam(value = "price", required = false, defaultValue = "-1") int price,
                                    @RequestParam(value = "number", required = false, defaultValue = "-1") int number) {
        try {
            //TODO 参数校验
            RestaurantMerchandise restaurantMerchandise = new RestaurantMerchandise();
            restaurantMerchandise.setRestaurantId(restaurantId);
            if (StringUtils.isNotBlank(name)) {
                restaurantMerchandise.setName(name);
            }
            if (StringUtils.isNotBlank(photoAddress)) {
                restaurantMerchandise.setPhotoAddress(photoAddress);
            }
            if (price > 0) {
                restaurantMerchandise.setPrice(price);
            }
            if (number > 0) {
                restaurantMerchandise.setNumber(number);
            }
            restaurantMerchandiseService.modifySelective(restaurantMerchandise);
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            LOGGER.error("RestaurantController.modifyMerchandise error, restaurantId={},name={},photoAddress={},price={},number={}\nerrorMessage={}", restaurantId, name, photoAddress, price, number, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/merchandise/remove", method = RequestMethod.GET)
    public Object removeMerchandise(int dishesId) {
        try {
            //TODO 参数校验
            int res = restaurantMerchandiseService.removeByMerchandiseId(dishesId);
            if (res < 1) {
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, "删除0项，请不要重复操作");
            }
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            LOGGER.error("RestaurantController.removeMerchandise error, dishesId={}\nerrorMessage={}", dishesId, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }

}
