package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoArchivoMapper;
import mx.mc.model.TipoArchivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class TipoArchivoServiceImpl extends GenericCrudServiceImpl<TipoArchivo, String> implements TipoArchivoService {

    @Autowired
    private TipoArchivoMapper tipoArchivoMapper;

    @Autowired
    public TipoArchivoServiceImpl(GenericCrudMapper<TipoArchivo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<TipoArchivo> obtenerListaActivos() throws Exception {
        try {
            return tipoArchivoMapper.obtenerListaActivos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Lista de tipos de Archivo: " + ex.getMessage());
        }
    }

}
