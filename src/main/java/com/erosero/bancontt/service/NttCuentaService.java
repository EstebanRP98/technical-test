package com.erosero.bancontt.service;

import com.erosero.bancontt.dto.NttCuentaDto;
import com.erosero.bancontt.entity.NttCuenta;
import com.erosero.bancontt.entity.NttTipoCuenta;

public interface NttCuentaService {

    NttTipoCuenta encontrarTipoCuentaPorId(Integer tipoCuentaId) throws Exception;

    NttCuenta encontrarCuentaPorId(Integer cuentaId) throws Exception;

    NttCuenta guardarCuenta(NttCuentaDto nttCuenta) throws Exception;

    NttCuenta actualizarCuenta(Integer id, NttCuentaDto nttCuentaActualizar) throws Exception;

    NttCuenta eliminarCuenta(Integer id) throws Exception ;

}
