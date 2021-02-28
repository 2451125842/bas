package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.domain.TokenInfo;
import com.nju.graduation.project.bas.domain.eu.UserType;
import com.nju.graduation.project.bas.exception.ParamException;
import com.nju.graduation.project.bas.service.TokenService;
import com.nju.graduation.project.bas.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author shanhe
 * @className ProductTokenImpl
 * @date 2021-02-25 16:10
 **/
@Service
@Transactional
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    //TODO redis表
    private static final String CONSUMER_PRE = "Token:C-";
    private static final String MANAGER_PRE = "Token:M-";
    private static final int TOKEN_EXPIRE_TIME = 24;
    private static final TimeUnit TOKEN_EXPIRE_UNIT = TimeUnit.HOURS;

    @Autowired
    private StringRedisTemplate redisTemplate;


    private String generateTokenKey(int user_id, int type) {
        String key;
        if (UserType.CONSUMER.getValue() == type) {
            key = CONSUMER_PRE+user_id;
        } else if (UserType.MANAGER.getValue() == type) {
            key = MANAGER_PRE+user_id;
        } else {
            LOGGER.error("TokenServiceImpl.checkToken error 用户类型错误, tokenInfo={}, type={}", user_id, type);
            throw new ParamException("TokenServiceImpl.generateTokenKey error 用户类型错误");
        }
        return key;
    }

    private TokenInfo generateToken(int user_id, int type) {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUser_id(user_id);
        tokenInfo.setType(type);
        tokenInfo.setToken(UUID.randomUUID().toString());
        return tokenInfo;
    }

    @Override
    public boolean checkToken(TokenInfo tokenInfo) {
        String key = generateTokenKey(tokenInfo.getUser_id(), tokenInfo.getType());
        String token_json = redisTemplate.opsForValue().get(key);
        TokenInfo trueTokenInfo = JsonUtils.json2Pojo(token_json, TokenInfo.class);
        if(trueTokenInfo.equals(tokenInfo)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TokenInfo createToken(int user_id, int type) {
        String key = generateTokenKey(user_id, type);
        TokenInfo tokenInfo = generateToken(user_id, type);
        redisTemplate.opsForValue().set(key, JsonUtils.object2Json(tokenInfo), TOKEN_EXPIRE_TIME, TOKEN_EXPIRE_UNIT);
        return tokenInfo;
    }

    @Override
    public boolean deleteToken(int user_id, int type) {
        String key = generateTokenKey(user_id, type);
        return redisTemplate.delete(key);
    }
}
