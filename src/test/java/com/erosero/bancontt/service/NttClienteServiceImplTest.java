package com.erosero.bancontt.service;

import com.erosero.bancontt.dto.NttClienteDto;
import com.erosero.bancontt.entity.NttCliente;
import com.erosero.bancontt.repository.NttClienteRepository;
import com.erosero.bancontt.service.impl.NttClienteServiceImpl;
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
@ContextConfiguration(classes = {NttClienteServiceImplTest.class})
public class NttClienteServiceImplTest {

    @Mock
    private NttClienteRepository nttClienteRepository;

    @InjectMocks
    private NttClienteServiceImpl nttClienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void encontrarClientePorId_ClienteExistente_RetornaCliente() throws Exception {
        
        Integer clienteId = 1;
        NttCliente clienteMock = new NttCliente();
        clienteMock.setPersId(clienteId);
        Mockito.when(nttClienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

        
        NttCliente result = nttClienteService.encontrarClientePorId(clienteId);

        
        Assertions.assertEquals(clienteMock, result);
    }

    @Test
    public void encontrarClientePorId_ClienteNoExistente_LanzaExcepcion() {
        Integer clienteId = 1;
        Mockito.when(nttClienteRepository.findById(clienteId)).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {
            nttClienteService.encontrarClientePorId(clienteId);
        });
    }

    @Test
    public void actualizarCliente_ClienteNoExistente_LanzaExcepcion() {
        
        Integer clienteId = 1;
        NttClienteDto nttClienteActualizar = new NttClienteDto();
        Mockito.when(nttClienteRepository.findById(clienteId)).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {
            nttClienteService.actualizarCliente(clienteId, nttClienteActualizar);
        });
    }

    @Test
    public void eliminarCliente_ClienteExistente_EliminaClienteCorrectamente() throws Exception {
        
        Integer clienteId = 1;
        NttCliente clienteExistente = new NttCliente();
        clienteExistente.setPersId(clienteId);
        Mockito.when(nttClienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));

        
        NttCliente result = nttClienteService.eliminarCliente(clienteId);

        
        Mockito.verify(nttClienteRepository, Mockito.times(1)).delete(clienteExistente);
        Assertions.assertEquals(clienteExistente, result);
    }

    @Test
    public void eliminarCliente_ClienteNoExistente_LanzaExcepcion() {
        Integer clienteId = 1;
        Mockito.when(nttClienteRepository.findById(clienteId)).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {
            nttClienteService.eliminarCliente(clienteId);
        });
    }


}
