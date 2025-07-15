package com.natha.dev.Controller;

import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dto.GroupeDto;
import com.natha.dev.IService.GroupeIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
public class GroupeController {

    @Autowired
    private GroupeIService groupeIService;

    @Autowired
    private GroupeDao groupeDao;


    // rekipere tout gwoup pou yon moun specifiqie
    @PreAuthorize("permitAll()")
    @GetMapping("/users/{userName}/groupe")
    List<GroupeDto> groupeByUsers(@PathVariable("userName") String userName) {
        return groupeIService.findByUsers(userName);
    }

    // rekipere tout group
    @PreAuthorize("permitAll()")
    @GetMapping("/allgroups")
    public ResponseEntity<List<GroupeDto>> getAllGroupes() {
        List<GroupeDto> groupes = groupeIService.findAll();
        return ResponseEntity.ok(groupes);
    }


    // rekipere tout gwoup pou yon Commune specifiqie
    @PreAuthorize("permitAll()")
    @GetMapping("/commune/{communeId}/groupe")  // Changez groupeId en usersId
    List<GroupeDto> groupeByCommune(@PathVariable Long communeId) {
        return groupeIService.findByCommuneId(communeId);
    }

    // kreye gwoup nan yon commune specifiqie
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Manager')")
    @PostMapping("/groupe/commune/{communeId}")
    public ResponseEntity<GroupeDto> NewGroupe(@RequestBody GroupeDto groupeDto, @PathVariable Long communeId) {
        GroupeDto newComposanteDto = groupeIService.saveById(groupeDto, communeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComposanteDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/groupe/{id}")
    public ResponseEntity<GroupeDto> getGroupeById(@PathVariable Long id) {
        Optional<GroupeDto> groupeOpt = groupeIService.findById(id);
        if (groupeOpt.isPresent()) {
            return ResponseEntity.ok(groupeOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/groupe/{id}")
    public ResponseEntity<GroupeDto> updateGroupe(@PathVariable Long id, @RequestBody GroupeDto groupeDto) {
        groupeDto.setId(id); // Mete ID a
        GroupeDto updated = groupeIService.update(groupeDto);
        return ResponseEntity.ok(updated);
    }



    @PreAuthorize("hasRole('ROLE_Admin')")
    @DeleteMapping("/groupe/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        Optional<GroupeDto> groupeOpt = groupeIService.findById(id);
        if (groupeOpt.isPresent()) {
            groupeIService.deleteById(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }


}
