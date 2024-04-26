package aston_org.example.service;
import aston_org.example.dto.CourceDto;
import aston_org.example.entity.Cource;
import aston_org.example.mapper.CourceMapper;
import aston_org.example.repository.CourceRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CourceService {
    private  CourceRepository courceRepository;
    private  CourceMapper courceMapper;


    public CourceService() {
        this.courceRepository = new CourceRepository();
        this.courceMapper = new CourceMapper();
    }
    public CourceService(CourceRepository courceRepository) {
    }

    public List<CourceDto> getAllCources() {
        List<Cource> cources = courceRepository.getAllCources();
        return cources.stream()
                .map(CourceMapper::courceToDto)
                .collect(Collectors.toList());
    }

    public CourceDto getCourceById(int courceId) {
        Cource cource = courceRepository.getCourceById(courceId);
        return CourceMapper.courceToDto(cource);
    }

    public long addCource(CourceDto courceDto){
        Cource cource = CourceMapper.courceDtoToEntity(courceDto);
        return courceRepository.addCource(cource);
    }

    public void updateCource(int courceId, CourceDto updatedCourceDto)  {
        Cource updatedCource = CourceMapper.courceDtoToEntity(updatedCourceDto);
        courceRepository.updateCource(courceId, updatedCource);
    }

    public void deleteCource(int courceId)  {
        courceRepository.deleteCource(courceId);
    }
}
