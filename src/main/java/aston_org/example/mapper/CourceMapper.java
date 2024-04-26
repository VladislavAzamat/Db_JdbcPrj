package aston_org.example.mapper;

import aston_org.example.dto.CourceDto;
import aston_org.example.entity.Cource;
import aston_org.example.service.StudentService;



public class CourceMapper {
    public static CourceDto courceToDto(Cource cource) {
        if (cource == null) {
            throw new NullPointerException("Ошибка маппера при создании CourceDto! Cource == null!");
        }
        CourceDto courceDto = new CourceDto();
        courceDto.setTitle(cource.getTitle());
        courceDto.setDuration(cource.getDuration());
        courceDto.setPrice(cource.getPrice());
        courceDto.setStudentDtoList(StudentService.returnListForCourceOrCredential(cource.getId()));
        return courceDto;
    }


    public static Cource courceDtoToEntity(CourceDto courceDto) {
        if (courceDto == null) {
            throw new NullPointerException("Ошибка маппера при создании Cource! CourceDto == null!");
        }
        Cource cource = new Cource();
        cource.setTitle(courceDto.getTitle());
        cource.setDuration(courceDto.getDuration());
        cource.setPrice(courceDto.getPrice());
        return cource;
    }
}
