package aston_org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDto {

    private String email;
    private String phoneNumber;
    private Integer studentId;
    private StudentDto studentDto;

    public CredentialDto(String email, String phoneNumber, Integer studentId) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
    }
}
