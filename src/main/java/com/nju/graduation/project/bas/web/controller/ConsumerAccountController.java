package com.nju.graduation.project.bas.web.controller;

import com.nju.graduation.project.bas.domain.ConsumerAccount;
import com.nju.graduation.project.bas.domain.ConsumerAddress;
import com.nju.graduation.project.bas.domain.eu.LoginMethodType;
import com.nju.graduation.project.bas.domain.eu.UserSex;
import com.nju.graduation.project.bas.domain.eu.UserType;
import com.nju.graduation.project.bas.domain.vo.PageVO;
import com.nju.graduation.project.bas.service.ConsumerAccountService;
import com.nju.graduation.project.bas.service.ConsumerAddressService;
import com.nju.graduation.project.bas.service.PhoneService;
import com.nju.graduation.project.bas.service.impl.TokenServiceImpl;
import com.nju.graduation.project.bas.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author shanhe
 * @className AccountController
 * @date 2021-02-23 21:25
 **/
@Controller
@RequestMapping("/consumer")
public class ConsumerAccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerAccountController.class);

    private static final String DEFAULT_NAKENAME = "用户";
    private static final int DEFAULT_DELIVER_ID = 0;
    private static final int PHONE_NUM_LENGTH = 11;

    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private ConsumerAccountService consumerAccountService;
    @Autowired
    private ConsumerAddressService consumerAddressService;


    @ResponseBody
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public Object SMS_Verify(int phone) {
        try {
            boolean access = false;
            if (access = (phoneService.isPhoneHasAccessTime(phone, UserType.CONSUMER.getValue()))
                    && phoneService.couldSendSMS_VerifyInfo(phone, UserType.CONSUMER.getValue())) {
                SMS_VerifyUtils.sendMessage(phone);
                phoneService.phoneVerifyCache(phone, UserType.CONSUMER.getValue());
            } else if (access){
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE_VERIFY_FREQUENTLY, CommonUtils.VERIFY_FREQUENTLY, "");
            } else {
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE_DALLY_TIME_LIMIT, CommonUtils.DALLY_TIME_LIMIT, "");
            }

        } catch (Exception e) {
            LOGGER.error("ManagerAccountController.SMS_Verify error, phone={}", phone);
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
        return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object consumerLogin(String phone, String password, int type, HttpServletResponse response) {
        ConsumerAccount consumerAccount = null;
        //验证登陆
        try {
            if (type == LoginMethodType.SMS.getValue()) {
                if (!SMS_VerifyUtils.SMS_Verify(phone)) {
                    return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.WRONG_VERIFY_CODE, "");
                }
            } else if (type == LoginMethodType.PASSWORD.getValue()) {
                consumerAccount = consumerAccountService.findByPhoneAndPassword(phone, password);
                if (consumerAccount == null) {
                    return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.WRONG_PASSWORD, "");
                }
            } else {
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.ILLEGAL_PARAMS, "");
            }
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountController.consumerLogin error, phone={}, password={}, type={}\nerrorMessage={}", phone, password, type, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
        try {
            if (consumerAccount == null) {
                consumerAccount = consumerAccountService.findByPhone(phone);
            }
            if (consumerAccount == null) {
                //注册
                consumerAccount = new ConsumerAccount();
                consumerAccount.setUserName(DEFAULT_NAKENAME);
                consumerAccount.setPhone(phone);
                consumerAccount.setPassword(String.valueOf(phone));
                consumerAccount.setLastDlyAdrsId(DEFAULT_DELIVER_ID);
                int user_id = consumerAccountService.createNewAccount(consumerAccount);
                if (user_id == 0) {
                    return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
                }
                addTokenInfo(response, user_id);
                //TODO 封装VO
                return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
            }
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountController.consumerLogin error, phone={}, password={}, type={}\nerrorMessage={}", phone, password, type, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
        //非首次登陆
        try {
            //TODO 搜索附近店铺，封装返回
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountController.consumerLogin error, phone={}, password={}, type={}\nerrorMessage={}", phone, password, type, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }

    private void addTokenInfo(HttpServletResponse response, int user_id) {
        response.addHeader("token", JsonUtils.object2Json(tokenService.createToken(user_id, UserType.MANAGER.getValue())));
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object consumerLogout(int user_id) {
        try {
            tokenService.deleteToken(user_id, UserType.CONSUMER.getValue());
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountController.consumerLogout error, user_id={}\nerrorMessage={}", user_id, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object consumerModify() {
        ConsumerAccount consumerAccount = new ConsumerAccount();
        //TODO 设置属性，error日志补充属性
        try {
            consumerAccountService.modifySelective(consumerAccount);
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountController.consumerLogout error\nerrorMessage={}", e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deliveryAddress/list", method = RequestMethod.POST)
    public Object listDeliveryAddressByPage(@RequestParam(value = "userId") int userId,
                                            @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<ConsumerAddress> list = consumerAddressService.listByPage(userId, pageNum, pageSize);
        //TODO 封装，list+当前页面+总页面数
        int total = consumerAddressService.countSumPage(userId, pageSize);
        PageVO<ConsumerAddress> vo = new PageVO<>();
        vo.setList(list)
                .setPageNum(pageNum)
                .setTotal(total);
        return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/deliveryAddress/add", method = RequestMethod.POST)
    public Object addDeliveryAddress(@RequestParam("userId") int userId,
                                     @RequestParam("city") String city,
                                     @RequestParam("address" )String address,
                                     @RequestParam("longitude") String longitude,
                                     @RequestParam("latitude") String latitude,
                                     @RequestParam("poiId") String poiId,
                                     @RequestParam("houseNumber") String houseNumber,
                                     @RequestParam("name") String name,
                                     @RequestParam("sex") int sex,
                                     @RequestParam("phone") String phone) {
        //数据校验，校验userId，city，phone, sex
        int cityId = CityUtils.getCityId(city.trim());
        if(!verifyUserId(userId)
            && !verifyPhone(phone)
                && cityId == 0
                    && verifySex(sex)) {
            LOGGER.error("ConsumerAccountController.addDeliveryAddress 参数验证失败，userId={}, city={}, phone={}, sex={}", userId, city, phone, sex);
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.ILLEGAL_PARAMS, null);
        }
        ConsumerAddress consumerAddress = new ConsumerAddress();
        consumerAddress.setUserId(userId);
        consumerAddress.setCity(city);
        consumerAddress.setCityId(cityId);
        consumerAddress.setAddress(address);
        consumerAddress.setLongitude(longitude);
        consumerAddress.setLatitude(latitude);
        consumerAddress.setPoiId(poiId);
        consumerAddress.setHouseNumber(houseNumber);
        consumerAddress.setName(name);
        consumerAddress.setSex(sex);
        consumerAddress.setPhone(phone);
        consumerAddress.setTime(TimeUtils.getCurrentUnixTime());

        int res = consumerAddressService.createNewAddress(consumerAddress);
        if (res != 0) {
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        }

        return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
    }

    private boolean verifySex(int sex) {
        if (UserSex.getUserSexByValue(sex) == null) {
            return false;
        }
        return true;
    }

    private boolean verifyPhone(String phone) {
        return phone.trim().length() == PHONE_NUM_LENGTH;
    }

    private boolean verifyUserId(int userId) {
        return userId > 0;
    }

    @ResponseBody
    @RequestMapping(value = "/deliveryAddress/modify", method = RequestMethod.POST)
    public Object modifyDeliveryAddress(@RequestParam("userId") int userId,
                                        @RequestParam("city") String city,
                                        @RequestParam("address" )String address,
                                        @RequestParam("longitude") String longitude,
                                        @RequestParam("latitude") String latitude,
                                        @RequestParam("poiId") String poiId,
                                        @RequestParam("houseNumber") String houseNumber,
                                        @RequestParam("name") String name,
                                        @RequestParam("sex") int sex,
                                        @RequestParam("phone") String phone) {
        ConsumerAddress consumerAddress = new ConsumerAddress();
        if (!verifyUserId(userId)) {
            LOGGER.error("ConsumerAccountController.modifyDeliveryAddress 参数验证失败，userId={}", userId);
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.ILLEGAL_PARAMS, null);
        }
        consumerAddress.setUserId(userId);
        if (StringUtils.isNotBlank(poiId)) {
            consumerAddress.setPoiId(poiId);
            consumerAddress.setLongitude(longitude);
            consumerAddress.setLatitude(latitude);
            consumerAddress.setAddress(address);
            if (StringUtils.isNotBlank(city)) {
                int cityId = CityUtils.getCityId(city);
                consumerAddress.setCityId(cityId);
                consumerAddress.setCity(city);
            }
        }
        if (StringUtils.isNotBlank(houseNumber)) {
            consumerAddress.setHouseNumber(houseNumber);
        }
        if (StringUtils.isNotBlank(name)) {
            consumerAddress.setName(name);
        }
        if (sex != 0) {
            if (!verifySex(sex)) {
                LOGGER.error("modifyDeliveryAddress.addDeliveryAddress 参数验证失败，sex={}", sex);
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.ILLEGAL_PARAMS, null);
            }
            consumerAddress.setSex(sex);
        }
        if (StringUtils.isNotBlank(phone)) {
            if (!verifyPhone(phone)) {
                LOGGER.error("modifyDeliveryAddress.addDeliveryAddress 参数验证失败，phone={}", phone);
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.ILLEGAL_PARAMS, null);
            }
            consumerAddress.setPhone(phone);
        }
        int num = consumerAddressService.modifySelective(consumerAddress);
        if (num == 0) {
            LOGGER.error("modifyDeliveryAddress.addDeliveryAddress 修改失败，consumerAddress={}", consumerAddress);
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.MODIFY_ERROR, null);
        }
        return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
    }

    @ResponseBody
    @RequestMapping(value = "/deliveryAddress/remove", method = RequestMethod.POST)
    public Object removeDeliveryAddress(int addressId) {
        //参数校验
        if (!verifyUserId(addressId)) {
            LOGGER.error("ConsumerAccountController.removeDeliveryAddress 参数验证失败，addressId={}", addressId);
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.ILLEGAL_PARAMS, null);
        }
        if (consumerAddressService.removeDeliveryAddressById(addressId)) {
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        }
        return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
    }
}
