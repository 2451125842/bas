package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.domain.eu.UserType;
import com.nju.graduation.project.bas.exception.ParamException;
import com.nju.graduation.project.bas.service.PhoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author shanhe
 * @className PhoneServiceImpl
 * @date 2021-02-26 18:32
 **/
@Service
public class PhoneServiceImpl implements PhoneService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneServiceImpl.class);

    private static final String DALLY_ACCESS_TIME = "10";
    private static final String MANAGER_ACCESS_PRE = "MP:A-";
    private static final String CONSUMER_ACCESS_PRE = "CP:A-";
    private static final String MANAGER_SEND_PRE = "MP:S-";
    private static final String CONSUMER_SEND_PRE = "CP:S-";
    private static final int ACCESS_EXPIRE_TIME = 24;
    private static final TimeUnit ACCESS_EXPIRE_UNIT = TimeUnit.HOURS;
    private static final int SEND_EXPIRE_TIME = 60;
    private static final TimeUnit SEND_EXPIRE_UNIT = TimeUnit.SECONDS;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String generateAccessKey(int phone, int type) {
        if (type == UserType.MANAGER.getValue()) {
            return MANAGER_ACCESS_PRE +phone;
        } else if (type == UserType.CONSUMER.getValue()){
            return CONSUMER_ACCESS_PRE+phone;
        } else {
            LOGGER.error("PhoneServiceImpl.generateAccessKey error, phone={}, type={}", phone, type);
            throw new ParamException("参数异常");
        }
    }

    private String generateSendKey(int phone, int type) {
        if (type == UserType.MANAGER.getValue()) {
            return MANAGER_SEND_PRE +phone;
        } else if (type == UserType.CONSUMER.getValue()){
            return CONSUMER_SEND_PRE+phone;
        } else {
            LOGGER.error("PhoneServiceImpl.generateSendKey error, phone={}, type={}", phone, type);
            throw new ParamException("参数异常");
        }
    }

    private void createPhoneCache(String key) {
        redisTemplate.opsForValue().set(key, DALLY_ACCESS_TIME, ACCESS_EXPIRE_TIME, ACCESS_EXPIRE_UNIT);
    }

    @Override
    public boolean isPhoneHasAccessTime(int phone, int type) {
        String key = generateAccessKey(phone, type);
        if (!redisTemplate.hasKey(key)) {
            createPhoneCache(key);
        } else {
            long time = redisTemplate.boundValueOps(key).increment(1);
            if (time >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void phoneVerifyCache(int phone, int type) {
        String key = generateSendKey(phone, type);
        redisTemplate.opsForValue().set(key, String.valueOf(phone), SEND_EXPIRE_TIME, SEND_EXPIRE_UNIT);
    }

    @Override
    public boolean couldSendSMS_VerifyInfo(int phone, int type) {
        return redisTemplate.hasKey(generateSendKey(phone, type));
    }


}
