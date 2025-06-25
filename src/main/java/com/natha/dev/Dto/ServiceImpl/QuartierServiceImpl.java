package com.natha.dev.Dto.ServiceImpl;

import com.natha.dev.Dao.QuartierDao;
import com.natha.dev.Dao.SectionCommunaleDao;
import com.natha.dev.Dto.QuartierDto;
import com.natha.dev.IService.QuartierIService;
import com.natha.dev.Model.Quartier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartierServiceImpl implements QuartierIService {

    @Autowired
    private QuartierDao dao;

    @Autowired
    private SectionCommunaleDao sectionCommunaleDao;

    @Override
    public QuartierDto save(QuartierDto dto) {
        Quartier quartier = new Quartier();
        quartier.setName(dto.getName());
        quartier.setSectionCommunale(sectionCommunaleDao.findById(dto.getSectionCommunaleId()).orElse(null));
        return convertToDto(dao.save(quartier));
    }

    @Override
    public List<QuartierDto> getBySectionCommunale(Long sectionId) {
        return dao.findBySectionCommunaleId(sectionId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<QuartierDto> getAll() {
        return dao.findAll().stream().map(this::convertToDto).toList();
    }

    private QuartierDto convertToDto(Quartier quartier) {
        return new QuartierDto(quartier.getId(), quartier.getName(), quartier.getSectionCommunale().getId());
    }
}

