package com.natha.dev.Exeption;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
@Controller

public class MySystemNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MySystemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public Map<String, String> exceptionHandoler(MySystemNotFoundException exception){
        Map<String, String> errorMap=new HashMap<>();
        errorMap.put("erreur ", exception.getMessage());

        return errorMap;
    }
}
