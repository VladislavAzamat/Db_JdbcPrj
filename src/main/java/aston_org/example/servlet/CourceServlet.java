package aston_org.example.servlet;
import aston_org.example.dto.CourceDto;
import aston_org.example.service.CourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cources/*")
public class CourceServlet extends HttpServlet {
    private CourceService courceService = new CourceService();
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void init() throws ServletException {
        super.init();

    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<CourceDto> courceList = courceService.getAllCources();
            String jsonResponse = objectMapper.writeValueAsString(courceList);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        } else {
            String courceId = pathInfo.substring(1);
            CourceDto requestedCource = courceService.getCourceById(Integer.parseInt(courceId));
            if (courceId != null) {
                String jsonResponse = objectMapper.writeValueAsString(requestedCource);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }
    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        CourceDto newCource = objectMapper.readValue(request.getReader(), CourceDto.class);
        courceService.addCource(newCource);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("text/plain");
        PrintWriter messageToClient = response.getWriter();
        messageToClient.printf("Учебный курс по названию %s успешно добавлен!", newCource.getTitle());
        messageToClient.close();
    }

    @SneakyThrows
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String courceId = pathInfo.substring(1);
            CourceDto updatedCource = objectMapper.readValue(request.getReader(), CourceDto.class);
            courceService.updateCource(Integer.parseInt(courceId),updatedCource);
            if (updatedCource != null) {
                response.setContentType("text/plain");
                PrintWriter messageToClient = response.getWriter();
                messageToClient.printf("Данные учебного курса по названию %s изменены!", updatedCource.getTitle());
                messageToClient.close();
                String jsonResponse = objectMapper.writeValueAsString(updatedCource);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cource not found");
            }
        }
    }

    @SneakyThrows
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String courceId = pathInfo.substring(1);
            CourceDto courceToDelete = courceService.getCourceById(Integer.parseInt(courceId));

            courceService.deleteCource(Integer.parseInt(courceId));

            response.setStatus(HttpServletResponse.SC_GONE);
            response.setContentType("text/plain");
            PrintWriter messageToClient = response.getWriter();
            messageToClient.printf("Курс с названием %s успешно удален!", courceToDelete.getTitle());
            messageToClient.close();
        }
    }
}
