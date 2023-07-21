package com.erosero.bancontt.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NttMovimientoDto {

    private BigDecimal movValor;
    private Integer movCuenId;
    private Integer movTipoMovimiento;

}
