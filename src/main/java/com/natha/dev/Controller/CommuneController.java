package com.natha.dev.Controller;

import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.IService.CommuneIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CommuneController {
    @Autowired
    private CommuneIService communeIService;

    @GetMapping("/commune")
    List<CommuneDto> communeList(){
        return communeIService.findAll();
    }


//    @DeleteMapping("/commune/{id}")
//    public void deleteCommune(@PathVariable Long id){
//        communeIService.deleteById(id);
//    }


}
