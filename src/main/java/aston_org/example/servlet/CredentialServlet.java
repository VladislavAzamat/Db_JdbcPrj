package aston_org.example.servlet;

import aston_org.example.dto.CredentialDto;
import aston_org.example.service.CredentialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/credentials/*")
public class CredentialServlet extends HttpServlet {
    private CredentialService credentialService = new CredentialService();
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
            List<CredentialDto> credentialDtoList = credentialService.getAllCredentials();
            String json = objectMapper.writeValueAsString(credentialDtoList);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            String credentialId = pathInfo.substring(1);
            CredentialDto credentialDto = credentialService.getCredentialById(Integer.parseInt(credentialId));
            if (credentialDto != null) {
                String jsonResponce = objectMapper.writeValueAsString(credentialDto);
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

        CredentialDto newCredential = objectMapper.readValue(request.getReader(), CredentialDto.class);
        Integer newCredentialId = credentialService.addCredential(newCredential);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("text/plain");
        PrintWriter messageToClient = response.getWriter();
        messageToClient.printf("Учетные данные с айДи %d успешно добавлены!", newCredentialId);
        messageToClient.close();
    }

    @SneakyThrows
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String credentialId = pathInfo.substring(1);
            CredentialDto credentialDtoToUpdt = objectMapper.readValue(request.getReader(), CredentialDto.class);
            credentialService.updateCredential(Integer.parseInt(credentialId), credentialDtoToUpdt);

            if (credentialDtoToUpdt != null) {
                response.setContentType("text/plain");
                PrintWriter messageToClient = response.getWriter();
                messageToClient.printf("Учетные данные с айДи %s изменены!", credentialId);
                messageToClient.close();
                String jsonResponse = objectMapper.writeValueAsString(credentialDtoToUpdt);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to update credential");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @SneakyThrows
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        response.setCharacterEncoding("UTF-8");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String credentialId = pathInfo.substring(1);

            credentialService.deleteCredential(Integer.parseInt(credentialId));

            response.setStatus(HttpServletResponse.SC_GONE);
            response.setContentType("text/plain");
            PrintWriter messageToClient = response.getWriter();
            messageToClient.printf("Учетные данные с айДи %s успешно удалены!", credentialId);
            messageToClient.close();
        }
    }


}


