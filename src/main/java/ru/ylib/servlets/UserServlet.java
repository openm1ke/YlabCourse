package ru.ylib.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.ylib.dto.UserDTO;
import ru.ylib.services.UserService;

import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {

    @Autowired
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
//        userService = context.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        UserDTO createdUser = userService.create(userDTO);
        if (createdUser != null) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), createdUser);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Если нет ID в запросе, возвращаем всех пользователей
        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            List<UserDTO> users = userService.readAll();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), users);
        } else {
            // Если ID есть, возвращаем конкретного пользователя
            try {
                long id = Long.parseLong(req.getPathInfo().substring(1));
                UserDTO userDTO = userService.read(id);
                if (userDTO != null) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType("application/json");
                    objectMapper.writeValue(resp.getOutputStream(), userDTO);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid user ID format");
            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getPathInfo().substring(1));
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        userDTO.setId(id);
        UserDTO updatedUser = userService.update(userDTO);
        if (updatedUser != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), updatedUser);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getPathInfo().substring(1));
        userService.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
