/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.DiaFestivo;
import mx.mc.model.DiaFestivo_Extended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.DiaFestivoMapper;

/**
 *
 * @author bbautista
 */
@Service
public class DiaFestivoServiceImpl extends GenericCrudServiceImpl<DiaFestivo, String> implements DiaFestivoService{
    
    @Autowired
    private DiaFestivoMapper feriadosMapper;
    
    @Autowired
    public DiaFestivoServiceImpl(GenericCrudMapper<DiaFestivo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<DiaFestivo> feriadosAnoActual(String anoActul) throws Exception {
        try{
            return feriadosMapper.feriadosAnoActual(anoActul);
        }catch(Exception ex){
            throw new Exception("No se pudo obtener la lista de Dias Feriados: "+ex.getMessage());
        }
    }

    @Override
    public List<DiaFestivo> topDiasFeriados() throws Exception {
        try{
            return feriadosMapper.topDiasFeriados();
        }catch(Exception ex){
            throw new Exception("No se pudo obtener la lista top dias feriados: "+ex.getMessage());
        }
    }

    @Override
    public List<DiaFestivo_Extended> obtenerDiasFeriadosByMes(DiaFestivo_Extended diasFestivoExtended) throws Exception {
        try {
            return feriadosMapper.obtenerDiasFeriadosByMes(diasFestivoExtended);
        } catch (Exception e) {
            throw new Exception("No se pudo obtener la lista de dias feriados por Mes "+e.getMessage());
        }
    }

    @Override
    public boolean deleteDiaFeriadoDate(String idDia) throws Exception {
        try {
            return feriadosMapper.deleteDiaFeriadoDate(idDia);
        } catch (Exception e) {
            throw new Exception("Error al Eliminar el dia Festivo "+e.getMessage());
        }
    }

    @Override
    public DiaFestivo_Extended validaExistenciaFestiva(Date fechaFestiva, int numeroDia) throws Exception {
        try {
            return feriadosMapper.validaExistenciaFestiva(fechaFestiva,numeroDia);
        } catch (Exception e) {
            throw new Exception("Error al Eliminar el dia Festivo "+e.getMessage());
        }
    }
    
    
}
