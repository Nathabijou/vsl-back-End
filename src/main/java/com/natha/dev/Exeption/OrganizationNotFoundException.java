package com.natha.dev.Exeption;

public class OrganizationNotFoundException extends RuntimeException{
    public OrganizationNotFoundException(String id){
        super("Could not fund  the organization with id" +id);
    }
}
