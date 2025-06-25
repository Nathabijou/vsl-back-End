package com.natha.dev.Controller;

import com.natha.dev.Dto.DepartementDto;
import com.natha.dev.IService.DepartementIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
@CrossOrigin("http://localhost:4200")
public class DepartementController {

    @Autowired
    private DepartementIService service;
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/create")
    public DepartementDto create(@RequestBody DepartementDto dto) {
        return service.save(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER')")
    @GetMapping("/zone/{zoneId}")
    public List<DepartementDto> getByZone(@PathVariable Long zoneId) {
        return service.getByZone(zoneId);
    }

    @PreAuthorize("hasAnyRole( 'SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/all")
    public List<DepartementDto> all() {
        return service.getAll();
    }
}

