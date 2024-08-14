/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.DiaFestivo;
import mx.mc.model.DiaFestivo_Extended;

/**
 *
 * @author bbautista
 */
public interface DiaFestivoService extends GenericCrudService<DiaFestivo, String>{
    
    public List<DiaFestivo> feriadosAnoActual(String anoActul)throws Exception;
    public List<DiaFestivo> topDiasFeriados()throws Exception;
    
    public List<DiaFestivo_Extended> obtenerDiasFeriadosByMes(DiaFestivo_Extended diasFestivoExtended)throws Exception;
    
    public boolean deleteDiaFeriadoDate(String idDia)throws Exception;
    
    public DiaFestivo validaExistenciaFestiva(Date fechaFestiva, int numeroDia) throws Exception;
}
