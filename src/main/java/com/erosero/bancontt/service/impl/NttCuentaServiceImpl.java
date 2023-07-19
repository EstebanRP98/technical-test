package com.erosero.bancontt.service.impl;

import com.erosero.bancontt.dto.NttCuentaDto;
import com.erosero.bancontt.entity.NttCuenta;
import com.erosero.bancontt.entity.NttTipoCuenta;
import com.erosero.bancontt.repository.NttCuentaRepository;
import com.erosero.bancontt.repository.NttTipoCuentaRepository;
import com.erosero.bancontt.service.NttCuentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class NttCuentaServiceImpl implements NttCuentaService {

    @Autowired
    NttCuentaRepository nttCuentaRepository;

    @Autowired
    NttTipoCuentaRepository nttTipoCuentaRepository;

    @Autowired
    NttClienteServiceImpl nttClienteServiceImpl;

    public NttCuentaServiceImpl(NttCuentaRepository nttCuentaRepository) {
        this.nttCuentaRepository = nttCuentaRepository;
    }

    public NttTipoCuenta encontrarTipoCuentaPorId(Integer tipoCuentaId) throws Exception {
        log.info("Find account type");
        Optional<NttTipoCuenta> nttTipoCuenta = nttTipoCuentaRepository.findById(tipoCuentaId);
        if (!nttTipoCuenta.isPresent()) {
            throw new Exception("Ingrese un Tipo de Cuenta Válido");
        }
        return nttTipoCuenta.get();
    }

    public NttCuenta encontrarCuentaPorId(Integer cuentaId) throws Exception {
        log.info("Find account");
        Optional<NttCuenta> nttCuenta = nttCuentaRepository.findById(cuentaId);
        if (!nttCuenta.isPresent()) {
            throw new Exception("Ingrese una Cuenta Válida");
        }
        return nttCuenta.get();
    }

    public NttCuenta guardarCuenta(NttCuentaDto nttCuenta) throws Exception {
        log.info("Save account");
        NttCuenta nttCuentaGuardada = new NttCuenta();

        nttCuentaGuardada.setCuenNumero(nttCuenta.getCuenNumero() != null ? nttCuenta.getCuenNumero() : null);
        nttCuentaGuardada.setCuenEstado(nttCuenta.isCuenEstado());
        nttCuentaGuardada.setCuenSaldoInicial(nttCuenta.getCuenSaldoInicial() != null ? nttCuenta.getCuenSaldoInicial() : null);
        nttCuentaGuardada.setCuenTipoCuenta(nttCuenta.getCuenTipoCuenta() != null ? encontrarTipoCuentaPorId(nttCuenta.getCuenTipoCuenta()) : null);
        nttCuentaGuardada.setCuenCliId(nttCuenta.getCuenCliId() != null ? nttClienteServiceImpl.encontrarClientePorId(nttCuenta.getCuenCliId()) : null);

        return nttCuentaRepository.save(nttCuentaGuardada);
    }

    public NttCuenta actualizarCuenta(Integer id, NttCuentaDto nttCuentaActualizar) throws Exception {
        log.info("Update account");
        Optional<NttCuenta> cuenta = nttCuentaRepository.findById(id);

        if (!cuenta.isPresent())
            throw new Exception("La Cuenta que intenta actualizar no existe");

        cuenta.get().setCuenNumero(nttCuentaActualizar.getCuenNumero() != null ? nttCuentaActualizar.getCuenNumero() : cuenta.get().getCuenNumero());
        cuenta.get().setCuenEstado(nttCuentaActualizar.isCuenEstado());
        cuenta.get().setCuenSaldoInicial(nttCuentaActualizar.getCuenSaldoInicial() != null ? nttCuentaActualizar.getCuenSaldoInicial() : cuenta.get().getCuenSaldoInicial());
        cuenta.get().setCuenTipoCuenta(nttCuentaActualizar.getCuenTipoCuenta() != null ? encontrarTipoCuentaPorId(nttCuentaActualizar.getCuenTipoCuenta()) : cuenta.get().getCuenTipoCuenta());
        cuenta.get().setCuenCliId(nttCuentaActualizar.getCuenCliId() != null ? nttClienteServiceImpl.encontrarClientePorId(nttCuentaActualizar.getCuenCliId()) : cuenta.get().getCuenCliId());

        return nttCuentaRepository.save(cuenta.get());
    }

    public NttCuenta eliminarCuenta(Integer id) throws Exception {
        log.info("Delete account");
        Optional<NttCuenta> nttCuenta = nttCuentaRepository.findById(id);

        if (!nttCuenta.isPresent())
            throw new Exception("La Cuenta que intenta eliminar no existe");
        nttCuenta.get().setCuenEstado(false);
        nttCuentaRepository.save(nttCuenta.get());
        return nttCuenta.get();
    }


}
