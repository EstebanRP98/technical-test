package com.erosero.bancontt.service;

import com.erosero.bancontt.dto.NttCuentaDto;
import com.erosero.bancontt.entity.NttCuenta;
import com.erosero.bancontt.entity.NttTipoCuenta;
import com.erosero.bancontt.repository.NttCuentaRepository;
import com.erosero.bancontt.repository.NttTipoCuentaRepository;
import com.erosero.bancontt.service.impl.NttClienteServiceImpl;
import com.erosero.bancontt.service.impl.NttCuentaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
@SpringBootTest
@ContextConfiguration(classes = {NttCuentaServiceImplTest.class})
public class NttCuentaServiceImplTest {
    @Mock
    private NttCuentaRepository nttCuentaRepository;

    @Mock
    private NttTipoCuentaRepository nttTipoCuentaRepository;

    @Mock
    private NttClienteServiceImpl nttClienteServiceImpl;

    @InjectMocks
    private NttCuentaServiceImpl nttCuentaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void encontrarTipoCuentaPorId_TipoCuentaExistente_RetornaTipoCuenta() throws Exception {
        // Arrange
        Integer tipoCuentaId = 1;
        NttTipoCuenta tipoCuentaMock = new NttTipoCuenta();
        tipoCuentaMock.setTpcId(tipoCuentaId);
        tipoCuentaMock.setTpcDescripcion("new");
        Mockito.when(nttTipoCuentaRepository.findById(tipoCuentaId)).thenReturn(Optional.of(tipoCuentaMock));
        NttTipoCuenta result = nttCuentaService.encontrarTipoCuentaPorId(tipoCuentaId);
        Assertions.assertEquals(tipoCuentaMock, result);
    }

    @Test
    public void encontrarTipoCuentaPorId_TipoCuentaNoExistente_LanzaExcepcion() {
        // Arrange
        Integer tipoCuentaId = 1;
        Mockito.when(nttTipoCuentaRepository.findById(tipoCuentaId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            nttCuentaService.encontrarTipoCuentaPorId(tipoCuentaId);
        });
    }

    @Test
    public void encontrarCuentaPorId_CuentaExistente_RetornaCuenta() throws Exception {
        // Arrange
        Integer cuentaId = 1;
        NttCuenta cuentaMock = new NttCuenta();
        cuentaMock.setCuenId(cuentaId);
        Mockito.when(nttCuentaRepository.findById(cuentaId)).thenReturn(Optional.of(cuentaMock));

        // Act
        NttCuenta result = nttCuentaService.encontrarCuentaPorId(cuentaId);

        // Assert
        Assertions.assertEquals(cuentaMock, result);
    }

    @Test
    public void encontrarCuentaPorId_CuentaNoExistente_LanzaExcepcion() {
        // Arrange
        Integer cuentaId = 1;
        Mockito.when(nttCuentaRepository.findById(cuentaId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            nttCuentaService.encontrarCuentaPorId(cuentaId);
        });
    }

    @Test
    public void actualizarCuenta_CuentaNoExistente_LanzaExcepcion() {
        // Arrange
        Integer cuentaId = 1;
        NttCuentaDto nttCuentaActualizar = new NttCuentaDto();
        // ... Configurar datos del DTO

        Mockito.when(nttCuentaRepository.findById(cuentaId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            nttCuentaService.actualizarCuenta(cuentaId, nttCuentaActualizar);
        });
    }

    @Test
    public void eliminarCuenta_CuentaExistente_EliminaCuentaCorrectamente() throws Exception {
        // Arrange
        Integer cuentaId = 1;
        NttCuenta cuentaExistente = new NttCuenta();
        cuentaExistente.setCuenId(cuentaId);
        Mockito.when(nttCuentaRepository.findById(cuentaId)).thenReturn(Optional.of(cuentaExistente));

        // Act
        NttCuenta result = nttCuentaService.eliminarCuenta(cuentaId);

        // Assert
        Mockito.verify(nttCuentaRepository, Mockito.times(1)).save(Mockito.eq(cuentaExistente));
        Assertions.assertEquals(cuentaExistente, result);
    }

    @Test
    public void eliminarCuenta_CuentaNoExistente_LanzaExcepcion() {
        // Arrange
        Integer cuentaId = 1;
        Mockito.when(nttCuentaRepository.findById(cuentaId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            nttCuentaService.eliminarCuenta(cuentaId);
        });
    }

}
