package com.natha.dev.ServiceImpl;


import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.ComposanteDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dao.QuartierDao;
import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.Dto.ProjetDto;
import com.natha.dev.IService.ProjetIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Composante;
import com.natha.dev.Model.Projet;
import com.natha.dev.Model.Quartier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetImpl implements ProjetIService {

    @Autowired
    private ProjetDao projetDao;

    @Autowired
    private ComposanteDao composanteDao;

    @Autowired
    private BeneficiaireDao beneficiaireDao;

    @Autowired
    private QuartierDao quartierDao;

    @Override
    public ProjetDto save(ProjetDto dto) {
        Projet projet = convertToEntity(dto);
        Projet saved = projetDao.save(projet);
        return convertToDto(saved);
    }

    @Override
    public ProjetDto updateInComposante(Long composanteId, String idProjet, ProjetDto dto) {
        Composante comp = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante non trouvée: " + composanteId));
        Projet p = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé: " + idProjet));
        // verify li lye ak composante
        if (!p.getComposante().getId().equals(composanteId))
            throw new RuntimeException("Projet pa dans composante: " + composanteId);

        applyDtoToEntity(dto, p);
        p.setComposante(comp);
        p.setUpdatedAt(LocalDateTime.now());
        Projet saved = projetDao.save(p);
        return convertToDto(saved);
    }

    /** 2) Update component + quartier **/
    @Override
    public ProjetDto updateInComposanteAndQuartier(Long composanteId, String quartierId, String idProjet, ProjetDto dto) {
        Composante comp = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante non trouvée: " + composanteId));
        Quartier q = quartierDao.findById(Long.valueOf(quartierId))
                .orElseThrow(() -> new RuntimeException("Quartier non trouvé: " + quartierId));
        Projet p = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé: " + idProjet));

        Long qId = Long.valueOf(quartierId);
        if (!p.getComposante().getId().equals(composanteId)
                || !p.getQuartier().getId().equals(qId)) {
            throw new RuntimeException("Mismatch composante/quartier for Project: " + idProjet);
        }


        applyDtoToEntity(dto, p);
        p.setComposante(comp);
        p.setQuartier(q);
        p.setUpdatedAt(LocalDateTime.now());
        Projet saved = projetDao.save(p);
        return convertToDto(saved);
    }


    @Override
    public List<BeneficiaireDto> getBeneficiairesByProjetId(String idProjet) {
        Projet projet = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet pa jwenn"));

        List<Beneficiaire> beneficiaires = projet.getBeneficiaires();

        return beneficiaires.stream()
                .map(b -> {
                    BeneficiaireDto dto = new BeneficiaireDto();
                    dto.setIdBeneficiaire(b.getIdBeneficiaire());
                    dto.setNom(b.getNom());
                    dto.setPrenom(b.getPrenom());
                    dto.setSexe(b.getSexe());
                    dto.setDateNaissance(b.getDateNaissance());
                    dto.setDomaineDeFormation(b.getDomaineDeFormation());
                    dto.setTypeIdentification(b.getTypeIdentification());
                    dto.setIdentification(b.getIdentification());
                    dto.setLienNaissance(b.getLienNaissance());
                    dto.setQualification(b.getQualification());
                    dto.setTelephoneContact(b.getTelephoneContact());
                    dto.setTelephonePaiement(b.getTelephonePaiement());
                    dto.setOperateurPaiement(b.getOperateurPaiement());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public void setProjetActiveStatus(String idProjet, boolean active) {
        Projet p = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé: " + idProjet));
        p.setActive(active);
        p.setUpdatedAt(LocalDateTime.now());
        projetDao.save(p);
    }



    /** Helper pou copier chan ki modifye **/
    private void applyDtoToEntity(ProjetDto dto, Projet p) {
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setAddress(dto.getAddress());
        p.setDomaineDeFormation(dto.getDomaineDeFormation());
        p.setNumeroDePatente(dto.getNumeroDePatente());
        p.setNumeroDeReconnaissanceLegale(dto.getNumeroDeReconnaissanceLegale());
        p.setSourceDesNumeroDeReconnaissance(dto.getSourceDesNumeroDeReconnaissance());
        p.setRangDePriorisation(dto.getRangDePriorisation());
        p.setType(dto.getType());
        p.setStatut(dto.getStatut());
        p.setPhase(dto.getPhase());
        p.setCode(dto.getCode());
        p.setDateDebut(dto.getDateDebut());
        p.setDateFin(dto.getDateFin());
        p.setLatitude(dto.getLatitude());
        p.setLongitude(dto.getLongitude());
        p.setMontantAssurance(dto.getMontantAssurance());
        p.setMontantMateriaux(dto.getMontantMateriaux());
        p.setMontantMainOeuvreQualifier(dto.getMontantMainOeuvreQualifier());
        p.setMontantMainOeuvreNonQualifier(dto.getMontantMainOeuvreNonQualifier());
        p.setMontantTotal(dto.getMontantTotal());
        p.setModifyBy(dto.getModifyBy());
    }


    @Override
    public List<ProjetDto> findAll() {
        return projetDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ProjetDto> findById(String idProjet) {
        return projetDao.findById(idProjet).map(this::convertToDto);
    }

    @Override
    public void deleteById(String id) {
        projetDao.deleteById(id);
    }

    @Override
    public ProjetDto createProjetInQuartierAndComposante(ProjetDto dto, String quartierId, Long composanteId) {
        // 1) On récupère le Quartier
        Quartier q = quartierDao.findById(Long.valueOf(quartierId))
                .orElseThrow(() -> new RuntimeException("Quartier non trouvé: " + quartierId));
        // 2) On récupère la Composante
        Composante c = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante non trouvée: " + composanteId));

        // 3) On convertit le DTO en entity (sans y toucher aux relations)
        Projet p = convertToEntity(dto);

        // 4) On lie
        p.setQuartier(q);
        p.setComposante(c);

        // 5) Audit
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());

        // 6) Sauvegarde
        Projet saved = projetDao.save(p);

        // 7) Retour en DTO
        return convertToDto(saved);
    }

    @Override
    public List<ProjetDto> findByComposante(Long composanteId) {
        return projetDao
                .findByComposanteId(composanteId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjetDto> findByComposanteAndQuartier(Long composanteId, String quartierId) {
        return projetDao
                .findByComposanteIdAndQuartierId(composanteId, quartierId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Projet convertToEntity(ProjetDto dto) {
        Projet p = new Projet();
        p.setIdProjet(dto.getIdProjet());
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setAddress(dto.getAddress());
        p.setDomaineDeFormation(dto.getDomaineDeFormation());
        p.setNumeroDePatente(dto.getNumeroDePatente());
        p.setNumeroDeReconnaissanceLegale(dto.getNumeroDeReconnaissanceLegale());
        p.setSourceDesNumeroDeReconnaissance(dto.getSourceDesNumeroDeReconnaissance());
        p.setRangDePriorisation(dto.getRangDePriorisation());
        p.setType(dto.getType());
        p.setStatut(dto.getStatut());
        p.setPhase(dto.getPhase());
        p.setCode(dto.getCode());
        p.setDateDebut(dto.getDateDebut());
        p.setDateFin(dto.getDateFin());
        p.setLatitude(dto.getLatitude());
        p.setLongitude(dto.getLongitude());
        p.setMontantMainOeuvreQualifier(dto.getMontantMainOeuvreQualifier());
        p.setMontantMainOeuvreNonQualifier(dto.getMontantMainOeuvreNonQualifier());
        p.setMontantAssurance(dto.getMontantAssurance());
        p.setMontantMateriaux(dto.getMontantMateriaux());
        p.setMontantTotal(dto.getMontantTotal());
        p.setCreatedBy(dto.getCreatedBy());
        p.setModifyBy(dto.getModifyBy());
        p.setCreatedAt(dto.getCreatedAt());
        p.setUpdatedAt(dto.getUpdatedAt());

        // Nou pa mete composante ak quartier la la pou evite doublon, metòd ki rele yo mete yo
        return p;
    }

    private ProjetDto convertToDto(Projet p) {
        ProjetDto dto = new ProjetDto();
        dto.setIdProjet(p.getIdProjet());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setAddress(p.getAddress());
        dto.setDomaineDeFormation(p.getDomaineDeFormation());
        dto.setNumeroDePatente(p.getNumeroDePatente());
        dto.setNumeroDeReconnaissanceLegale(p.getNumeroDeReconnaissanceLegale());
        dto.setSourceDesNumeroDeReconnaissance(p.getSourceDesNumeroDeReconnaissance());
        dto.setRangDePriorisation(p.getRangDePriorisation());
        dto.setType(p.getType());
        dto.setStatut(p.getStatut());
        dto.setPhase(p.getPhase());
        dto.setCode(p.getCode());
        dto.setDateDebut(p.getDateDebut());
        dto.setDateFin(p.getDateFin());
        dto.setLatitude(p.getLatitude());
        dto.setLongitude(p.getLongitude());
        dto.setMontantMainOeuvreQualifier(p.getMontantMainOeuvreQualifier());
        dto.setMontantMainOeuvreNonQualifier(p.getMontantMainOeuvreNonQualifier());
        dto.setMontantAssurance(p.getMontantAssurance());
        dto.setMontantMateriaux(p.getMontantMateriaux());
        dto.setMontantTotal(p.getMontantTotal());
        dto.setCreatedBy(p.getCreatedBy());
        dto.setModifyBy(p.getModifyBy());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());
        dto.setComposanteId(p.getComposante() != null ? p.getComposante().getId() : null);
        dto.setQuartierId(p.getQuartier() != null ? p.getQuartier().getId() : null);
        return dto;
    }
}
