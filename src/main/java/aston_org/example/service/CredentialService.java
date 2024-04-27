package aston_org.example.service;


import aston_org.example.dto.CredentialDto;
import aston_org.example.dto.StudentDto;
import aston_org.example.entity.Credential;
import aston_org.example.mapper.CredentialMapper;
import aston_org.example.repository.CredentialRepository;
import aston_org.example.repository.StudentRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CredentialService {

    private CredentialRepository repository;
    private CredentialMapper mapper;
    private StudentRepository studentRepository;

    public CredentialService() {
        this.repository = new CredentialRepository();
        this.mapper = new CredentialMapper();
    }

    public CredentialService(CredentialRepository credentialRepository, StudentRepository studentRepository) {
        this.repository = credentialRepository;
        this.studentRepository = studentRepository;
    }

    public List<CredentialDto> getAllCredentials() throws SQLException {
        List<Credential> credentials = repository.getAllCredentials();
        return credentials.stream()
                .map(CredentialMapper::credentialToDto)
                .collect(Collectors.toList());
    }

    public CredentialDto getCredentialById(int credentialId) throws SQLException {
        Credential credential = repository.getCredentialById(credentialId);
        return mapper.credentialToDto(credential);
    }

    public int addCredential(CredentialDto credentialDto) throws SQLException {
        Credential credential = mapper.credentialDtoToEntity(credentialDto);
        return repository.addCredential(credential);
    }

    public void updateCredential(int credentialId, CredentialDto credentialDto) throws SQLException {
        Credential credential = mapper.credentialDtoToEntity(credentialDto);
        credential.setId(credentialId);
        repository.updateCredential(credentialId, credential);
    }

    public void deleteCredential(int credentialId) throws SQLException {
        repository.deleteCredential(credentialId);
    }

}
