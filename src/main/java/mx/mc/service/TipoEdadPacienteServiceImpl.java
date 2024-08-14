/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.TipoEdadPacienteMapper;
import mx.mc.model.TipoEdadPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class TipoEdadPacienteServiceImpl extends GenericCrudServiceImpl<TipoEdadPaciente, String> implements TipoEdadPacienteService {

    @Autowired
    TipoEdadPacienteMapper tipoEdadPacienteMapper;
    
    @Override
    public List<TipoEdadPaciente> obtenerTodo() throws Exception {
        try {
            return tipoEdadPacienteMapper.obtenerTodo();
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener lista de tipoEdadPaciente:  " + ex.getMessage());
        }
    }

    @Override
    public TipoEdadPaciente obtenerTipoPorId(Integer idTipoEdadPaciente) throws Exception {
        
        try {
            return tipoEdadPacienteMapper.obtenerTipoPorId(idTipoEdadPaciente);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener tipoEdadPaciente por id:  " + idTipoEdadPaciente + "  " + ex.getMessage());
        }
    }
    
}
