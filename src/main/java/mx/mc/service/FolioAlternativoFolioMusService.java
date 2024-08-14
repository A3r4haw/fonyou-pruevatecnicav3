/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.FolioAlternativoFolioMusExtended;
import mx.mc.model.ReabastoInsumoExtended;

/**
 *
 * @author gcruz
 */
public interface FolioAlternativoFolioMusService extends GenericCrudService<FolioAlternativoFolioMus, String>{
    
    public List<ReabastoInsumoExtended> obtenerDetalleReabasto(String folioAlternativo) throws Exception;
    
    public List<FolioAlternativoFolioMus> obtenerFoliosAlternativos(String folioMUS) throws Exception;
    
    public List<FolioAlternativoFolioMusExtended> obtenerFoliosAlternativosExtended(String folioMus) throws Exception;
    
    public FolioAlternativoFolioMus obtenerFolioAlt(String folioMus,String idService) throws Exception;
    
    public boolean actualizarFolioAlt(String folioAlternativo,String folioMus,String updateIdUsuario,String estatus,String idService) throws Exception;
    
    public FolioAlternativoFolioMus obtenerFolioAlternativo(String folioAlternativo,String folioMus) throws Exception;
    
    public boolean eliminarIdFolio(String idFolioalternativo) throws Exception;
}
