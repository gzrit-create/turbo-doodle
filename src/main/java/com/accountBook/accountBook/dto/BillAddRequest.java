package com.accountBook.accountBook.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BillAddRequest {
    private Long categoryId;
    private BigDecimal amount;//这个作用也不明确
    private String note;
    private LocalDate billDate;
    private Integer type;

}
