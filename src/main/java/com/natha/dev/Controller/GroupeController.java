package com.natha.dev.Controller;

import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dto.GroupeDto;
import com.natha.dev.IService.GroupeIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class GroupeController {

    @Autowired
    private GroupeIService groupeIService;

    @Autowired
    private GroupeDao groupeDao;

    @GetMapping("/users/{usersId}/groupe")  // Changez groupeId en usersId
    List<GroupeDto> groupeByUsers(@PathVariable Long usersId) {
        return groupeIService.findByUsers(usersId);
    }

    @GetMapping("/commune/{communeId}/groupe")  // Changez groupeId en usersId
    List<GroupeDto> groupeByCommune(@PathVariable Long communeId) {
        return groupeIService.findByCommuneId(communeId);
    }

    @PostMapping("/groupe/commune/{communeId}")
    public ResponseEntity<GroupeDto> NewGroupe(@RequestBody GroupeDto groupeDto, @PathVariable Long communeId) {
        GroupeDto newComposanteDto = groupeIService.saveById(groupeDto, communeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComposanteDto);
    }

}
