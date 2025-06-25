package com.natha.dev.Dto.ServiceImpl;

import com.natha.dev.Dao.PrivilegeDao;
import com.natha.dev.Model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeImpl {

    @Autowired
    private PrivilegeDao privilegeDao;

    // Kreye nouvo privilèj
    public Privilege createNewPrivilege(Privilege privilege) {
        return privilegeDao.save(privilege);
    }

    // Ranmase tout privilèj ki egziste
    public List<Privilege> getAllPrivileges() {
        return privilegeDao.findAll();
    }

    // Efase privilèj pa non li (ID)
    public void deletePrivilege(String privilegeName) {
        privilegeDao.deleteById(privilegeName);
    }

    // Tcheke si li egziste, sinon kreye li
    public Privilege getOrCreatePrivilege(String name, String description) {
        Optional<Privilege> existing = privilegeDao.findById(name);
        if (existing.isPresent()) {
            return existing.get();
        } else {
            Privilege privilege = new Privilege(name, description);
            return privilegeDao.save(privilege);
        }
    }
}
