package com.natha.dev.Exeption;

public class MySystemNotFoundException extends RuntimeException {

    public MySystemNotFoundException(String id){
        super("Could not fund  the group with id" +id);
    }
}
