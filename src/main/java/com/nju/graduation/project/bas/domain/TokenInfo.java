package com.nju.graduation.project.bas.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * @author shanhe
 * @className TokenInfo
 * @date 2021-02-25 15:44
 **/
@Data
@ToString
public class TokenInfo {
    public static final String USER_ID = "id";
    public static final String TOKEN = "token";
    public static final String TYPE = "type";

    private int user_id;
    private String token;
    private int type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenInfo)) return false;
        TokenInfo tokenInfo = (TokenInfo) o;
        return getUser_id() == tokenInfo.getUser_id() &&
                getType() == tokenInfo.getType() &&
                getToken().equals(tokenInfo.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getToken(), getType());
    }
}
