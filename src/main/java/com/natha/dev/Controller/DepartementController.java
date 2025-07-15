package com.natha.dev.Controller;

import com.natha.dev.Dto.DepartementDto;
import com.natha.dev.IService.DepartementIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
@CrossOrigin("http://localhost:4200")
public class DepartementController {

    @Autowired
    private DepartementIService service;

    // POST /api/departements
    @PostMapping
    public DepartementDto create(@RequestBody DepartementDto dto) {
        return service.save(dto);
    }

    // GET /api/departements
    @GetMapping
    public List<DepartementDto> getAll() {
        return service.getAll();
    }

    // DELETE /api/departements/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
