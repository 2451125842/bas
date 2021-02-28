package com.nju.graduation.project.bas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shanhe
 * @className CommonUtils
 * @date 2020-09-23 17:01
 **/
public class CommonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 300;
    public static final int FAIL_CODE_VERIFY_FREQUENTLY = 301;
    public static final int FAIL_CODE_DALLY_TIME_LIMIT = 302;
    public static final int FAIL_CODE_AUTHENTICATION_FAIL = 303;

    public static final String SUCCESS_MESSAGE = "success";
    public static final String ILLEGAL_PARAMS = "参数异常";
    public static final String SYSTEM_ERROR = "系统异常";
    public static final String JSON_ERROR = "JSON转换异常";
    public static final String VERIFY_FREQUENTLY = "验证码获取频繁";
    public static final String DALLY_TIME_LIMIT = "验证码获取达到每日限额，请使用其他登陆方式";
    public static final String AUTHENTICATION_FAIL = "认证失败";
    public static final String WRONG_VERIFY_CODE = "验证码错误";
    public static final String WRONG_PASSWORD = "账号或密码错误";
    public static final String MODIFY_ERROR = "修改失败";

    private static final String JSON_ERROR_RESPONSE = "{\"code\":300,\"message\":\"JSON转换异常\"}";


    public static Object getResponse(int code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", data);
        try {
            String res = JsonUtils.object2Json(response);
            if (res != null)
                return res;
        } catch (Exception e) {
            LOGGER.error("CommonUtils.getResponse error, response={}, errorMessage={}", response, e.getMessage());
        }
        return JSON_ERROR_RESPONSE;
    }
}
