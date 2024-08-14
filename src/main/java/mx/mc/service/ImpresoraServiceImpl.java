/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ImpresoraMapper;
import mx.mc.model.Impresora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class ImpresoraServiceImpl extends GenericCrudServiceImpl<Impresora, String> implements ImpresoraService {
    
    @Autowired
    private ImpresoraMapper impresoraMapper;
    
    @Autowired
    public ImpresoraServiceImpl(GenericCrudMapper<Impresora, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public Impresora obtenerPorIdImpresora(String idImpresora) throws Exception {
        Impresora unaImpresora = new Impresora();
        try {
            unaImpresora = impresoraMapper.obtenerPorIdImpresora(idImpresora);
        } catch(Exception ex) {
            throw new Exception("Error al obtener la impresora " + ex.getMessage());
        }
        return unaImpresora;
    }

    @Override
    public List<Impresora> obtenerListaImpresora() throws Exception {
        List<Impresora> listaImpresoras = new ArrayList<>();
        try {
            listaImpresoras = impresoraMapper.obtenerListaImpresora();
        } catch(Exception ex) {
            throw new Exception("Error al obtener la lista de impresoras " + ex.getMessage());
        }
        return listaImpresoras;
    }
    
    @Override
    public List<Impresora> obtenerListaImpresoras(String idUsuario, String descripcion, String tipo) throws Exception {
        List<Impresora> listaImpresoras = new ArrayList<>();
        try {
            listaImpresoras = impresoraMapper.obtenerListaImpresoras(idUsuario, descripcion, tipo);
        } catch(Exception ex) {
            throw new Exception("Error al obtenerListaImpresoras " + ex.getMessage());
        }
        return listaImpresoras;
    }

    @Override
    public Impresora obtenerImpresoraByIpAndDescripcion(Impresora impresora) throws Exception {
        Impresora unaImpresora = new Impresora();
        try {
            unaImpresora = impresoraMapper.obtenerImpresoraByIpAndDescripcion(impresora);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener impresora por ip y descripción" + ex.getMessage());
        }
        return unaImpresora;
    }

    @Override
    public List<Impresora> obtenerListaImpresoraByUsuario(String idUsuario) throws Exception {
         List<Impresora> listaImpresoras = new ArrayList<>();
        try {
            listaImpresoras = impresoraMapper.obtenerListaImpresoraByUsuario(idUsuario);
        } catch(Exception ex) {
            throw new Exception("Error al obtener la lista de impresoras " + ex.getMessage());
        }
        return listaImpresoras;
    }
    
}
