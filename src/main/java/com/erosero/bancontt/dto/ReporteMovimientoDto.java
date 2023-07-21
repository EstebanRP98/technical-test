package com.erosero.bancontt.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReporteMovimientoDto {

    private Date fechaMovimiento;
    private String cliente;
    private String numeroCuenta;
    private String tipoMovimiento;
    private BigDecimal saldoInicial;
    private BigDecimal movimiento;
    private BigDecimal saldo;
    private Boolean estadoCuenta;
}
