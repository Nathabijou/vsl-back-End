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

@RestController
@CrossOrigin("http://localhost:4200")
public class GroupeController {

    @Autowired
    private GroupeIService groupeIService;

    @Autowired
    private GroupeDao groupeDao;


    // rekipere tout gwoup pou yon moun specifiqie
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER')")
    @GetMapping("/users/{userName}/groupe")
    List<GroupeDto> groupeByUsers(@PathVariable("userName") String userName) {
        return groupeIService.findByUsers(userName);
    }

    // rekipere tout group
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/allgroups")
    public ResponseEntity<List<GroupeDto>> getAllGroupes() {
        List<GroupeDto> groupes = groupeIService.findAll();
        return ResponseEntity.ok(groupes);
    }


    // rekipere tout gwoup pou yon Commune specifiqie
    @GetMapping("/commune/{communeId}/groupe")  // Changez groupeId en usersId
    List<GroupeDto> groupeByCommune(@PathVariable Long communeId) {
        return groupeIService.findByCommuneId(communeId);
    }

    // kreye gwoup nan yon  commune specifiqie
    @PostMapping("/groupe/commune/{communeId}")
    public ResponseEntity<GroupeDto> NewGroupe(@RequestBody GroupeDto groupeDto, @PathVariable Long communeId) {
        GroupeDto newComposanteDto = groupeIService.saveById(groupeDto, communeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComposanteDto);
    }

}
