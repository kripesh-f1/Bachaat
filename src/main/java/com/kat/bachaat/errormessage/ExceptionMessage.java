package com.kat.bachaat.errormessage;

public class ExceptionMessage {
    private String message;
    private String callerUrl;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallerUrl() {
        return callerUrl;
    }

    public void setCallerUrl(String callerUrl) {
        this.callerUrl = callerUrl;
    }
}
