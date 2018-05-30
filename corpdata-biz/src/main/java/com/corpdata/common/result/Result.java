package com.corpdata.common.result;

import java.io.Serializable;

/**
 * 操作结果集封装
 *
 * @author zealon
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int code;
    private String error;
    private Object message;

    public Result(int code, String error, Object message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }
    public Result(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public Result() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
