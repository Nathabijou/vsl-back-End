package com.natha.dev.Exeption;

public class MessagingException extends RuntimeException{
    public MessagingException(String id){
        super("Could not fund  the Messaging with is" +id);
    }
}
