package com.nju.graduation.project.bas.service;

import com.nju.graduation.project.bas.domain.TokenInfo;

/**
 * @author shanhe
 * @className ProductToken
 * @date 2021-02-25 16:09
 **/
public interface TokenService {

    boolean checkToken(TokenInfo tokenInfo);

    TokenInfo createToken(int user_id, int type);

    boolean deleteToken(int user_id, int type);
}
