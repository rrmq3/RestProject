package com.Myrestdemo.dto;

public class StatusDTO {

    String status;
    int code;
    String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public StatusDTO success (String message) {
        setStatus("SUCCESS");
        setCode(200);
        setError("");
        return this;
    }

    public StatusDTO fail (int code, String error) {
        setStatus("FAIL");
        setCode(code);
        setError(error);
        return this;
    }
}
