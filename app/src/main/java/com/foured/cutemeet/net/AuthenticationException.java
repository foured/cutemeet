package com.foured.cutemeet.net;

public class AuthenticationException extends Exception{

    public AuthenticationException(){
        super();
    }

    public AuthenticationException(String message){
        super(message);
    }
}
