package com.natha.dev.Controller;

import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.ComposanteDto;
import com.natha.dev.Dto.UsersDto;
import com.natha.dev.IService.ComposanteIService;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ComposanteController {

    @Autowired
    private ComposanteIService composanteIService;

    @Autowired
    public UserDao userDao;

    @GetMapping("/users/{userName}/composantes")
    public ResponseEntity<List<ComposanteDto>> getComposantesByUserName(@PathVariable String userName) {
        List<ComposanteDto> composantes = composanteIService.getComposantesByUserName(userName);
        return ResponseEntity.ok(composantes);
    }


    @GetMapping("/composantess/{composanteId}/users")
    public ResponseEntity<List<UsersDto>> getUsersByComposante(@PathVariable Long composanteId) {
        List<UsersDto> users = composanteIService.getUsersByComposanteId(composanteId);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/composante/{composanteId}/users/{userName}")
    public ResponseEntity<?> removeUserFromComposante(
            @PathVariable Long composanteId,
            @PathVariable String userName) {
        composanteIService.removeUserFromComposante(userName, composanteId);
        return ResponseEntity.ok("User removed from composante");
    }


    //Adduser to component (Yes Verify)
    @PostMapping("/composantes/{composanteId}/users/{userName}")
    public ResponseEntity<?> assignUserToComposante(
            @PathVariable Long composanteId,
            @PathVariable String userName) {
        composanteIService.assignUserToComposante(userName, composanteId);
        return ResponseEntity.ok("User added to composante");
    }

    // cheche li composantant nan yon application pou yon user
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/composantes/by-application-and-user/{applicationId}/{username}")
    public List<ComposanteDto> getByApplicationAndUser(
            @PathVariable String applicationId,
            @PathVariable String username
    ) {
        return composanteIService.findByApplicationAndUser(applicationId, username);
    }


    @GetMapping("/{id}/users")
    public ResponseEntity<List<Users>> getUsersForComposante(@PathVariable Long id) {
        return ResponseEntity.ok(composanteIService.findUsersByComposante(id));
    }


    // create composante with Application ID (Yes Verify)
   // @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
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
    //@PreAuthorize("hasAnyRole('SUPERADMIN')")
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
    //@PreAuthorize("hasAnyRole('SUPERADMIN')")
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
