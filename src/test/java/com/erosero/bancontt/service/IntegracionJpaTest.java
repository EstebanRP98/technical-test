package com.erosero.bancontt.service;

import com.erosero.bancontt.entity.*;
import com.erosero.bancontt.repository.*;
import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegracionJpaTest {

    @Autowired
    NttCuentaRepository nttCuentaRepository;
    @Autowired
    NttClienteRepository nttClienteRepository;
    @Autowired
    NttTipoCuentaRepository nttTipoCuentaRepository;
    @Autowired
    NttMovimientoRepository nttMovimientoRepository;
    @Autowired
    NttTipoMovimientoRepository nttTipoMovimientoRepository;

    @Test
    void saveCuenta(){
        Optional<NttCliente> nttCliente = nttClienteRepository.findById(99);
        Optional<NttTipoCuenta> nttTipoCuenta = nttTipoCuentaRepository.findById(1);
        NttCuenta cuenta = new NttCuenta(2, "2539187668", new BigDecimal("200.00"),
                true, nttCliente.get(), nttTipoCuenta.get() , null);
        NttCuenta nttCuentaGuardada = nttCuentaRepository.save(cuenta);


        assertEquals("2539187668", nttCuentaGuardada.getCuenNumero());
        assertEquals(new BigDecimal("200.00"), nttCuentaGuardada.getCuenSaldoInicial());
    }


    @Test
    void encontrarClientePorId(){
        Optional<NttCliente> nttCliente = nttClienteRepository.findById(99);
        assertTrue(nttCliente.isPresent());
        assertEquals("1708306046", nttCliente.get().getPersIdentificacion());
    }

    @Test
    void saveCliente(){
        NttCliente cliente = new NttCliente(2, "Juan", "masculino", "1726442906", new Date(), "remigio",
                "0935467472","pass", true, null );
        NttCliente nttClienteGuardada = nttClienteRepository.save(cliente);


        assertEquals("1726442906", nttClienteGuardada.getPersIdentificacion());
        assertEquals("0935467472", nttClienteGuardada.getPersTelefono());
    }





}
