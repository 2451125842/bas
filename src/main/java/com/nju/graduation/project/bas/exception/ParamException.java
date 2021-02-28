package com.nju.graduation.project.bas.exception;

/**
 * @author shanhe
 * @className ParamException
 * @date 2021-02-26 17:46
 **/
public class ParamException extends RuntimeException {
    private int code;
    private String message;

    public ParamException(String message) {
        super();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public ParamException setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ParamException setMessage(String message) {
        this.message = message;
        return this;
    }
}
