package com.erosero.bancontt.service;

import com.erosero.bancontt.dto.NttClienteDto;
import com.erosero.bancontt.entity.NttCliente;

public interface NttClienteService {

    NttCliente encontrarClientePorId(Integer clienteId) throws Exception;

    NttCliente guardarCliente(NttClienteDto nttCliente) throws Exception;

    NttCliente actualizarCliente(Integer id, NttClienteDto nttClienteActualizar) throws Exception;

    NttCliente eliminarCliente(Integer id) throws Exception;

}
