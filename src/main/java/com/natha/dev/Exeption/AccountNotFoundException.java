package com.natha.dev.Exeption;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String id){
        super("Could not fund  the Account with id" +id);
    }
}
