package com.natha.dev.Exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class OrganizationNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(OrganizationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public Map<String, String> exceptionHandoler(OrganizationNotFoundException exception){
        Map<String, String> errorMap=new HashMap<>();
        errorMap.put("erreur ", exception.getMessage());

        return errorMap;
    }
}
