package dev.jaidson.geestacar.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RevenueDTO {
    private double amount;
    private String currency;
    private LocalDate date;
}
