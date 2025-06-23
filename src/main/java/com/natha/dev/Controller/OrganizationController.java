package com.natha.dev.Controller;

import com.natha.dev.Dao.OrganizationDao;
import com.natha.dev.Dto.OrganizationDto;
import com.natha.dev.IService.OrganizationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
public class OrganizationController {

    @Autowired
    private OrganizationIService organizationIService;

    // 🔍 Wè yon òganizasyon: Admin & SuperAdmin
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    @GetMapping("/mysystem/{mySystemId}/organization/{orgId}")
    public ResponseEntity<OrganizationDto> getOrganizationFromMySystem(
            @PathVariable Long mySystemId,
            @PathVariable String orgId) {

        return organizationIService.findByIdAndMySystemId(orgId, mySystemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Kreye òganizasyon: Admin & SuperAdmin
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    @PostMapping("/create/{mySystemId}")
    public OrganizationDto createOrganizationByMySystem(
            @PathVariable Long mySystemId,
            @RequestBody OrganizationDto organizationDto
    ) {
        return organizationIService.saveById(organizationDto, mySystemId);
    }

    // 🔁 Wè tout òganizasyon: Admin & SuperAdmin
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    @GetMapping("/AllOrg")
    public List<OrganizationDto> getAllOrganizations() {
        return organizationIService.findAll();
    }

    // ❌ Efase òganizasyon: SuperAdmin sèlman
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @DeleteMapping("/{idorg}")
    public void deleteOrganization(@PathVariable String idorg) {
        organizationIService.deleteById(idorg);
    }

    // 🔍 Wè org pa ID & system: Admin & SuperAdmin
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    @GetMapping("/{idorg}/system/{mySystemId}")
    public Optional<OrganizationDto> getByIdAndSystem(
            @PathVariable String idorg,
            @PathVariable Long mySystemId
    ) {
        return organizationIService.findByIdAndMySystemId(idorg, mySystemId);
    }

    // 🔍 Wè tout org nan yon system: Admin & SuperAdmin
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    @GetMapping("/mysystem/{mySystemId}/organizations")
    public List<OrganizationDto> getOrganizationsByMySystem(@PathVariable Long mySystemId) {
        return organizationIService.findByMySystemId(mySystemId);
    }

    // ✏️ Modifye org: SuperAdmin sèlman
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @PutMapping("/mysystem/{mySystemId}/organization/{idorg}")
    public ResponseEntity<OrganizationDto> updateOrganizationBySystem(
            @PathVariable Long mySystemId,
            @PathVariable String idorg,
            @RequestBody OrganizationDto organizationDto
    ) {
        OrganizationDto updatedOrg = organizationIService.updateByIdAndSystemId(idorg, mySystemId, organizationDto);
        return ResponseEntity.ok(updatedOrg);
    }
}





