package com.erosero.bancontt.service;

import com.erosero.bancontt.dto.NttCuentaDto;
import com.erosero.bancontt.dto.NttMovimientoDto;
import com.erosero.bancontt.dto.ReporteMovimientoDto;
import com.erosero.bancontt.entity.NttCuenta;
import com.erosero.bancontt.entity.NttMovimiento;
import com.erosero.bancontt.entity.NttTipoMovimiento;
import com.erosero.bancontt.repository.NttMovimientoRepository;
import com.erosero.bancontt.repository.NttTipoMovimientoRepository;
import com.erosero.bancontt.service.impl.NttCuentaServiceImpl;
import com.erosero.bancontt.service.impl.NttMovimientoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@SpringBootTest
@ContextConfiguration(classes = {NttMovimientoServiceImplTest.class})
public class NttMovimientoServiceImplTest {
    @Mock
    private NttMovimientoRepository nttMovimientoRepository;

    @Mock
    private NttCuentaServiceImpl nttCuentaServiceImpl;

    @Mock
    private NttTipoMovimientoRepository nttTipoMovimientoRepository;

    @InjectMocks
    private NttMovimientoServiceImpl nttMovimientoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void encontrarTipoMovimientoPorId_TipoMovimientoExistente_RetornaTipoMovimiento() throws Exception {
        Integer tipoMovimientoId = 1;
        NttTipoMovimiento tipoMovimientoMock = new NttTipoMovimiento();
        tipoMovimientoMock.setTpmId(tipoMovimientoId);
        Mockito.when(nttTipoMovimientoRepository.findById(tipoMovimientoId)).thenReturn(Optional.of(tipoMovimientoMock));
        NttTipoMovimiento result = nttMovimientoService.encontrarTipoMovimientoPorId(tipoMovimientoId);
        Assertions.assertEquals(tipoMovimientoMock, result);
    }

    @Test
    public void encontrarTipoMovimientoPorId_TipoMovimientoNoExistente_LanzaExcepcion() {
        Integer tipoMovimientoId = 1;
        Mockito.when(nttTipoMovimientoRepository.findById(tipoMovimientoId)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> {
            nttMovimientoService.encontrarTipoMovimientoPorId(tipoMovimientoId);
        });
    }

    @Test
    public void encontrarMovimientoPorFechas_RetornaListaDeMovimientos() throws Exception {
        Date fechaInicial = new Date();
        Date fechaFinal = new Date();
        Integer clienteId = 1;
        List<NttMovimiento> movimientoList = new ArrayList<>();


        Mockito.when(nttMovimientoRepository.encontrarMovimientosPorFechas(fechaInicial, fechaFinal, clienteId)).thenReturn(movimientoList);
        List<ReporteMovimientoDto> result = nttMovimientoService.encontrarMovimientoPorFechas(fechaInicial, fechaFinal, clienteId);
        Assertions.assertNotNull(result);
    }

    @Test
    public void guardarMovimiento_GuardaMovimientoError() throws Exception {
        NttMovimientoDto nttMovimientoDto = new NttMovimientoDto();
        nttMovimientoDto.setMovCuenId(1);
        nttMovimientoDto.setMovTipoMovimiento(1);
        nttMovimientoDto.setMovValor(new BigDecimal(200));

        NttCuenta cuentaMock = new NttCuenta();
        cuentaMock.setCuenId(1);
        cuentaMock.setCuenEstado(true);
        cuentaMock.setCuenSaldoInicial(new BigDecimal(300));
        Mockito.when(nttCuentaServiceImpl.encontrarCuentaPorId(Mockito.anyInt())).thenReturn(cuentaMock);

        NttTipoMovimiento tipoMovimientoMock = new NttTipoMovimiento();
        tipoMovimientoMock.setTpmId(1);
        tipoMovimientoMock.setTpmDescripcion("ahorros");
        Mockito.when(nttTipoMovimientoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(tipoMovimientoMock));
        NttMovimiento result = nttMovimientoService.guardarMovimiento(nttMovimientoDto);
        Mockito.verify(nttCuentaServiceImpl, Mockito.times(1)).actualizarCuenta(Mockito.anyInt(), Mockito.any(NttCuentaDto.class));
        Mockito.verify(nttMovimientoRepository, Mockito.times(1)).save(Mockito.any(NttMovimiento.class));
        Assertions.assertNull(result);
    }
}
