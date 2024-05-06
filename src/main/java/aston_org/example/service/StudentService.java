package aston_org.example.service;

import aston_org.example.dto.StudentDto;
import aston_org.example.entity.Student;
import aston_org.example.mapper.StudentMapper;
import aston_org.example.repository.StudentRepository;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private StudentRepository repository = new StudentRepository();

    public StudentService() {

    }

    public StudentService(StudentRepository studentRepository) {
        this.repository = studentRepository;
    }

    public List<StudentDto> getAllStudents() throws SQLException {

        List<Student> students = repository.getAllStudents();

        for (Student student : students) {
            System.out.println(student);
        }

        return students.stream()
                .map(StudentMapper::studentToDto)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public StudentDto getStudentById(int studentId) {
        Student student = repository.getStudentById(studentId);
        return StudentMapper.studentToDto(student);
    }


    @SneakyThrows
    public int addStudent(StudentDto studentDto) {
        Student student = StudentMapper.studentDtoToEntity(studentDto);
        return repository.addStudent(student);
    }

    @SneakyThrows
    public void updateStudent(int studentId, StudentDto studentDto) {
        if (!repository.courseExists(studentDto.getCourseId())) {
            throw new RuntimeException("Курс с указанным идентификатором не существует");
        }
        Student student = StudentMapper.studentDtoToEntity(studentDto); // раз уж сделал статические методы то лучше так, но вообще методы не должны быть статическими, это же не утилитарные методы
        student.setId(studentId);
        repository.updateStudent(studentId, student);
    }

    public void deleteStudent(int studentId) throws SQLException {
        repository.deleteStudent(studentId);
    }

    public static List<StudentDto> returnListForCourceOrCredential(int courceId) {
        List<Student> studentList = StudentRepository.getStudentsByCourseId(courceId);
        return studentList.stream()
                .map(StudentMapper::studentToDto)
                .collect(Collectors.toList());
    }

    public static StudentDto getStudent(int studentId) throws SQLException {
        Student student = StudentRepository.getStudent(studentId);
        return StudentMapper.studentToDto(student);
    }
}
