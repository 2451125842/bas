package com.nju.graduation.project.bas.web.controller;

import com.nju.graduation.project.bas.domain.ManagerAccount;
import com.nju.graduation.project.bas.domain.eu.LoginMethodType;
import com.nju.graduation.project.bas.domain.eu.UserType;
import com.nju.graduation.project.bas.service.PhoneService;
import com.nju.graduation.project.bas.service.impl.ManagerAccountServiceImpl;
import com.nju.graduation.project.bas.service.impl.TokenServiceImpl;
import com.nju.graduation.project.bas.utils.CommonUtils;
import com.nju.graduation.project.bas.utils.JsonUtils;
import com.nju.graduation.project.bas.utils.RealNameAuthenticationUtils;
import com.nju.graduation.project.bas.utils.SMS_VerifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author shanhe
 * @className ManagerAccountController
 * @date 2021-02-23 22:27
 **/
@Controller
@RequestMapping("/manager")
public class ManagerAccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerAccountController.class);

    private static final String DEFAULT_NAKENAME = "经理用户";
    private static final int DEFAULT_RESTAURANT_ID = 0;

    @Autowired
    private ManagerAccountServiceImpl managerAccountService;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private PhoneService phoneService;


    @ResponseBody
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public Object SMS_Verify(int phone) {
        try {
            boolean access = false;
            if (access = (phoneService.isPhoneHasAccessTime(phone, UserType.MANAGER.getValue()))
                    && phoneService.couldSendSMS_VerifyInfo(phone, UserType.MANAGER.getValue())) {
                SMS_VerifyUtils.sendMessage(phone);
                phoneService.phoneVerifyCache(phone, UserType.MANAGER.getValue());
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
    public Object managerLogin(String phone, String password, int type, HttpServletResponse response) {
        ManagerAccount managerAccount= null;
        try{
            if (type == LoginMethodType.SMS.getValue()) {
                if (!SMS_VerifyUtils.SMS_Verify(phone)) {
                    return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.WRONG_VERIFY_CODE, "");
                }
            } else if (type == LoginMethodType.PASSWORD.getValue()) {
                managerAccount = managerAccountService.findByPhoneAndPassword(phone, password);
                if (managerAccount == null) {
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
            if (managerAccount == null) {
                managerAccount = managerAccountService.findByPhone(phone);
            }
            if (managerAccount == null) {
                //首次登陆
                managerAccount = new ManagerAccount();
                managerAccount.setNakename(DEFAULT_NAKENAME);
                managerAccount.setPassword(String.valueOf(phone));
                managerAccount.setPhone(phone);
                managerAccount.setRealNameAuthentication(false);
                managerAccount.setLastLoginRestaurant(DEFAULT_RESTAURANT_ID);
                int user_id = managerAccountService.createNewAccount(managerAccount);
                if (user_id == 0) {
                    return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
                }
                addTokenInfo(response, user_id);
                //TODO 封装VO
                return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
            }
            addTokenInfo(response, managerAccount.getManagerId());
            //TODO 权鉴 如果没完成实名认证，直接返回
            //TODO 获取店铺信息，经营分析总览，封装为VO

        } catch (Exception e) {

            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
        }
        //TODO 将VO封装到response返回
        return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
    }

    private void addTokenInfo(HttpServletResponse response, int user_id) {
        response.addHeader("token", JsonUtils.object2Json(tokenService.createToken(user_id, UserType.MANAGER.getValue())));
    }


    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object managerLogout(int phone) {
        try {
            tokenService.deleteToken(phone, UserType.MANAGER.getValue());
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            LOGGER.error("ManagerAccountController.managerLogout error, phone={}\nerrorMessage={}", phone, e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }


    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object managerModify() {
        ManagerAccount managerAccount = new ManagerAccount();
        //TODO 填充修改内容

        try {
            managerAccountService.modifySelective(managerAccount);
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
        } catch (Exception e) {
            //TODO 补充参数信息
            LOGGER.error("ManagerAccountController.managerModify error, \nerrorMessage={}", e.getMessage());
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/realNameAuthentication", method = RequestMethod.POST)
    public Object realNameAuthentication(int user_id, String name, String IDCard) {
        try {
            if(RealNameAuthenticationUtils.authentication(name, IDCard)) {
                ManagerAccount managerAccount = new ManagerAccount();
                managerAccount.setManagerId(user_id);
                managerAccount.setRealNameAuthentication(true);
                managerAccountService.modifySelective(managerAccount);
                return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, "");
            } else {
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE_AUTHENTICATION_FAIL, CommonUtils.AUTHENTICATION_FAIL, "");
            }
        } catch (Exception e) {
            //TODO 补充参数信息
            LOGGER.error("ManagerAccountController.realNameAuthentication error");
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, e.getMessage());
        }
    }
}
