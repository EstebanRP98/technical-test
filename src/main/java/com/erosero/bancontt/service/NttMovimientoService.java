package com.erosero.bancontt.service;

import com.erosero.bancontt.dto.NttMovimientoDto;
import com.erosero.bancontt.dto.ReporteMovimientoDto;
import com.erosero.bancontt.entity.NttMovimiento;
import com.erosero.bancontt.entity.NttTipoMovimiento;

import java.util.Date;
import java.util.List;

public interface NttMovimientoService {

    NttTipoMovimiento encontrarTipoMovimientoPorId(Integer tipoMovimientoId) throws Exception;

    List<ReporteMovimientoDto> encontrarMovimientoPorFechas(Date fechaInicial, Date fechaFinal, Integer clienteId) throws Exception;

    NttMovimiento guardarMovimiento(NttMovimientoDto nttMovimiento);

}
