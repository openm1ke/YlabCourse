package ru.ylib.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {
    private long id;
    private String status;
    @NotNull
    private long carId;
    @NotNull
    private long userId;
    private String type;
    @NotNull
    private String orderDate;

}