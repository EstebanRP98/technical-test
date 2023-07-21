package com.erosero.bancontt.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NttClienteDto {

    private Integer persId;
    private String persNombre;
    private String persGenero;
    private String persIdentificacion;
    private Date persFechaNacimiento;
    private String persDireccion;
    private String persTelefono;
    private String cliPassword;
    private boolean cliEstado;
}
