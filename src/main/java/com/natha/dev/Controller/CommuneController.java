package com.natha.dev.Controller;

import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.IService.CommuneIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CommuneController {
    @Autowired
    private CommuneIService communeIService;
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/commune")
    List<CommuneDto> communeList(){
        return communeIService.findAll();
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/communes/create")
    public CommuneDto create(@RequestBody CommuneDto dto) {
        return communeIService.save(dto);
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER')")

    @GetMapping("/communes/arrondissment/{id}")
    public List<CommuneDto> getByDepartement(@PathVariable Long id) {
        return communeIService.getByArrondissmentId(id);
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/communes/all")
    public List<CommuneDto> all() {
        return communeIService.getAll();
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/communes/{id}")
    public void delete(@PathVariable Long id) {
        communeIService.delete(id);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/communess/{id}")
    public CommuneDto update(@PathVariable Long id, @RequestBody CommuneDto communeDto) {
        return communeIService.update(id, communeDto);
    }

//    @DeleteMapping("/commune/{id}")
//    public void deleteCommune(@PathVariable Long id){
//        communeIService.deleteById(id);
//    }


}
