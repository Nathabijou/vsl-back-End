package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PrivilegeDao;
import com.natha.dev.Model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeImpl {
    @Autowired
    private PrivilegeDao privilegeDao;

    public Privilege createNewPrivilege(Privilege privilege) {
        return privilegeDao.save(privilege);
    }
}
