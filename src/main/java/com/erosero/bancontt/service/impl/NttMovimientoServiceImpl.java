package com.erosero.bancontt.service.impl;

import com.erosero.bancontt.dto.NttCuentaDto;
import com.erosero.bancontt.dto.NttMovimientoDto;
import com.erosero.bancontt.dto.ReporteMovimientoDto;
import com.erosero.bancontt.entity.NttCuenta;
import com.erosero.bancontt.entity.NttMovimiento;
import com.erosero.bancontt.entity.NttTipoMovimiento;
import com.erosero.bancontt.repository.NttMovimientoRepository;
import com.erosero.bancontt.repository.NttTipoMovimientoRepository;
import com.erosero.bancontt.service.NttMovimientoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NttMovimientoServiceImpl implements NttMovimientoService {

    @Autowired
    NttMovimientoRepository nttMovimientoRepository;
    @Autowired
    NttCuentaServiceImpl nttCuentaServiceImpl;
    @Autowired
    NttTipoMovimientoRepository nttTipoMovimientoRepository;

    public NttTipoMovimiento encontrarTipoMovimientoPorId(Integer tipoMovimientoId) throws Exception {
        log.info("Find type of movement");
        Optional<NttTipoMovimiento> nttTipoMovimiento = nttTipoMovimientoRepository.findById(tipoMovimientoId);
        if (!nttTipoMovimiento.isPresent()) {
            log.error("Type of movement not found");
            throw new Exception("Ingrese un Tipo de Movimiento Válido");
        }
        return nttTipoMovimiento.get();
    }

    public List<ReporteMovimientoDto> encontrarMovimientoPorFechas(Date fechaInicial, Date fechaFinal, Integer clienteId) throws Exception {
        log.info("find transaction by date");
        List<NttMovimiento> movimientoList = nttMovimientoRepository.encontrarMovimientosPorFechas(fechaInicial, fechaFinal, clienteId);
        List<ReporteMovimientoDto> reporteMovimientoDtoList = new ArrayList<>();
        for (NttMovimiento nttMovimiento: movimientoList) {
            ReporteMovimientoDto reporteMovimientoDto = new ReporteMovimientoDto();
            reporteMovimientoDto.setMovimiento(nttMovimiento.getMovValor());
            reporteMovimientoDto.setFechaMovimiento(nttMovimiento.getMovFecha());
            switch (nttMovimiento.getMovTipoMovimiento().getTpmId()){
                //retiro
                case 1:
                    reporteMovimientoDto.setTipoMovimiento(nttMovimiento.getMovTipoMovimiento().getTpmDescripcion()+": -"+nttMovimiento.getMovValor().doubleValue());
                    break;
                //deposito
                case 2:
                    reporteMovimientoDto.setTipoMovimiento(nttMovimiento.getMovTipoMovimiento().getTpmDescripcion()+": +"+nttMovimiento.getMovValor().doubleValue());
                    break;
            }
            reporteMovimientoDto.setNumeroCuenta(nttMovimiento.getMovCuenId().getCuenNumero());
            reporteMovimientoDto.setCliente(nttMovimiento.getMovCuenId().getCuenCliId().getPersNombre());
            reporteMovimientoDto.setSaldoInicial(nttMovimiento.getMovSaldoInicial());
            reporteMovimientoDto.setSaldo(nttMovimiento.getMovSaldo());
            reporteMovimientoDto.setEstadoCuenta(nttMovimiento.getMovCuenId().isCuenEstado());
            reporteMovimientoDtoList.add(reporteMovimientoDto);
        }
        return reporteMovimientoDtoList;
    }

    @Transactional
    public NttMovimiento guardarMovimiento(NttMovimientoDto nttMovimiento) throws Exception {
        log.info("Save movement");
        NttMovimiento nttMovimientoGuardar = new NttMovimiento();
        if (nttMovimiento.getMovCuenId() == null ) {
            log.error("Enter a Valid Account");
            throw new Exception("Ingrese una Cuenta Válida");
        }
        NttCuenta nttCuenta = nttCuentaServiceImpl.encontrarCuentaPorId(nttMovimiento.getMovCuenId());
        if (!nttCuenta.isCuenEstado()) {
            log.error("Account is Deactivated");
            throw new Exception("La Cuenta esta Desactivada");
        }

        if (nttMovimiento.getMovTipoMovimiento() == null || nttMovimiento.getMovTipoMovimiento() == null) {
            log.error("Enter a valid Movement Type");
            throw new Exception("Ingrese un Tipo de Movimiento válido");
        }

        nttMovimientoGuardar.setMovCuenId(nttCuenta);
        nttMovimientoGuardar.setMovFecha(new Date());
        nttMovimientoGuardar.setMovSaldoInicial(nttCuenta.getCuenSaldoInicial());
        nttMovimientoGuardar.setMovTipoMovimiento(encontrarTipoMovimientoPorId(nttMovimiento.getMovTipoMovimiento()));
        BigDecimal saldoFinal;
        switch (nttMovimientoGuardar.getMovTipoMovimiento().getTpmId()){
            //retiro
            case 1:
                if (nttCuenta.getCuenSaldoInicial().doubleValue() < nttMovimiento.getMovValor().doubleValue()) {
                    log.error("Balance not available");
                    throw new Exception("Saldo no Disponible");
                }
                saldoFinal = nttCuenta.getCuenSaldoInicial().subtract(nttMovimiento.getMovValor());
                nttMovimientoGuardar.setMovSaldo(saldoFinal);
                nttMovimientoGuardar.setMovValor(nttMovimiento.getMovValor());
                break;
                //deposito
            case 2:
                saldoFinal = nttCuenta.getCuenSaldoInicial().add(nttMovimiento.getMovValor());
                nttMovimientoGuardar.setMovSaldo(saldoFinal);
                nttMovimientoGuardar.setMovValor(nttMovimiento.getMovValor());
                break;
            default:
                log.error("Enter a Valid Movement Type");
                throw new Exception("Ingrese un Tipo de Movimiento Válido");
        }

        NttCuentaDto nttCuentaDto = new NttCuentaDto();
        nttCuentaDto.setCuenSaldoInicial(saldoFinal);
        nttCuentaDto.setCuenEstado(true);
        nttCuentaServiceImpl.actualizarCuenta(nttCuenta.getCuenId(), nttCuentaDto);
        return nttMovimientoRepository.save(nttMovimientoGuardar);
    }


}
