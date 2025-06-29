package com.natha.dev.IService;

        import com.natha.dev.Dto.OrganizationDto;

        import java.util.List;
        import java.util.Optional;

public interface OrganizationIService {
    Optional<OrganizationDto> findById(String idorg);
    List<OrganizationDto> findAll();
    void deleteById(String idorg);
    OrganizationDto  save(OrganizationDto organizationDto);

    Optional<OrganizationDto> findByIdAndMySystemId(String orgId, Long mySystemId);

    List<OrganizationDto> findByMySystemId(Long mySystemId);
    OrganizationDto saveById(OrganizationDto organizationDto, Long mySystemId);

    OrganizationDto updateByIdAndSystemId(String idorg, Long mySystemId, OrganizationDto organizationDto);


    List<OrganizationDto> findByUserName(String userName);
}
