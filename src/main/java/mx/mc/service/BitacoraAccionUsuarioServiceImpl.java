/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.BitacoraAccionUsuarioMapper;
import mx.mc.model.BitacoraAccionesUsuario;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class BitacoraAccionUsuarioServiceImpl extends GenericCrudServiceImpl<BitacoraAccionesUsuario, String> implements BitacoraAccionUsuarioService{

    @Autowired
    private  BitacoraAccionUsuarioMapper bitacoraAccionUsuarioMapper;
    
    @Override
    public List<BitacoraAccionesUsuario> consultarAccionesUsuario(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {            
            if(paramBusquedaReporte.getListUsuarios() != null && !paramBusquedaReporte.getListUsuarios().isEmpty()) {
                for(Usuario usuario : paramBusquedaReporte.getListUsuarios()) {
                    StringBuilder nombreUsuario = new StringBuilder();
                    nombreUsuario.append(usuario.getNombre());
                    if(!usuario.getApellidoPaterno().isEmpty()) {
                        nombreUsuario.append(" ").append(usuario.getApellidoPaterno());
                    }
                    if(!usuario.getApellidoMaterno().isEmpty()) {
                        nombreUsuario.append(" ").append(usuario.getApellidoMaterno());
                    }
                    usuario.setNombreUsuario(nombreUsuario.toString());
                }
            }            
            return bitacoraAccionUsuarioMapper.consultarAccionesUsuario(paramBusquedaReporte, startingAt, maxPerPage);
        } catch(Exception ex) {
            throw new Exception("Error al obtener consulta de bitacora acciones usuario" + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {                 
            if(paramBusquedaReporte.getListUsuarios() != null && !paramBusquedaReporte.getListUsuarios().isEmpty()) {
                for(Usuario usuario : paramBusquedaReporte.getListUsuarios()) {
                    StringBuilder nombreUsuario = new StringBuilder();
                    nombreUsuario.append(usuario.getNombre());
                    if(!usuario.getApellidoPaterno().isEmpty()) {
                        nombreUsuario.append(" ").append(usuario.getApellidoPaterno());
                    }
                    if(!usuario.getApellidoMaterno().isEmpty()) {
                        nombreUsuario.append(" ").append(usuario.getApellidoMaterno());
                    }
                    usuario.setNombreUsuario(nombreUsuario.toString());
                }
            }
            return bitacoraAccionUsuarioMapper.obtenerTotalRegistros(paramBusquedaReporte);
        } catch(Exception ex) {
            throw new Exception("Error al obtener total de bitacora acciones usuario" + ex.getMessage());
        }
    }

  
    
}
