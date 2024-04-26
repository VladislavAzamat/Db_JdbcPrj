package aston_org.example.servlet;
import aston_org.example.dto.StudentDto;
import aston_org.example.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.List;

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {
    private StudentService studentService = new StudentService();
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void init() throws ServletException {
        super.init();
    }
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        response.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<StudentDto> studentDtoList = studentService.getAllStudents();
            String json = objectMapper.writeValueAsString(studentDtoList);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            String studentId = pathInfo.substring(1);
            StudentDto studentDto = studentService.getStudentById(Integer.parseInt(studentId));
            if (studentDto != null) {
                String jsonResponce = objectMapper.writeValueAsString(studentDto);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponce);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        StudentDto newStudent = objectMapper.readValue(request.getReader(), StudentDto.class);
        Integer newStudentId = studentService.addStudent(newStudent);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("text/plain");
        PrintWriter messageToClient = response.getWriter();
        messageToClient.printf("Студент с айДи %d успешно добавлен!", newStudentId);
        messageToClient.close();
    }

    @SneakyThrows
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String studentId = pathInfo.substring(1);
            StudentDto studentDtoToUpdt = objectMapper.readValue(request.getReader(), StudentDto.class);
            studentService.updateStudent(Integer.parseInt(studentId),studentDtoToUpdt);

            if (studentDtoToUpdt != null) {
                response.setContentType("text/plain");
                PrintWriter messageToClient = response.getWriter();
                messageToClient.printf("Данные студента с айДи %s изменены!", studentId);
                messageToClient.close();
                String jsonResponse = objectMapper.writeValueAsString(studentDtoToUpdt);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to update student");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @SneakyThrows
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String studentId = pathInfo.substring(1);

            studentService.deleteStudent(Integer.parseInt(studentId));

            response.setStatus(HttpServletResponse.SC_GONE);
            response.setContentType("text/plain");
            PrintWriter messageToClient = response.getWriter();
            messageToClient.printf("Студент с айДи %s успешно удален!", studentId);
            messageToClient.close();
        }
    }

    
}
