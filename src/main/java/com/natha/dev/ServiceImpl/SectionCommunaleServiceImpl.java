package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.CommuneDao;
import com.natha.dev.Dao.SectionCommunaleDao;
import com.natha.dev.Dto.SectionCommunaleDto;
import com.natha.dev.IService.SectionCommunaleIService;
import com.natha.dev.Model.SectionCommunale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionCommunaleServiceImpl implements SectionCommunaleIService {

    @Autowired
    private SectionCommunaleDao dao;

    @Autowired
    private CommuneDao communeDao;

    @Override
    public SectionCommunaleDto save(SectionCommunaleDto dto) {
        SectionCommunale section = new SectionCommunale();
        section.setName(dto.getName());
        section.setCommune(communeDao.findById(dto.getCommuneId()).orElse(null));
        return convertToDto(dao.save(section));
    }

    @Override
    public List<SectionCommunaleDto> getByCommune(Long communeId) {
        return dao.findByCommuneId(communeId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<SectionCommunaleDto> getAll() {
        return dao.findAll().stream().map(this::convertToDto).toList();
    }

    private SectionCommunaleDto convertToDto(SectionCommunale section) {
        return new SectionCommunaleDto(section.getId(), section.getName(), section.getCommune().getId());
    }
}

