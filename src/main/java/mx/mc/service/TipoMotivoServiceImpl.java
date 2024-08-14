/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;

import mx.mc.model.TipoMotivo;
import mx.mc.mapper.TipoMotivoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author bbautista
 */
@Service
public class TipoMotivoServiceImpl extends GenericCrudServiceImpl<TipoMotivo, String> implements TipoMotivoService {
    
    @Autowired
    private TipoMotivoMapper tipoMotivoMapper;

    @Autowired
    public TipoMotivoServiceImpl(GenericCrudMapper<TipoMotivo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<TipoMotivo> listaAjusteInventario() throws Exception {
        List<TipoMotivo> tmo = new ArrayList<>();
        try{
            tmo= tipoMotivoMapper.listaAjusteInventario();
        }catch(Exception ex){
            throw new Exception("Error obtener Motivos AjusteInventario. " + ex.getMessage());
        }
        return tmo;
    }

	@Override
	public List<TipoMotivo> getListaByIdTipoMovimiento(int idTipoMovimiento) throws Exception {
		List<TipoMotivo> listTipoMotivos = new ArrayList<>();
        try{
        	listTipoMotivos = tipoMotivoMapper.getListaByIdTipoMovimiento(idTipoMovimiento);
        } catch(Exception ex) {
        	throw new Exception("Error obtener Motivos por idTipoMovimiento!! " + ex.getMessage());
        }
		return listTipoMotivos;
	}

    @Override
    public List<TipoMotivo> listaDevolucionEnPrescripcion() throws Exception {
        List<TipoMotivo> tmo = new ArrayList<>();
        try{
            tmo=tipoMotivoMapper.listaMotivoDevolucion();
        }catch(Exception ex){
            throw new Exception("No se pudo obtener la lista Devoución:"+ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
        return tmo;
    }
        
    @Override
    public List<TipoMotivo> obtieneListaMotivosActivos(int activo) throws Exception {
        List<TipoMotivo> tipoMotivoListActivos = new ArrayList<>();
        try {
            tipoMotivoListActivos = tipoMotivoMapper.obtieneListaMotivosActivos(activo);

        } catch (Exception ex) {
            throw new Exception("Error al obtener Bloqueoso!! " + ex.getMessage());
        }
        return tipoMotivoListActivos;
    }

    @Override
    public List<TipoMotivo> obtieneListaMotivosActivosEntrada() throws Exception {
        List<TipoMotivo> tipoMotivoListActivosEntrada = new ArrayList<>();
        try {
            tipoMotivoListActivosEntrada = tipoMotivoMapper.obtieneListaMotivosActivosEntrada();
        } catch (Exception e) {
            throw new Exception("Error al obtener Motivos Entrada!! " + e.getMessage());
        }
        return tipoMotivoListActivosEntrada;
    }    
}
