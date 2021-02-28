package com.nju.graduation.project.bas.exception;

/**
 * @author shanhe
 * @className JSONException
 * @date 2021-02-25 09:31
 **/
public class JSONException extends RuntimeException {
    private int code;
    private String message;

    public JSONException(String message) {
        super();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public JSONException setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public JSONException setMessage(String message) {
        this.message = message;
        return this;
    }
}
