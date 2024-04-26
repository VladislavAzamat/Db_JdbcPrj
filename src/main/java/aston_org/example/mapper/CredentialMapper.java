package aston_org.example.mapper;

import aston_org.example.dto.CredentialDto;
import aston_org.example.entity.Credential;
import aston_org.example.entity.Student;
import aston_org.example.service.StudentService;
import lombok.SneakyThrows;

public class CredentialMapper {

    @SneakyThrows
    public static CredentialDto credentialToDto(Credential credential) {
        if (credential == null) {
            throw new NullPointerException("Ошибка маппера при создании CredentialDto! Credential == null!");
        }
        CredentialDto credentialDto = new CredentialDto();
        credentialDto.setEmail(credential.getEmail());
        credentialDto.setPhoneNumber(credential.getPhoneNumber());
        credentialDto.setStudentId(credential.getStudent().getId());
        credentialDto.setStudentDto(StudentService.getStudent(credential.getStudent().getId()));
        return credentialDto;
    }

    @SneakyThrows
    public static Credential credentialDtoToEntity(CredentialDto credentialDto) {
        if (credentialDto == null) {
            throw new NullPointerException("Ошибка маппера при создании Credential! CredentialDto == null!");
        }
        Credential credential = new Credential();
        credential.setEmail(credentialDto.getEmail());
        credential.setPhoneNumber(credentialDto.getPhoneNumber());
        credential.setStudent(new Student(credentialDto.getStudentId()));
        return credential;
    }
}
