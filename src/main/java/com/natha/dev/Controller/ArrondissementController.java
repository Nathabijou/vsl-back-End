package com.natha.dev.Controller;

import com.natha.dev.Dto.ArrondissementDto;
import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.IService.ArrondissmentIService;
import com.natha.dev.IService.CommuneIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin("http://localhost:4200")
public class ArrondissementController {

    @Autowired
    private ArrondissmentIService arrondissmentIService;
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/arrondissement")
    List<ArrondissementDto> arrondessementList(){
        return arrondissmentIService.findAll();
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/arrondissement/create")
    public ArrondissementDto create(@RequestBody ArrondissementDto dto) {
        return arrondissmentIService.save(dto);
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER')")
    @GetMapping("/arrondissements/arrondissement/{id}")
    public List<ArrondissementDto> getByDepartement(@PathVariable Long id) {
        return arrondissmentIService.getByDepartementId(id);
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/arrondissemene/all")
    public List<ArrondissementDto> all() {
        return arrondissmentIService.getAll();
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/arrondissemene/{id}")
    public void delete(@PathVariable Long id) {
        arrondissmentIService.deleteById(id);
    }
}
