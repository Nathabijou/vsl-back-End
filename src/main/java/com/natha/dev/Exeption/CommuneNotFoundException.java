package com.natha.dev.Exeption;

public class CommuneNotFoundException extends RuntimeException{
    public CommuneNotFoundException(String id){
        super("Could not fund  the group with id" +id);
    }
}
