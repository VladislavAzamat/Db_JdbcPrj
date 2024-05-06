
import aston_org.example.dto.StudentDto;
import aston_org.example.entity.Student;
import aston_org.example.mapper.StudentMapper;
import aston_org.example.repository.StudentRepository;
import aston_org.example.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void testGetAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "John", "Doe", 1));
        students.add(new Student(2, "Jane", "Doe", 2));
        when(studentRepository.getAllStudents()).thenReturn(students);

        List<StudentDto> studentDtos = studentService.getAllStudents();

        assertEquals(2, studentDtos.size());
        assertEquals("John", studentDtos.get(0).getFirstName());
        assertEquals("Doe", studentDtos.get(0).getLastName());
        assertEquals(1, studentDtos.get(0).getCourseId());
        assertEquals("Jane", studentDtos.get(1).getFirstName());
        assertEquals("Doe", studentDtos.get(1).getLastName());
        assertEquals(2, studentDtos.get(1).getCourseId());
    }

    @Test
    public void testGetStudentById() throws SQLException {
        Student student = new Student(1, "John", "Doe", 1);
        when(studentRepository.getStudentById(1)).thenReturn(student);

        StudentDto studentDto = studentService.getStudentById(1);

        assertEquals("John", studentDto.getFirstName());
        assertEquals("Doe", studentDto.getLastName());
        assertEquals(1, studentDto.getCourseId());
    }

    @Test
    public void testAddStudent() throws SQLException {
        StudentDto studentDto = new StudentDto("John", "Doe", 1);
        when(studentRepository.addStudent(any(Student.class))).thenReturn(1);

        int studentId = studentService.addStudent(studentDto);

        assertEquals(1, studentId);
        verify(studentRepository, times(1)).addStudent(any(Student.class));
    }

    @Test
    public void testUpdateStudent() throws SQLException {
        StudentDto studentDto = new StudentDto("John", "Doe", 1);
        studentService.updateStudent(1, studentDto);

        verify(studentRepository, times(1)).updateStudent(eq(1), any(Student.class));
    }

    @Test
    public void testDeleteStudent() throws SQLException {
        studentService.deleteStudent(1);
        verify(studentRepository, times(1)).deleteStudent(eq(1));
    }
}