package com.natha.dev.Controller;

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

    // 🔍see
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/mysystem/{mySystemId}/organization/{orgId}")
    public ResponseEntity<OrganizationDto> getOrganizationFromMySystem(
            @PathVariable Long mySystemId,
            @PathVariable String orgId) {

        return organizationIService.findByIdAndMySystemId(orgId, mySystemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userName}/organizations")
    public List<OrganizationDto> getOrganizationsByUser(@PathVariable String userName) {
        return organizationIService.findByUserName(userName);
    }


    // ✅ Create Org : (Yes Verify)
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/create/{mySystemId}")
    public ResponseEntity<?> createOrganizationByMySystem(
            @PathVariable Long mySystemId,
            @RequestBody OrganizationDto organizationDto) {
        try {
            OrganizationDto savedOrg = organizationIService.saveById(organizationDto, mySystemId);
            return ResponseEntity.ok(savedOrg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating organization: " + e.getMessage());
        }
    }

    // 🔁 See All Org (Yes Verify)
   // @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/allOrganisations")
    public List<OrganizationDto> getAllOrganizations() {
        return organizationIService.findAll();
    }

    // ✏️ Modify org: (Yes Verify)
    //@PreAuthorize("hasAnyRole('SUPERADMIN')")
    @PutMapping("/mysystem/{mySystemId}/organization/{idorg}")
    public ResponseEntity<OrganizationDto> updateOrganizationBySystem(
            @PathVariable Long mySystemId,
            @PathVariable String idorg,
            @RequestBody OrganizationDto organizationDto) {

        OrganizationDto updatedOrg = organizationIService.updateByIdAndSystemId(idorg, mySystemId, organizationDto);
        return ResponseEntity.ok(updatedOrg);
    }

    // 🔍 see org by Id  system: (Yes Verify)
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/{idorg}/mysystem/{mySystemId}")
    public Optional<OrganizationDto> getByIdAndSystem(
            @PathVariable String idorg,
            @PathVariable Long mySystemId) {
        return organizationIService.findByIdAndMySystemId(idorg, mySystemId);
    }

    // 🔍 see All prg on system (Yes Verify)
    //@PreAuthorize("hasAnyRole('SUPERADMIN')")
    @GetMapping("/mysystem/{mySystemId}/organizations")
    public List<OrganizationDto> getOrganizationsByMySystem(@PathVariable Long mySystemId) {
        return organizationIService.findByMySystemId(mySystemId);
    }

    // ❌ Delete ganizasyon: (Yes Verify)
    //@PreAuthorize("hasAnyRole('SUPERADMIN')")
    @DeleteMapping("/{idorg}")
    public void deleteOrganization(@PathVariable String idorg) {
        organizationIService.deleteById(idorg);
    }

    // 🚫 Dezaktive org (Yes Verify)
    //@PreAuthorize("hasAnyRole('SUPERADMIN')")
    @PutMapping("/deactivate/{idorg}")
    public ResponseEntity<OrganizationDto> deactivateOrganization(@PathVariable String idorg) {
        Optional<OrganizationDto> optOrg = organizationIService.findById(idorg);
        if (optOrg.isPresent()) {
            OrganizationDto org = optOrg.get();
            org.setActive(false); // ✅ Sipoze ou itilize 'active' olye de 'status'
            return ResponseEntity.ok(organizationIService.save(org));
        }
        return ResponseEntity.notFound().build();
    }
    //Acivate Org (Yes Verify)
    //@PreAuthorize("hasAnyRole('SUPERADMIN')")
    @PutMapping("/activate/{idorg}")
    public ResponseEntity<OrganizationDto> ActiveOrganization(@PathVariable String idorg) {
        Optional<OrganizationDto> optOrg = organizationIService.findById(idorg);
        if (optOrg.isPresent()) {
            OrganizationDto org = optOrg.get();
            org.setActive(true); // ✅ Sipoze ou itilize 'active' olye de 'status'
            return ResponseEntity.ok(organizationIService.save(org));
        }
        return ResponseEntity.notFound().build();
    }


    // ✅ Get org by ID (SAN mySystemId)
    @GetMapping("/programs/{idorg}")
    public ResponseEntity<OrganizationDto> findOrganizationById(@PathVariable String idorg) {
        return organizationIService.findById(idorg)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
