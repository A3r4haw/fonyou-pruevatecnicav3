/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DetalleInsumoSiam;
import mx.mc.model.Reabasto;

/**
 *
 * @author Admin
 */
public interface DetalleInsumoSiamService  extends GenericCrudService<DetalleInsumoSiam,String>{
    
    public boolean insertarLista(List<DetalleInsumoSiam> detalle) throws Exception;    
    public boolean actualizarLista(List<DetalleInsumoSiam> detalle) throws Exception;
    public List<DetalleInsumoSiam> obtenerDetalleSIAM(String idFolioAlternativo) throws Exception;
    public DetalleInsumoSiam obtenerDetalleSIAM(String folioAlternativo, String idInsumo) throws Exception;
    public DetalleInsumoSiam ultimaColectivaSurtida(String idEstructura, String idInsumo) throws Exception;
    public List<DetalleInsumoSiam> obtenerDetalleXFolioEstructura(String folioMus,String idEstructura,String estatus)  throws Exception;
    public String eliminaInsumo(Reabasto reabasto, String idInsumo) throws Exception;
}
