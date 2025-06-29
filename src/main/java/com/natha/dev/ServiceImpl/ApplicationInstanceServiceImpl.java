package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ApplicationInstanceDao;
import com.natha.dev.Dao.OrganizationDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.ApplicationInstanceDto;
import com.natha.dev.IService.ApplicationInstanceIService;
import com.natha.dev.Model.ApplicationInstance;
import com.natha.dev.Model.Organization;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationInstanceServiceImpl implements ApplicationInstanceIService {

    @Autowired
    private ApplicationInstanceDao applicationInstanceDao;
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<ApplicationInstanceDto> getApplicationsByUser(String userName) {
        // Jwenn aplikasyon yo soti nan repo
        List<ApplicationInstance> apps = applicationInstanceDao.findByUsersUserName(userName);

        // Map entities ApplicationInstance -> ApplicationInstanceDto
        return apps.stream()
                .map(app -> convertToDto(app))
                .collect(Collectors.toList());
    }


    @Override
    public ApplicationInstanceDto createStandaloneApp(ApplicationInstanceDto dto) {
        // Asire `organizationId` pa obligatwa
        dto.setOrganizationId(null);
        // mete default pou active, themeColor, status si ou vle
        if (dto.getActive() == null) dto.setActive(true);
        if (dto.getStatus() == null) dto.setStatus("ACTIVE");
        if (dto.getThemeColor() == null) dto.setThemeColor("default");

        return save(dto); // ap rele metòd save(dto)
    }


    @Override
    public ApplicationInstanceDto addUserToApplication(String userName, String appId) {
        Users user = userDao.findById(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ApplicationInstance app = applicationInstanceDao.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        user.getApplicationInstances().add(app); // ajoute app a user la
        app.getUsers().add(user); // ajoute user lan app la

        userDao.save(user); // oswa applicationInstanceRepository.save(app)
        return convertToDto(app);

    }


    @Override
    public List<Users> getUsersByApplication(String idApp) {
        ApplicationInstance app = applicationInstanceDao.findById(idApp)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        return new ArrayList<>(app.getUsers()); // Si ou pa vle Set
    }


    @Override
    public Optional<ApplicationInstanceDto> findById(String idApp) {
        Optional<ApplicationInstance> appOpt = applicationInstanceDao.findById(idApp);
        return appOpt.map(this::convertToDto);
    }

    @Override
    public List<ApplicationInstanceDto> findAll() {
        List<ApplicationInstance> apps = applicationInstanceDao.findAll();
        return apps.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Override
    public ApplicationInstanceDto save(ApplicationInstanceDto dto) {
        ApplicationInstance app = convertToEntity(dto);
        ApplicationInstance savedApp = applicationInstanceDao.save(app);
        return convertToDto(savedApp);
    }

    @Override
    public void deleteById(String idApp) {
        applicationInstanceDao.deleteById(idApp);
    }

    @Override
    public ApplicationInstanceDto save(ApplicationInstanceDto dto, String orgId) {
        Organization org = organizationDao.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found with ID: " + orgId));

        ApplicationInstance app = convertToEntity(dto);
        app.setOrganization(org);

        ApplicationInstance saved = applicationInstanceDao.save(app);
        return convertToDto(saved);
    }

    @Override
    public ApplicationInstanceDto saveByOrg(ApplicationInstanceDto dto, String orgId) {
        // 1. Chèche Organization a
        Organization org = organizationDao.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization non jwenn ak ID: " + orgId));

        // 2. Si DTO a gen yon idApp (li vle di li ap modifye), tcheke si app sa ekziste
        ApplicationInstance app;
        if (dto.getIdApp() != null && applicationInstanceDao.existsById(dto.getIdApp())) {
            app = applicationInstanceDao.findById(dto.getIdApp())
                    .orElseThrow(() -> new RuntimeException("Aplikasyon pa jwenn ak ID: " + dto.getIdApp()));
        } else {
            app = new ApplicationInstance(); // nouvo app
        }

        // 3. Mete tout valè ki soti nan DTO a
        app.setName(dto.getName());
        app.setDescription(dto.getDescription());
        app.setLogo(dto.getLogo());
        app.setLanguage(dto.getLanguage());
        app.setThemeColor(dto.getThemeColor());
        app.setStatus(dto.getStatus());
        app.setIsSandbox(dto.getIsSandbox());
        app.setCreatedBy(dto.getCreatedBy());


        // 4. Asosye org lan
        app.setOrganization(org);

        // 5. Sove epi retounen DTO
        ApplicationInstance savedApp = applicationInstanceDao.save(app);
        return convertToDto(savedApp);
    }


    private ApplicationInstanceDto convertToDto(ApplicationInstance app) {
        ApplicationInstanceDto dto = new ApplicationInstanceDto();
        dto.setIdApp(app.getIdApp());
        dto.setName(app.getName());
        dto.setDescription(app.getDescription());
        dto.setLogo(app.getLogo());
        dto.setStatus(app.getStatus());
        dto.setIsSandbox(app.getIsSandbox());
        dto.setCreatedAt(app.getCreatedAt());
        dto.setUpdatedAt(app.getUpdatedAt());
        dto.setLastModifiedDate(app.getLastModifiedDate());
        dto.setCreatedBy(app.getCreatedBy());
        dto.setLanguage(app.getLanguage());
        dto.setThemeColor(app.getThemeColor());

        if (app.getOrganization() != null) {
            dto.setOrganizationId(app.getOrganization().getIdorg());
            dto.setOrganizationName(app.getOrganization().getName());
        }

        return dto;
    }

    private ApplicationInstance convertToEntity(ApplicationInstanceDto dto) {
        ApplicationInstance app = new ApplicationInstance();
        app.setIdApp(dto.getIdApp());
        app.setName(dto.getName());
        app.setDescription(dto.getDescription());
        app.setLogo(dto.getLogo());
        app.setStatus(dto.getStatus());
        app.setIsSandbox(dto.getIsSandbox());
        app.setCreatedAt(dto.getCreatedAt());
        app.setUpdatedAt(dto.getUpdatedAt());
        app.setLastModifiedDate(dto.getLastModifiedDate());
        app.setCreatedBy(dto.getCreatedBy());
        app.setLanguage(dto.getLanguage());
        app.setThemeColor(dto.getThemeColor());

        if (dto.getOrganizationId() != null) {
            Organization org = new Organization();
            org.setIdorg(dto.getOrganizationId());
            app.setOrganization(org);
        }

        return app;
    }
}
