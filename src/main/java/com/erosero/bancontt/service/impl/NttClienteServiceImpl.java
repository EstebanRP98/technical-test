package com.erosero.bancontt.service.impl;

import com.erosero.bancontt.dto.NttClienteDto;
import com.erosero.bancontt.entity.NttCliente;
import com.erosero.bancontt.repository.NttClienteRepository;
import com.erosero.bancontt.service.NttClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class NttClienteServiceImpl implements NttClienteService {

    @Autowired
    NttClienteRepository nttClienteRepository;


    public NttCliente encontrarClientePorId(Integer clienteId) throws Exception {
        log.info("Find customer by id");
        Optional<NttCliente> nttCliente = nttClienteRepository.findById(clienteId);
        if (!nttCliente.isPresent()) {
            throw new Exception("Ingrese un Cliente Válido");
        }
        return nttCliente.get();
    }

    public NttCliente guardarCliente(NttClienteDto nttCliente) throws Exception {
        log.info("Save customer");
        NttCliente nttClienteGuardado = new NttCliente();

        nttClienteGuardado.setCliEstado(true);
        nttClienteGuardado.setCliPassword(nttCliente.getCliPassword() != null ? nttCliente.getCliPassword() : null);
        nttClienteGuardado.setPersDireccion(nttCliente.getPersDireccion() != null ?
                nttCliente.getPersDireccion() : null);
        nttClienteGuardado.setPersFechaNacimiento(nttCliente.getPersFechaNacimiento() != null ?
                nttCliente.getPersFechaNacimiento() : null);
        nttClienteGuardado.setPersGenero(nttCliente.getPersGenero() != null ?
                nttCliente.getPersGenero() : null);
        nttClienteGuardado.setPersNombre(nttCliente.getPersNombre() != null ?
                nttCliente.getPersNombre() : null);
        nttClienteGuardado.setPersIdentificacion(nttCliente.getPersIdentificacion() != null ?
                nttCliente.getPersIdentificacion() : null);
        nttClienteGuardado.setPersTelefono(nttCliente.getPersTelefono() != null ?
                nttCliente.getPersTelefono() : null);

        return nttClienteRepository.save(nttClienteGuardado);
    }

    public NttCliente actualizarCliente(Integer id, NttClienteDto nttClienteActualizar) throws Exception {
        log.info("Update customer");
        Optional<NttCliente> cliente = nttClienteRepository.findById(id);

        if (!cliente.isPresent())
            throw new Exception("El Cliente que intenta actualizar no existe");

        cliente.get().setCliEstado(nttClienteActualizar.isCliEstado());
        cliente.get().setCliPassword(nttClienteActualizar.getCliPassword() != null ?
                nttClienteActualizar.getCliPassword() : cliente.get().getCliPassword());
        cliente.get().setPersDireccion(nttClienteActualizar.getPersDireccion() != null ?
                nttClienteActualizar.getPersDireccion() : cliente.get().getPersDireccion());
        cliente.get().setPersFechaNacimiento(nttClienteActualizar.getPersFechaNacimiento() != null ?
                nttClienteActualizar.getPersFechaNacimiento() : cliente.get().getPersFechaNacimiento());
        cliente.get().setPersGenero(nttClienteActualizar.getPersGenero() != null ?
                nttClienteActualizar.getPersGenero() : cliente.get().getPersGenero());
        cliente.get().setPersNombre(nttClienteActualizar.getPersNombre() != null ?
                nttClienteActualizar.getPersNombre() : cliente.get().getPersNombre());
        cliente.get().setPersIdentificacion(nttClienteActualizar.getPersIdentificacion() != null ?
                nttClienteActualizar.getPersIdentificacion() : cliente.get().getPersIdentificacion());
        cliente.get().setPersTelefono(nttClienteActualizar.getPersTelefono() != null ?
                nttClienteActualizar.getPersTelefono() : cliente.get().getPersTelefono());

        return nttClienteRepository.save(cliente.get());
    }

    public NttCliente eliminarCliente(Integer id) throws Exception {
        log.info("Delete customer");
        Optional<NttCliente> nttCliente = nttClienteRepository.findById(id);

        if (!nttCliente.isPresent())
            throw new Exception("La Cuenta que intenta eliminar no existe");

        nttClienteRepository.delete(nttCliente.get());
        return nttCliente.get();
    }




}
