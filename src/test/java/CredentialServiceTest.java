
import aston_org.example.dto.CredentialDto;
import aston_org.example.entity.Credential;
import aston_org.example.entity.Student;
import aston_org.example.mapper.CredentialMapper;
import aston_org.example.repository.CredentialRepository;
import aston_org.example.repository.StudentRepository;
import aston_org.example.service.CredentialService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Хороший код, молодец
class CredentialServiceTest {

    private CredentialService credentialService;
    private CredentialRepository credentialRepository;
    private StudentRepository studentRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        credentialRepository = new CredentialRepository(connection);
        studentRepository = new StudentRepository(connection);
        credentialService = new CredentialService(credentialRepository, studentRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void getAllCredentials() throws SQLException {
        Student student = new Student("John", "Doe");
        studentRepository.addStudent(student);

        Credential credential1 = new Credential("john.doe@example.com", "1234567890", student);
        Credential credential2 = new Credential("jane.doe@example.com", "0987654321", student);
        credentialRepository.addCredential(credential1);
        credentialRepository.addCredential(credential2);

        List<CredentialDto> credentials = credentialService.getAllCredentials();

        assertEquals(2, credentials.size());
        assertEquals("john.doe@example.com", credentials.get(0).getEmail());
        assertEquals("1234567890", credentials.get(0).getPhoneNumber());
        assertEquals("John", credentials.get(0).getStudentDto().getFirstName());
        assertEquals("Doe", credentials.get(0).getStudentDto().getLastName());
        assertEquals("jane.doe@example.com", credentials.get(1).getEmail());
        assertEquals("0987654321", credentials.get(1).getPhoneNumber());
        assertEquals("John", credentials.get(1).getStudentDto().getFirstName());
        assertEquals("Doe", credentials.get(1).getStudentDto().getLastName());
    }

    @Test
    void getCredentialById() throws SQLException {
        Student student = new Student("John", "Doe");
        studentRepository.addStudent(student);

        Credential credential = new Credential("john.doe@example.com", "1234567890", student);
        credentialRepository.addCredential(credential);

        CredentialDto credentialDto = credentialService.getCredentialById(1);

        assertEquals("john.doe@example.com", credentialDto.getEmail());
        assertEquals("1234567890", credentialDto.getPhoneNumber());
        assertEquals("John", credentialDto.getStudentDto().getFirstName());
        assertEquals("Doe", credentialDto.getStudentDto().getLastName());
    }

    @Test
    void addCredential() throws SQLException {
        Student student = new Student("John", "Doe");
        studentRepository.addStudent(student);

        CredentialDto credentialDto = new CredentialDto("john.doe@example.com", "1234567890", student.getId());

        int credentialId = credentialService.addCredential(credentialDto);

        CredentialDto addedCredential = credentialService.getCredentialById(credentialId);
        assertEquals("john.doe@example.com", addedCredential.getEmail());
        assertEquals("1234567890", addedCredential.getPhoneNumber());
        assertEquals("John", addedCredential.getStudentDto().getFirstName());
        assertEquals("Doe", addedCredential.getStudentDto().getLastName());
    }

    @Test
    void updateCredential() throws SQLException {
        Student student = new Student("John", "Doe");
        studentRepository.addStudent(student);

        Credential credential = new Credential("john.doe@example.com", "1234567890", student);
        credentialRepository.addCredential(credential);

        CredentialDto updatedCredentialDto = new CredentialDto("jane.doe@example.com", "0987654321", student.getId());

        credentialService.updateCredential(1, updatedCredentialDto);

        CredentialDto updatedCredential = credentialService.getCredentialById(1);
        assertEquals("jane.doe@example.com", updatedCredential.getEmail());
        assertEquals("0987654321", updatedCredential.getPhoneNumber());
        assertEquals("John", updatedCredential.getStudentDto().getFirstName());
        assertEquals("Doe", updatedCredential.getStudentDto().getLastName());
    }

    @Test
    void deleteCredential() throws SQLException {
        Student student = new Student("John", "Doe");
        studentRepository.addStudent(student);

        Credential credential = new Credential("john.doe@example.com", "1234567890", student);
        credentialRepository.addCredential(credential);

        credentialService.deleteCredential(1);

        List<CredentialDto> credentials = credentialService.getAllCredentials();
        assertEquals(0, credentials.size());
    }
}