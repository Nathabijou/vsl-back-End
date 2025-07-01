package com.natha.dev.Controller;

import com.natha.dev.IService.DashboardIService;
import com.natha.dev.Model.DashboardFilter;
import com.natha.dev.Dto.KpiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api") // ajoute prefix si ou vle
public class DashboardController {

    @Autowired
    private DashboardIService dashboardIService;

    @GetMapping("/dashboard")
    public ResponseEntity<KpiResponse> getDashboard(@ModelAttribute DashboardFilter filter, Principal principal) {
        String username = principal.getName();
        KpiResponse response = dashboardIService.getKpiData(filter, username);
        return ResponseEntity.ok(response);
    }

}
