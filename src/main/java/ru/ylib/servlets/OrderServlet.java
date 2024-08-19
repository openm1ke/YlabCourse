package ru.ylib.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylib.dto.OrderDTO;
import ru.ylib.services.OrderService;
import ru.ylib.utils.DatabaseConnection;

import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.orderService = new OrderService(dbConnection);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDTO orderDTO = objectMapper.readValue(req.getInputStream(), OrderDTO.class);
        OrderDTO createdOrder = orderService.create(orderDTO);
        if (createdOrder != null) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), createdOrder);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // Get all orders
            List<OrderDTO> orders = orderService.readAll();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), orders);
        } else if (pathInfo.startsWith("/car/")) {
            // Get order by car ID
            long carId = Long.parseLong(pathInfo.substring(5));
            OrderDTO orderDTO = orderService.readByCarId(carId);
            if (orderDTO != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), orderDTO);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // Get order by ID
            long id = Long.parseLong(pathInfo.substring(1));
            OrderDTO orderDTO = orderService.read(id);
            if (orderDTO != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), orderDTO);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getPathInfo().substring(1));
        OrderDTO orderDTO = objectMapper.readValue(req.getInputStream(), OrderDTO.class);
        orderDTO.setId(id);
        OrderDTO updatedOrder = orderService.update(orderDTO);
        if (updatedOrder != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), updatedOrder);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getPathInfo().substring(1));
        orderService.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
