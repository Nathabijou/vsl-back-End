package com.natha.dev.Controller;

import com.natha.dev.Dto.RepartitionDto;
import com.natha.dev.IService.IRepartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RepartitionController {

    @Autowired
    private IRepartitionService repartitionService;

    @GetMapping("/api/repartitions/groupe/{groupeId}")
    public ResponseEntity<List<RepartitionDto>> getRepartitionByGroupe(@PathVariable Long groupeId) {
        List<RepartitionDto> repartitionList = repartitionService.getRepartitionByGroupe(groupeId);
        return ResponseEntity.ok(repartitionList);
    }
}
