package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.DepartementDao;
import com.natha.dev.Dao.ZoneDao;
import com.natha.dev.Dto.DepartementDto;
import com.natha.dev.IService.DepartementIService;
import com.natha.dev.Model.Departement;
import com.natha.dev.Model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementServiceImpl implements DepartementIService {

    @Autowired
    private DepartementDao dao;

    @Autowired
    private ZoneDao zoneDao;

    @Override
    public DepartementDto save(DepartementDto dto) {
        Departement d = new Departement();
        d.setName(dto.getName());

        Zone zone = zoneDao.findById(dto.getZoneId()).orElse(null);

        if (zone != null) {
            d.setZones(List.of(zone));  // ✅ Chanje Set -> List
        }

        return convertToDto(dao.save(d));
    }


    @Override
    public List<DepartementDto> getByZone(Long zoneId) {
        return dao.findByZoneId(zoneId).stream().map(this::convertToDto).toList();
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<DepartementDto> getAll() {
        return dao.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }


    @Override
    public DepartementDto convertToDto(Departement d) {
        Long zoneId = null;
        if (d.getZones() != null && !d.getZones().isEmpty()) {
            zoneId = d.getZones().iterator().next().getId(); // pran premye zone a
        }
        return new DepartementDto(d.getId(), d.getName(), zoneId);
    }

}

