package com.natha.dev.IService;


import com.natha.dev.Model.Action;  // Import model Action ou a
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface ActionIService {

    Action save(Action action);
    List<Action> findAllByAccountId(String accountId);

    Action update(Action action);

    @Transactional
    void deleteById(Long id);

    Optional<Action> findById(Long id);
}
