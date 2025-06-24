package com.natha.dev.Controller;

import com.natha.dev.Dto.ComposanteDto;
import com.natha.dev.IService.ComposanteIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ComposanteController {

    @Autowired
    private ComposanteIService composanteIService;

    // create composante with Application ID (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/composantes/create/{applicationId}")
    public ResponseEntity<ComposanteDto> create(
            @PathVariable String applicationId,
            @RequestBody ComposanteDto dto) {
        ComposanteDto saved = composanteIService.save(dto, applicationId);
        return ResponseEntity.ok(saved);
    }

    // Find composante with Application ID (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/composantes/by-application/{applicationId}")
    public List<ComposanteDto> getByApp(@PathVariable String applicationId) {
        return composanteIService.findByApplication(applicationId);
    }



    // get All Component  (yes verify)
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @GetMapping("/composantes/all")
    public List<ComposanteDto> getAllComposantes() {
        return composanteIService.findAll();  // Ou ajoute method findAll() nan service si poko genyen
    }

    // Modify Component with App  (yes verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/composantes/{applicationId}/{composanteId}")
    public ResponseEntity<ComposanteDto> updateComposanteOfApp(
            @PathVariable String applicationId,
            @PathVariable Long composanteId,
            @RequestBody ComposanteDto dto) {

        ComposanteDto updated = composanteIService.updateByApplication(applicationId, composanteId, dto);
        return ResponseEntity.ok(updated);
    }


    // Metòd update
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @PutMapping("/composantes/{id}")
    public ResponseEntity<ComposanteDto> updateComposante(
            @PathVariable Long id,
            @RequestBody ComposanteDto dto) {
        ComposanteDto updated = composanteIService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // delete component with App (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/composantes/{applicationId}/{composanteId}")
    public ResponseEntity<Void> deleteComposanteOfApp(
            @PathVariable String applicationId,
            @PathVariable Long composanteId) {

        composanteIService.deleteByApplication(applicationId, composanteId);
        return ResponseEntity.noContent().build();
    }
}
