package aston_org.example.entity;
import java.util.Objects;

public class Credential {
    private Integer id;
    private String email;
    private String phoneNumber;
    private Student student;

    public Credential(Integer id, String email, String phoneNumber, Student student) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.student = student;
    }

    public Credential() {
    }

    public Credential(String email, String phoneNumber, Student student) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return id == that.id && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber);
    }
    public Integer getId() { return id; }

    public String getEmail() {
        return email;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
