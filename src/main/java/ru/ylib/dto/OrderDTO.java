package ru.ylib.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private long id;
    private long userId;
    private long carId;
    private String orderDate;
    private String status; // Например, "PENDING", "COMPLETED" и т.д.
}
