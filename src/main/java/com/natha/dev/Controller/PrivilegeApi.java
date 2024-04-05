package com.natha.dev.Controller;

import com.natha.dev.Model.Privilege;
import com.natha.dev.ServiceImpl.PrivilegeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivilegeApi {
    @Autowired
    private PrivilegeImpl privilegeImpl;

    @PostMapping("/createNewPrivilege")
    public Privilege createNewPrivilege(@RequestBody Privilege privilege) {
        return privilegeImpl.createNewPrivilege(privilege);
    }
}
