package com.nju.graduation.project.bas.interceptor;

import com.nju.graduation.project.bas.domain.TokenInfo;
import com.nju.graduation.project.bas.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shanhe
 * @className UserInterceptor
 * @date 2021-02-25 15:41
 **/
public class UserInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInterceptor.class);

    @Autowired
    private TokenService tokenService;

    protected TokenInfo getUserToken(HttpServletRequest request) {
        TokenInfo res = new TokenInfo();
        String user_id = request.getHeader(TokenInfo.USER_ID);
        String token = request.getHeader(TokenInfo.TOKEN);
        String type = request.getHeader(TokenInfo.TYPE);
        if (StringUtils.isNotBlank(user_id) && StringUtils.isNotBlank(token) && StringUtils.isNotBlank(type)) {
            res.setUser_id(Integer.valueOf(user_id));
            res.setToken(token);
            res.setType(Integer.valueOf(type));
        } else {
            return null;
        }
        return res;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        TokenInfo tokenInfo = this.getUserToken(request);
        if (tokenInfo == null) {
            LOGGER.info("没有登陆信息或登陆信息过期，返回登陆");
            return false;
        }
        try {
            if (tokenService.checkToken(tokenInfo)) {
                LOGGER.info("身份校验成功！");
                //TODO token刷新
                return true;
            } else {
                LOGGER.info("身份校验失败，返回登陆！");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("ConsumerInterceptor.preHandle error, request={}, errorMessage={} ", request, e.getMessage());
            return false;
        }
    }


}
