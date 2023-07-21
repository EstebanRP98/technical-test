package com.erosero.bancontt.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NttCuentaDto {

    private Integer cuenId;
    private String cuenNumero;
    private BigDecimal cuenSaldoInicial;
    private boolean cuenEstado;
    private Integer cuenCliId;
    private Integer cuenTipoCuenta;

}
