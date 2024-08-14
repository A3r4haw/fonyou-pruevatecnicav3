/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.FolioAlternativoFolioMusExtended;
import mx.mc.model.ReabastoInsumoExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface FolioAlternativoFolioMusMapper extends GenericCrudMapper<FolioAlternativoFolioMus, String> {
    
    public List<ReabastoInsumoExtended> obtenerDetalleReabasto(@Param("folioAlternativo") String folioAlternativo);
    public boolean eliminarPorFolio(@Param("folioMUS")String folioMUS );
    public List<FolioAlternativoFolioMus> obtenerFoliosAlternativos(@Param("folioMUS") String folioMUS);
    public List<FolioAlternativoFolioMusExtended> obtenerFoliosAlternativosExtended(@Param("folioMus") String folioMus);
    public FolioAlternativoFolioMus obtenerFolioAlt(@Param("folioMus") String folioMus,@Param("idService") String idService);
    public boolean actualizarFolioAlt(@Param("folioAlternativo") String folioAlternativo,@Param("folioMus") String folioMus,
            @Param("updateIdUsuario") String updateIdUsuario, @Param("estatus") String estatus,@Param("idService") String idService);
    public FolioAlternativoFolioMus obtenerFolioAlternativoByFolio(@Param("folioAlternativo") String folioAlternativo,@Param("folioMus") String folioMus);
    public boolean eliminarIdFolio(@Param("idFolioAlternativo") String idfolioalternativo);
    public Integer obtenerTamañoFolioAlternativo(@Param("folioMUS")String folioMUS);
}
