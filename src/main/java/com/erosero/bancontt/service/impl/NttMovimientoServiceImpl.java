package com.erosero.bancontt.service.impl;

import com.erosero.bancontt.dto.NttCuentaDto;
import com.erosero.bancontt.dto.NttMovimientoDto;
import com.erosero.bancontt.dto.ReporteMovimientoDto;
import com.erosero.bancontt.entity.NttCuenta;
import com.erosero.bancontt.entity.NttMovimiento;
import com.erosero.bancontt.entity.NttTipoMovimiento;
import com.erosero.bancontt.exception.*;
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

    public NttTipoMovimiento encontrarTipoMovimientoPorId(Integer tipoMovimientoId) {
        log.info("Find type of movement");
        Optional<NttTipoMovimiento> nttTipoMovimiento = nttTipoMovimientoRepository.findById(tipoMovimientoId);
        if (!nttTipoMovimiento.isPresent()) {
            log.error("Type Movement not found");
            throw new TypeMovementNotFoundException();
        }
        return nttTipoMovimiento.get();
    }

    public List<ReporteMovimientoDto> encontrarMovimientoPorFechas(Date fechaInicial, Date fechaFinal, Integer clienteId) {
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
    public NttMovimiento guardarMovimiento(NttMovimientoDto nttMovimiento) {
        log.info("Save movement");
        NttMovimiento nttMovimientoGuardar = new NttMovimiento();
        NttCuenta nttCuenta = nttCuentaServiceImpl.encontrarCuentaPorId(nttMovimiento.getMovCuenId());
        if (!nttCuenta.isCuenEstado()) {
            log.error("Account is Deactivated");
            throw new AccountDeactivatedException();
        }
        validarMovimiento(nttMovimiento, nttCuenta);
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
                    throw new BalanceNotAvailableException();
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
                throw new TypeMovementNotFoundException();
        }

        NttCuentaDto nttCuentaDto = new NttCuentaDto();
        nttCuentaDto.setCuenSaldoInicial(saldoFinal);
        nttCuentaDto.setCuenEstado(true);
        nttCuentaServiceImpl.actualizarCuenta(nttCuenta.getCuenId(), nttCuentaDto);
        return nttMovimientoRepository.save(nttMovimientoGuardar);
    }

    public void validarMovimiento(NttMovimientoDto nttMovimiento, NttCuenta nttCuenta) {
        if (nttMovimiento.getMovCuenId() == null ) {
            log.error("Enter a Valid Account");
            throw new AccountNotFoundException();
        }

        if(nttMovimiento.getMovValor().compareTo(new BigDecimal(5)) <= 0 ){
            log.error("The transaction cannot be less than 5 dollars.");
            throw new TransactionException();
        }

        if (nttMovimiento.getMovTipoMovimiento() == null || nttMovimiento.getMovTipoMovimiento() == null) {
            log.error("Type Movement not found");
            throw new TypeMovementNotFoundException();
        }
    }


}
