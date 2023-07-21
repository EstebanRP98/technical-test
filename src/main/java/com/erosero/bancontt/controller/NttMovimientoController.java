package com.erosero.bancontt.controller;


import com.erosero.bancontt.dto.NttMovimientoDto;
import com.erosero.bancontt.dto.ReporteMovimientoDto;
import com.erosero.bancontt.entity.NttMovimiento;
import com.erosero.bancontt.service.NttMovimientoService;
import com.erosero.bancontt.util.GenericResponse;
import com.erosero.bancontt.util.ParametersApp;
import com.erosero.bancontt.util.UtilsApi;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class NttMovimientoController {

    private final NttMovimientoService nttMovimientoService;

    public NttMovimientoController(NttMovimientoService nttMovimientoService) {
        this.nttMovimientoService = nttMovimientoService;
    }

    @GetMapping(value = "/encontrarMovimientoPorFecha")
    public ResponseEntity<GenericResponse> encontrarMovimientoPorFecha(@RequestParam("fechaInicial") @DateTimeFormat(pattern="dd-MM-yyyy") Date fechaInicial,
                                                                       @RequestParam("fechaFinal") @DateTimeFormat(pattern="dd-MM-yyyy") Date fechaFinal,
                                                                       @RequestParam("clienteId") Integer clienteId) {
        GenericResponse<List<ReporteMovimientoDto>> nttMovimientoGR = new GenericResponse<>();
        try {
            List<ReporteMovimientoDto> reporteMovimientoDtoList = nttMovimientoService.encontrarMovimientoPorFechas(fechaInicial, fechaFinal, clienteId);
            nttMovimientoGR.setObject(reporteMovimientoDtoList);
            nttMovimientoGR.setStatus(ParametersApp.SUCCESSFUL.value());
        } catch (Exception e) {
            nttMovimientoGR.setObject(null);
            nttMovimientoGR.setStatus(ParametersApp.PROCESS_NOT_COMPLETED.value());
            nttMovimientoGR.saveMessage(UtilsApi.getProcessExceptionMessage(e, 500));
        }
        return new ResponseEntity<>(nttMovimientoGR, HttpStatus.OK);
    }


    @PostMapping(value = "/crearMovimiento")
    public ResponseEntity<GenericResponse> crearMovimiento(@RequestBody NttMovimientoDto nttMovimiento) {
        GenericResponse<NttMovimiento> nttMovimientoGR = new GenericResponse<>();
        try {
            NttMovimiento movimiento = nttMovimientoService.guardarMovimiento(nttMovimiento);
            nttMovimientoGR.setObject(movimiento);
            nttMovimientoGR.setStatus(ParametersApp.SUCCESSFUL.value());
        } catch (Exception e) {
            nttMovimientoGR.setObject(null);
            nttMovimientoGR.setStatus(ParametersApp.PROCESS_NOT_COMPLETED.value());
            nttMovimientoGR.saveMessage(UtilsApi.getProcessExceptionMessage(e, 500));
        }
        return new ResponseEntity<>(nttMovimientoGR, HttpStatus.OK);
    }


}
