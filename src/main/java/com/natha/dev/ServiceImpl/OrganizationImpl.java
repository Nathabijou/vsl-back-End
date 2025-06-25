package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.MySystemDao;
import com.natha.dev.Dao.OrganizationDao;

import com.natha.dev.Dto.OrganizationDto;
import com.natha.dev.Dto.OrganizationDtoLimited;
import com.natha.dev.Exeption.OrganizationNotFoundException;
import com.natha.dev.IService.OrganizationIService;

import com.natha.dev.Model.MySystem;
import com.natha.dev.Model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class OrganizationImpl  implements OrganizationIService {

    @Autowired
    private MySystemDao mySystemDao;
    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public Optional<OrganizationDto> findById(String idorg) {
        Optional<Organization> organization= organizationDao.findById(idorg);
        return organization.map(this::convertToDto);
    }

    @Override
    public List<OrganizationDto> findAll() {
        List<Organization> organizations = organizationDao.findAll();
        return convertToDtoList(organizations);
    }

    private List<OrganizationDto> convertToDtoList(List<Organization> organizations) {
        return organizations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String idorg) {
        organizationDao.deleteById(idorg);
    }


    @Override
    public OrganizationDto save(OrganizationDto organizationDto) {
        Organization organization = convertToEntity(organizationDto);
        Organization savedOrganisation = organizationDao.save(organization);
        return convertToDto(savedOrganisation);
    }


    @Override
    public Optional<OrganizationDto> findByIdAndMySystemId(String orgId, Long mySystemId) {
        Optional<Organization> org = organizationDao.findByIdorgAndMySystemId(orgId, mySystemId);
        return org.map(this::convertToDto);
    }


    @Override
    public List<OrganizationDto> findByMySystemId(Long mySystemId) {
        List<Organization> organizations = organizationDao.findByMySystemId(mySystemId);
        return organizations.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    @Override
    public OrganizationDto saveById(OrganizationDto organizationDto, Long mySystemId) {
        MySystem mySystemParent = mySystemDao.findById(mySystemId).orElse(null);

        if (mySystemParent == null) {
            throw new OrganizationNotFoundException("MySystem introuvable avec l'ID : " + mySystemId);
        }

        Organization organization = new Organization();

        organization.setIdorg(organizationDto.getIdorg());
        organization.setActive(organizationDto.getActive());              // boolean active
        organization.setContactEmail(organizationDto.getContactEmail());
        organization.setDescription(organizationDto.getDescription());
        organization.setAddress(organizationDto.getAddress());
        organization.setCurrency(organizationDto.getCurrency());
        organization.setEdition(organizationDto.getEdition());
        organization.setLanguage(organizationDto.getLanguage());
        organization.setCreatedAt(organizationDto.getCreatedAt());
        organization.setName(organizationDto.getName());
        organization.setCreatedBy(organizationDto.getCreatedBy());
        organization.setIsSandbox(organizationDto.getIsSandbox());
        organization.setLastModifiedDate(organizationDto.getLastModifiedDate());
        organization.setStartDate(organizationDto.getStartDate());
        organization.setPhoneNumber(organizationDto.getPhoneNumber());
        organization.setTrialExpirationDate(organizationDto.getTrialExpirationDate());
        organization.setType(organizationDto.getType());
        organization.setUpdatedAt(organizationDto.getUpdatedAt());

        organization.setMySystem(mySystemParent);

        Organization savedOrganization = organizationDao.save(organization);

        return convertToDto(savedOrganization);
    }



    private Organization convertToEntity(OrganizationDto organizationDto) {
        Organization organization =  new Organization();
        organization.setIdorg(organizationDto.getIdorg());
        organization.setTrialExpirationDate(organizationDto.getTrialExpirationDate());
        organization.setType(organizationDto.getType());
        organization.setName(organizationDto.getName());

        organization.setAddress(organizationDto.getAddress());
        organization.setEdition(organizationDto.getEdition());
        organization.setActive(organizationDto.getActive());
        organization.setCurrency(organizationDto.getCurrency());
        organization.setLanguage(organizationDto.getLanguage());
        organization.setUpdatedAt(organizationDto.getUpdatedAt());
        organization.setPhoneNumber(organizationDto.getPhoneNumber());
        organization.setCreatedBy(organizationDto.getCreatedBy());
        organization.setDescription(organizationDto.getDescription());
        organization.setContactEmail(organizationDto.getContactEmail());
        organization.setCreatedAt(organizationDto.getCreatedAt());
        organization.setIsSandbox(organizationDto.getIsSandbox());
        organization.setStartDate(organizationDto.getStartDate());
        organization.setLastModifiedDate(organizationDto.getLastModifiedDate());

        return organization;
    }

    private OrganizationDto convertToDto(Organization organization) {
        OrganizationDto dto = new OrganizationDto();
        dto.setIdorg(organization.getIdorg());
        dto.setAddress(organization.getAddress());
        dto.setCurrency(organization.getCurrency());
        dto.setActive(organization.getActive());
        dto.setEdition(organization.getEdition());
        dto.setName(organization.getName());
        dto.setLanguage(organization.getLanguage());
        dto.setDescription(organization.getDescription());
        dto.setType(organization.getType());
        dto.setContactEmail(organization.getContactEmail());
        dto.setPhoneNumber(organization.getPhoneNumber());

        dto.setIsSandbox(organization.getIsSandbox());
        dto.setCreatedAt(organization.getCreatedAt());
        dto.setUpdatedAt(organization.getUpdatedAt());
        dto.setLastModifiedDate(organization.getLastModifiedDate());
        dto.setTrialExpirationDate(organization.getTrialExpirationDate());
        dto.setStartDate(organization.getStartDate());
        dto.setCreatedBy(organization.getCreatedBy());
        return dto;
    }

    @Override
    public OrganizationDto updateByIdAndSystemId(String idorg, Long mySystemId, OrganizationDto organizationDto) {
        Optional<Organization> optionalOrg = organizationDao.findByIdorgAndMySystemId(idorg, mySystemId);

        if (optionalOrg.isPresent()) {
            Organization organization = optionalOrg.get();

            organization.setName(organizationDto.getName());
            organization.setDescription(organizationDto.getDescription());
            organization.setAddress(organizationDto.getAddress());
            organization.setContactEmail(organizationDto.getContactEmail());
            organization.setPhoneNumber(organizationDto.getPhoneNumber());
            organization.setMaxAppsAllowed(organizationDto.getMaxAppsAllowed());
            organization.setActive(organizationDto.getActive());
            organization.setEdition(organizationDto.getEdition());
            organization.setLanguage(organizationDto.getLanguage());
            organization.setCurrency(organizationDto.getCurrency());
            organization.setType(organizationDto.getType());
            organization.setIsSandbox(organizationDto.getIsSandbox());
            organization.setUpdatedAt(LocalDateTime.now());

            Organization updatedOrg = organizationDao.save(organization);
            return convertToDto(updatedOrg);
        } else {
            throw new OrganizationNotFoundException("Organization non trouvée avec ID: " + idorg + " et système ID: " + mySystemId);
        }
    }


    public OrganizationDtoLimited convertToLimitedDto(Organization organization) {
        OrganizationDtoLimited dto = new OrganizationDtoLimited();
        dto.setIdorg(organization.getIdorg());
        dto.setName(organization.getName());
        dto.setType(organization.getType());
        dto.setEdition(organization.getEdition());

        dto.setIsSandbox(organization.getIsSandbox());
        dto.setContactEmail(organization.getContactEmail());
        dto.setPhoneNumber(organization.getPhoneNumber());
        dto.setAddress(organization.getAddress());
        dto.setDescription(organization.getDescription());
        dto.setLanguage(organization.getLanguage());
        dto.setCurrency(organization.getCurrency());
        dto.setActive(organization.getActive());
        return dto;
    }



}
