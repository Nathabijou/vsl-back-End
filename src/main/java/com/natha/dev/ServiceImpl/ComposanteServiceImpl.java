package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ApplicationInstanceDao;
import com.natha.dev.Dao.ComposanteDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.ComposanteDto;
import com.natha.dev.Dto.UsersDto;
import com.natha.dev.IService.ComposanteIService;
import com.natha.dev.Model.ApplicationInstance;
import com.natha.dev.Model.Composante;
import com.natha.dev.Model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComposanteServiceImpl implements ComposanteIService {

    @Autowired
    private ComposanteDao composanteDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationInstanceDao applicationInstanceDao;

    @Override
    public List<ComposanteDto> getComposantesByUserName(String userName) {
        List<Composante> composantes = composanteDao.findByUsersUserName(userName);
        return composantes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    @Override
    public void removeUserFromComposante(String userName, Long composanteId) {
        Users user = userDao.findById(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Composante composante = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante not found"));

        // Retire relasyon an
        user.getComposantes().remove(composante);

        // Sove itilizatè a pou mete ajou many-to-many la
        userDao.save(user);
    }


    @Override
    public List<UsersDto> getUsersByComposanteId(Long composanteId) {
        Composante composante = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante not found"));

        Set<Users> users = composante.getUsers();

        return users.stream()
                .map(user -> new UsersDto(user.getUserName(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail()))
                .collect(Collectors.toList());
    }




    @Override
    public List<ComposanteDto> findByApplicationAndUser(String applicationId, String username) {
        List<Composante> composantes = composanteDao.findByApplicationIdAndUser(applicationId, username);
        return composantes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<Users> findUsersByComposante(Long composanteId) {
        return composanteDao.findUsersByComposanteId(composanteId);
    }

    @Transactional
    @Override
    public void assignUserToComposante(String userName, Long composanteId) {
        Users user = userDao.findById(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Composante composante = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante not found"));

        // Ajoute relasyon an
        user.getComposantes().add(composante);

        // Sove user la pou li mete l nan tab many-to-many la
        userDao.save(user);
    }



    @Override
    public List<ComposanteDto> findAll() {
        List<Composante> composantes = composanteDao.findAll();
        return composantes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByApplication(String applicationId, Long composanteId) {
        ApplicationInstance appInstance = applicationInstanceDao.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("App not found"));

        Composante composante = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante not found"));

        if (!composante.getApplicationInstance().getId().equals(applicationId)) {
            throw new RuntimeException("the Composante is not on the app!");
        }

        composanteDao.deleteById(composanteId);
    }

    @Override
    public ComposanteDto updateByApplication(String applicationId, Long id, ComposanteDto dto) {
        ApplicationInstance appInstance = applicationInstanceDao.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("App not found"));

        Composante composante = composanteDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Composante not found"));

        if (!composante.getApplicationInstance().getId().equals(applicationId)) {
            throw new RuntimeException("Composante pa fè pati app sa!");
        }

        composante.setNom(dto.getNom());
        composante.setDescription(dto.getDescription());

        Composante updated = composanteDao.save(composante);
        return convertToDto(updated);
    }


    @Override
    public ComposanteDto save(ComposanteDto dto, String applicationId) {
        ApplicationInstance appInstance = applicationInstanceDao.findById(applicationId).orElseThrow(() ->
                new RuntimeException("ApplicationInstance not found with ID: " + applicationId));

        Composante composante = new Composante();
        composante.setNom(dto.getNom());
        composante.setDescription(dto.getDescription());
        composante.setCreatedBy(dto.getCreatedBy());
        composante.setApplicationInstance(appInstance); // ✅ mete applicationInstance

        Composante saved = composanteDao.save(composante);
        return convertToDto(saved);
    }

    @Override
    public List<ComposanteDto> findByApplication(String applicationId) {
        return composanteDao.findByApplicationInstanceId(applicationId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComposanteDto update(Long id, ComposanteDto dto) {
        Composante composante = composanteDao.findById(id).orElseThrow(() ->
                new RuntimeException("Composante not found with ID: " + id));

        composante.setNom(dto.getNom());
        composante.setDescription(dto.getDescription());

        Composante updated = composanteDao.save(composante);
        return convertToDto(updated);
    }

    @Override
    public void delete(Long id) {
        composanteDao.deleteById(id);
    }

    private ComposanteDto convertToDto(Composante composante) {
        ComposanteDto dto = new ComposanteDto();
        dto.setId(composante.getId());
        dto.setNom(composante.getNom());
        dto.setDescription(composante.getDescription());
        dto.setCreatedBy(composante.getCreatedBy());
        dto.setApplicationId(composante.getApplicationInstance().getId()); // ✅
        return dto;
    }
}
