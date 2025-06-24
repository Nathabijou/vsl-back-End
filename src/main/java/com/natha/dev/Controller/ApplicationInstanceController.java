package com.natha.dev.Controller;

import com.natha.dev.Dto.ApplicationInstanceDto;
import com.natha.dev.IService.ApplicationInstanceIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
public class ApplicationInstanceController {

    @Autowired
    private ApplicationInstanceIService applicationInstanceService;

    // 🔍 Get tout aplikasyon yo
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/allApplicationInstance")
    public List<ApplicationInstanceDto> getAll() {
        return applicationInstanceService.findAll();
    }

    // ➕ Create Application With Org( Verify)
    @PostMapping("/organization/{orgId}/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ApplicationInstanceDto> createAppInOrg(
            @PathVariable String orgId,
            @RequestBody ApplicationInstanceDto dto) {

        ApplicationInstanceDto saved = applicationInstanceService.save(dto, orgId); // ✅ BON
        return ResponseEntity.ok(saved);
    }



    // 🔍 Get All App ( Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/{idApp}")
    public ResponseEntity<ApplicationInstanceDto> getById(@PathVariable String idApp) {
        return applicationInstanceService.findById(idApp)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    // ✏️ Modifye aplikasyon ki egziste
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/Application/{idApp}")
    public ResponseEntity<ApplicationInstanceDto> update(@PathVariable String idApp, @RequestBody ApplicationInstanceDto dto) {
        dto.setIdApp(idApp);
        ApplicationInstanceDto updated = applicationInstanceService.save(dto);
        return ResponseEntity.ok(updated);
    }

    //Update Application With Org (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/organization/{orgId}/application/{idApp}")
    public ResponseEntity<ApplicationInstanceDto> updateAppInOrg(
            @PathVariable String orgId,
            @PathVariable String idApp,
            @RequestBody ApplicationInstanceDto dto) {

        dto.setIdApp(idApp);
        ApplicationInstanceDto updated = applicationInstanceService.saveByOrg(dto, orgId); // Metòd ki verifye orgId
        return ResponseEntity.ok(updated);
    }


    // ❌ Delete App with Org
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @DeleteMapping("/Application/{idApp}")
    public ResponseEntity<Void> delete(@PathVariable String idApp) {
        applicationInstanceService.deleteById(idApp);
        return ResponseEntity.noContent().build();
    }

    // 🚫 Dezaktive aplikasyon (sipoze gen 'active' nan DTO)
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @PutMapping("/deactivate/{idApp}")
    public ResponseEntity<ApplicationInstanceDto> deactivateApplication(@PathVariable String idApp) {
        Optional<ApplicationInstanceDto> optApp = applicationInstanceService.findById(idApp);
        if (optApp.isPresent()) {
            ApplicationInstanceDto app = optApp.get();
            app.setActive(false);
            return ResponseEntity.ok(applicationInstanceService.save(app));
        }
        return ResponseEntity.notFound().build();
    }

    // 🚀 Aktive aplikasyon
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @PutMapping("/activate/{idApp}")
    public ResponseEntity<ApplicationInstanceDto> activateApplication(@PathVariable String idApp) {
        Optional<ApplicationInstanceDto> optApp = applicationInstanceService.findById(idApp);
        if (optApp.isPresent()) {
            ApplicationInstanceDto app = optApp.get();
            app.setActive(true);
            return ResponseEntity.ok(applicationInstanceService.save(app));
        }
        return ResponseEntity.notFound().build();
    }
}
