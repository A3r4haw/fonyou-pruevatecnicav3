/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.DetalleInsumoSiam;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Admin
 */
public interface DetalleInsumoSiamMapper  extends GenericCrudMapper<DetalleInsumoSiam,String> {
    
    public boolean insertarLista(List<DetalleInsumoSiam> listaDetalle);
    
    public boolean actualizarLista(List<DetalleInsumoSiam> listaDetalle);
    
    public List<DetalleInsumoSiam> obtenerDetalleSIAM(@Param("idFolioAlternativo") String idFolioAlternativo);
    
    public DetalleInsumoSiam obtenerDetallePorFolioEInsumo(@Param("folioAlternativo") String folioAlternativo, @Param("idInsumo") String idInsumo);
    
    public DetalleInsumoSiam ultimaColectivaSurtida(@Param("idEstructura") String idEstructura, @Param("idInsumo") String idInsumo);
    
    public List<DetalleInsumoSiam> detalleSIAMxFolioEstructura(@Param("folioMus") String folioMus,@Param("idEstructura") String idEstructura,@Param("status") String status);
    
    public boolean eliminarInsumo(@Param("idFolioAlternativo") String idFolioAlternativo, @Param("idInsumo") String idInsumo);
    
    public boolean eliminarDetalle(@Param("idFolioAlternativo") String idFolioAlternativo);
    
}
