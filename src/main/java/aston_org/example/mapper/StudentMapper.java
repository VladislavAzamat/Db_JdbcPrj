package aston_org.example.mapper;
import aston_org.example.dto.StudentDto;
import aston_org.example.entity.Student;

public class StudentMapper {

    public static StudentDto studentToDto(Student student) {
        if (student == null) {
            throw new NullPointerException("Ошибка маппера при создании StudentDto! Student == null!");
        }
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setCourseId(student.getCourceId());
        return studentDto;
    }

    public static Student studentDtoToEntity(StudentDto studentDto) {
        if (studentDto == null) {
            throw new NullPointerException("Ошибка маппера при создании Student! StudentDto == null!");
        }
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setCourceId(studentDto.getCourseId());
        return student;
    }
}
