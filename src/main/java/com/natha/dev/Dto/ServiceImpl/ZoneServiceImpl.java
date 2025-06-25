package com.natha.dev.Dto.ServiceImpl;

import com.natha.dev.Dao.ComposanteDao;
import com.natha.dev.Dao.ZoneDao;
import com.natha.dev.Dto.ZoneDto;
import com.natha.dev.IService.ZoneIService;
import com.natha.dev.Model.Composante;
import com.natha.dev.Model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneIService {

    @Autowired
    private ZoneDao zoneDao;

    @Autowired
    private ComposanteDao composanteDao;

    @Override
    public ZoneDto save(ZoneDto dto) {
        Composante composante = composanteDao.findById(dto.getComposanteId())
                .orElseThrow(() -> new RuntimeException("Composante pa jwenn"));

        Zone zone = new Zone();
        zone.setNom(dto.getNom());
        zone.setComposante(composante);

        return convertToDto(zoneDao.save(zone));
    }

    @Override
    public List<ZoneDto> getAll() {
        return zoneDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ZoneDto> getByComposanteId(Long composanteId) {
        return zoneDao.findByComposanteId(composanteId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        zoneDao.deleteById(id);
    }

    private ZoneDto convertToDto(Zone zone) {
        return new ZoneDto(zone.getId(), zone.getNom(), zone.getComposante().getId());
    }
}
