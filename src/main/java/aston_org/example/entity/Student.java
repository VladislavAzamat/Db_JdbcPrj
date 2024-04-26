package aston_org.example.entity;
import java.util.Objects;

public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer courceId;


    public Student() {
    }

    public Student(int id, String firstName, String lastName, Integer courceId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courceId = courceId;
    }
    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student {" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cource="  +  '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCourceId(Integer courceId) {
        this.courceId = courceId;
    }

    public Integer getCourceId() {
        return courceId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
