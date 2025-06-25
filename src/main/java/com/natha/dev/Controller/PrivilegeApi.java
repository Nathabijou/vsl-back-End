package com.natha.dev.Controller;

import com.natha.dev.Model.Privilege;
import com.natha.dev.Dto.ServiceImpl.PrivilegeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/privileges")
@CrossOrigin("http://localhost:4200")
public class PrivilegeApi {

    @Autowired
    private PrivilegeImpl privilegeImpl;

    // API pou kreye privilèj
    @PostMapping("/create")
    public Privilege createNewPrivilege(@RequestBody Privilege privilege) {
        return privilegeImpl.createNewPrivilege(privilege);
    }

    // API pou lis tout privilèj
    @GetMapping("/all")
    public List<Privilege> getAllPrivileges() {
        return privilegeImpl.getAllPrivileges();
    }

    // API pou efase yon privilèj
    @DeleteMapping("/delete/{name}")
    public void deletePrivilege(@PathVariable String name) {
        privilegeImpl.deletePrivilege(name);
    }

    // API pou tcheke/kreye otomatikman
    @PostMapping("/getOrCreate")
    public Privilege getOrCreatePrivilege(@RequestBody Privilege privilege) {
        return privilegeImpl.getOrCreatePrivilege(privilege.getPrivilegeName(), privilege.getPrivilegeDescription());
    }
}
