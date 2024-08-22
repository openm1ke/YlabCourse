package ru.ylib.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {
    private long id;
    @NotNull
    private long userId;
    @NotNull
    private long carId;
    @NotNull
    private String orderDate;
    private String status; // Например, "PENDING", "COMPLETED" и т.д.
}
