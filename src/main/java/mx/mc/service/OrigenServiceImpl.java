/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.sql.SQLException;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.OrigenMapper;
import mx.mc.model.Origen;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class OrigenServiceImpl extends GenericCrudServiceImpl<Origen, String> implements OrigenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportesServiceImpl.class);

    @Autowired
    private OrigenMapper origenMapper;
    
    @Override
    public List<Origen> obtenerLista(boolean activo) throws Exception {        
        try{
            return origenMapper.obtenerLista(activo);
        }catch(Exception ex){
            throw new Exception("Error en obtenerLista"+ex.getMessage());
        } 
        
    }

}
