/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.FolioAlternativoFolioMusMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.FolioAlternativoFolioMusExtended;
import mx.mc.model.ReabastoInsumoExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class FolioAlternativoFolioMusServiceImpl extends GenericCrudServiceImpl<FolioAlternativoFolioMus, String> implements FolioAlternativoFolioMusService {
    
    @Autowired
    private FolioAlternativoFolioMusMapper folioAlternativoFolioMusMapper;
    
    @Autowired
    public FolioAlternativoFolioMusServiceImpl(GenericCrudMapper<FolioAlternativoFolioMus, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<ReabastoInsumoExtended> obtenerDetalleReabasto(String folioAlternativo) throws Exception {
        try {
            return folioAlternativoFolioMusMapper.obtenerDetalleReabasto(folioAlternativo);

        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerDetalleReabasto. " + ex.getMessage());
        }
    }

    @Override
    public List<FolioAlternativoFolioMus> obtenerFoliosAlternativos(String folioMUS) throws Exception {
        try {
            return folioAlternativoFolioMusMapper.obtenerFoliosAlternativos(folioMUS);

        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerFoliosAlternativos. " + ex.getMessage());
        }
    }   
    
    @Override
    public List<FolioAlternativoFolioMusExtended> obtenerFoliosAlternativosExtended(String folioMus) throws Exception{
        try{
            return folioAlternativoFolioMusMapper.obtenerFoliosAlternativosExtended(folioMus);
        }
        catch(Exception ex){
            throw  new Exception("Error al obtener los folios alternativos: "+ex);
        }    
    }   
    
    @Override
    public FolioAlternativoFolioMus obtenerFolioAlt(String folioMus, String idService) throws Exception{
        try{
            return folioAlternativoFolioMusMapper.obtenerFolioAlt(folioMus,idService);
        }
        catch(Exception ex){
            throw  new Exception("Error al obtener el folio alternativo: "+ex);
        }    
    }   
    
    @Override
    public boolean actualizarFolioAlt(String folioAlternativo,String folioMus,String updateIdUsuario,String estatus,String idService) throws Exception{
        try{
            return folioAlternativoFolioMusMapper.actualizarFolioAlt(folioAlternativo,folioMus,updateIdUsuario,estatus,idService);
        }
        catch(Exception ex){
            throw  new Exception("Error al actualizar el folio alternativo: "+ex);
        }    
    }
    
    @Override
    public FolioAlternativoFolioMus obtenerFolioAlternativo(String folioAlternativo,String folioMus) throws Exception{
        try {
            return folioAlternativoFolioMusMapper.obtenerFolioAlternativoByFolio(folioAlternativo,folioMus);
        } catch (Exception ex) {
            throw  new Exception("Error al obtener el folio Alternbativo"+ex.getMessage());
        }
    }

    @Override
    public boolean eliminarIdFolio(String idFolioalternativo) throws Exception {
        try {
            return folioAlternativoFolioMusMapper.eliminarIdFolio(idFolioalternativo);
        } catch (Exception ex) {
            throw  new Exception("Error al eliminar el folio Alternbativo"+ex.getMessage());
        }
    }
    
    
}
