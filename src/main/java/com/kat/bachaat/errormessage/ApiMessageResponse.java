package com.kat.bachaat.errormessage;

public class ApiMessageResponse {

    private String message;
    public ApiMessageResponse(String messsage){
        this.message=messsage;
    }
    public ApiMessageResponse(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
