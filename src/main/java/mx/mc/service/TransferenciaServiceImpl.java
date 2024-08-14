/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.TransferenciaInsumoMapper;
import mx.mc.mapper.TransferenciaMapper;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.Transferencia;
import mx.mc.model.TransferenciaInsumo;
import mx.mc.util.Comunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class TransferenciaServiceImpl extends GenericCrudServiceImpl<Transferencia, String> implements TransferenciaService {
    
    @Autowired
    private TransferenciaMapper transferenciaMapper;
    
    @Autowired
    private TransferenciaInsumoMapper transferenciaInsumoMapper;
    
    @Autowired
    private InventarioMapper inventarioMapper;
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    public TransferenciaServiceImpl(GenericCrudMapper<Transferencia, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Transferencia> obtenerBusqueda(String cadena) throws Exception {        
        try{
            return transferenciaMapper.obtenerTransferencias(cadena);
        }catch(Exception ex){
            throw new Exception("Error al obtener la Busqueda. " + ex.getMessage());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarSolicitud(Transferencia transferencia, List<TransferenciaInsumo> insumos,Folios folio) throws Exception {
        boolean res=false;
        List<Inventario> insumosInv = new ArrayList<>();
        try{
            res =transferenciaMapper.insertar(transferencia);
            if(res){
                for(TransferenciaInsumo ri: insumos){
                   Inventario ac = new Inventario();
                   ac.setIdInsumo(ri.getIdInsumo());
                   ac.setCantidadActual(ri.getCantidadEnviada());
                   ac.setLote(ri.getLote());
                   ac.setIdEstructura(transferencia.getIdEstructura());
                   ac.setUpdateFecha(new Date());
                   ac.setUpdateIdUsuario(ri.getInsertIdUsuario());
                   insumosInv.add(ac);
                }
                folio.setSecuencia(Comunes.separaFolio(transferencia.getFolio()));
                folio.setUpdateFecha(new Date());
                folio.setUpdateIdUsuario(transferencia.getInsertIdUsuario());
                inventarioMapper.restarInventarioMasivo(insumosInv);
                foliosMapper.actualizaFolios(folio);
                res= transferenciaInsumoMapper.insertarLista(insumos);
                if(!res)
                    throw new Exception("Error al Insertar los insumos de la solicitud");    
            }else
                throw new Exception("Error al Insertar el reabasto de la solicitud ");
                
            
        }catch(Exception ex){
            throw new Exception("Error al Insertar la solicitud" + ex.getMessage());
        }
        return res;   
    }

    
    
   
    
}
