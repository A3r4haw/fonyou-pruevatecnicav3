/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.CapsulaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Capsula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 *
 * @author olozada
 */
@Service
public class CapsulaServiceImpl extends GenericCrudServiceImpl<Capsula , String> implements CapsulaService {
    
    @Autowired
    private CapsulaMapper capsulaMapper;
    
    @Autowired
    public CapsulaServiceImpl(GenericCrudMapper<Capsula, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<Capsula> obtieneListaCapsulasActivas(int activo , String idEstructura ) throws Exception {
        List<Capsula> listaCapsulas = new ArrayList<>();
        try {
            listaCapsulas = capsulaMapper.obtieneListaCapsulasActivas(activo , idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Capsulas!! " + ex.getMessage());
        }
        return listaCapsulas;
    }
    
    @Override
    public List<Capsula> obteneridCapsula(String codigoCapsula) throws Exception {
        List<Capsula> idCapsula = new ArrayList<>();
        try {
             idCapsula = capsulaMapper.obteneridCapsula(codigoCapsula);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Capsulas!! " + ex.getMessage());
        }
        return idCapsula;
    }
    
    @Override
    public List<Capsula> obtenerCapsulas(String cadenaBusqueda) throws Exception {
        try {
            return capsulaMapper.obtenerCapsulas(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener Capsulas   " + ex.getMessage());
        }
    }
    
    @Override
    public List<Capsula> obtenerCapsulasponombre(String cadenaBusqueda) throws Exception {
        try {
            return capsulaMapper.obtenerCapsulasponombre(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener lista de Capsulas   " + ex.getMessage());
        }
    }
}
