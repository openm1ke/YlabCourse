package ru.ylib.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylib.dto.UserDTO;
import ru.ylib.services.UserService;
import ru.ylib.utils.DatabaseConnection;

import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.userService = new UserService(dbConnection);
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
        long id = Long.parseLong(req.getPathInfo().substring(1));
        UserDTO userDTO = userService.read(id);
        if (userDTO != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), userDTO);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
