/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.DetalleInsumoSiamMapper;
import mx.mc.mapper.FolioAlternativoFolioMusMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.DetalleInsumoSiam;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Reabasto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class DetalleInsumoSiamServiceImpl extends GenericCrudServiceImpl<DetalleInsumoSiam,String> implements DetalleInsumoSiamService {

    @Autowired
    private FolioAlternativoFolioMusMapper folioAlternativoMapper;
    @Autowired
    private DetalleInsumoSiamMapper detalleSiamMapper;    
    
    @Autowired
    public DetalleInsumoSiamServiceImpl(GenericCrudMapper<DetalleInsumoSiam, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public boolean insertarLista(List<DetalleInsumoSiam> listDetalle) throws Exception {
        try{
            return detalleSiamMapper.insertarLista(listDetalle);
        }catch(Exception ex){
            throw new Exception("Error al insertar la lista SIAM. " + ex.getMessage());
        }
    }

    @Override
    public boolean actualizarLista(List<DetalleInsumoSiam> listDetalle) throws Exception {
        try{
            return detalleSiamMapper.actualizarLista(listDetalle);
        }catch(Exception ex){
            throw new Exception("Error al actualizar la lista SIAM. " + ex.getMessage());
        }
    }

    @Override
    public List<DetalleInsumoSiam> obtenerDetalleSIAM(String idFolioAlternativo)throws Exception {
        try{
            return detalleSiamMapper.obtenerDetalleSIAM(idFolioAlternativo);
        }catch(Exception ex){
            throw new Exception("Error al obtener el detalle del colectivo SIAM. " + ex.getMessage());
        }
    }
    
    @Override
    public DetalleInsumoSiam obtenerDetalleSIAM(String folioAlternativo, String idInsumo)throws Exception {
        try {
            return detalleSiamMapper.obtenerDetallePorFolioEInsumo(folioAlternativo, idInsumo);
        } catch(Exception ex) {
            throw new Exception("Error al obtener el detalle del colectivo SIAM por FolioAlternativo e Insumo. " + ex.getMessage());
        }
    }
    
    @Override
    public DetalleInsumoSiam ultimaColectivaSurtida(String idEstructura, String idInsumo) throws Exception {
        try {
            return detalleSiamMapper.ultimaColectivaSurtida(idEstructura, idInsumo);
        }
        catch(Exception ex) {
            throw  new Exception("Error al obtener los folios Ultima Fecha Colectiva Surtida: " + ex);
        }    
    }
    @Override
    public List<DetalleInsumoSiam> obtenerDetalleXFolioEstructura(String folioMus,String idEstructura,String estatus)  throws Exception{
        try{
            return detalleSiamMapper.detalleSIAMxFolioEstructura(folioMus,idEstructura,estatus);
        }catch(Exception ex){
            throw new Exception("Error al obtener el detalle del colectivo SIAM. " + ex.getMessage());
        }   
    }

    @Override
    public String eliminaInsumo(Reabasto reabasto, String idInsumo) throws Exception {
        String idFolioalternativo = "";
        try {
            FolioAlternativoFolioMus  folioAlternativo = folioAlternativoMapper.obtenerFolioAlt(reabasto.getFolio(), reabasto.getIdEstructura());
            if(folioAlternativo!=null){
                idFolioalternativo= folioAlternativo.getIdFolioAlternativo();
                detalleSiamMapper.eliminarInsumo(folioAlternativo.getIdFolioAlternativo(),idInsumo);
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar el insumo del colectivo SIAM. " + e.getMessage());
        }
        return idFolioalternativo;
    }
    
}
