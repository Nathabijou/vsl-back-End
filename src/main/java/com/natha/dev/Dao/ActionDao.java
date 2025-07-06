package com.natha.dev.Dao;

import com.natha.dev.Model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActionDao extends JpaRepository<Action, Long> {
    List<Action> findByAccount_Id(Long accountId);
}
