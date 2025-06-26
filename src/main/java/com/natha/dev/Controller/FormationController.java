package com.natha.dev.Controller;

import com.natha.dev.Dto.FormationDto;
import com.natha.dev.IService.FormationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    private FormationIService service;


    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','MANAGER','USER')")
    @PostMapping("/formation")
    public ResponseEntity<FormationDto> createFormation(@RequestBody FormationDto dto) {
        FormationDto saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public FormationDto update(@PathVariable String id, @RequestBody FormationDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public FormationDto getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping
    public List<FormationDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }
}
