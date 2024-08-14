package mx.mc.service;

import java.util.Arrays;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.AdjuntoMapper;
import mx.mc.mapper.FichaPrevencionAdjuntoMapper;
import mx.mc.mapper.FichaPrevencionMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.FichaPrevencion;
import mx.mc.model.FichaPrevencionAdjunto;
import mx.mc.model.FichaPrevencionExtended;
import mx.mc.model.Folios;
import mx.mc.util.Comunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cervanets
 */
@Service
public class FichaPrevencionServiceImpl extends GenericCrudServiceImpl<FichaPrevencion, String> implements FichaPrevencionService {

    @Autowired
    private FichaPrevencionMapper fichateMapper;

    @Autowired
    private AdjuntoMapper adjuntoMapper;

    @Autowired
    private FichaPrevencionAdjuntoMapper fichaPrevencionAdjuntoMapper;

    @Autowired
    private FoliosMapper foliosMapper;

    @Autowired
    public FichaPrevencionServiceImpl(GenericCrudMapper<FichaPrevencion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<FichaPrevencionExtended> obtenerListaFichas(FichaPrevencionExtended fpe) throws Exception {
        try {
            return fichateMapper.obtenerListaFichas(fpe);
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de fichas de prevencion " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertaFichaConAdjuntos(FichaPrevencion fp, List<AdjuntoExtended> adjuntosLista) throws Exception {
        boolean resp = true;
        
        try {
            Integer tipoDoc = TipoDocumento_Enum.FICHAPREVENCIONCONTAMINACION.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            fp.setFolio(Comunes.generaFolio(folio));
            folio.setSecuencia(Comunes.separaFolio(fp.getFolio()));
            folio.setUpdateFecha(new java.util.Date());
            folio.setUpdateIdUsuario(fp.getInsertIdUsuario());

            resp = foliosMapper.actualizaFolios(folio);

            if (!resp) {
                throw new Exception("Error al Actualizar folio de la ficha de prevencion de contaminación. ");

            } else {
                resp = fichateMapper.insertar(fp);

                if (!resp) {
                    throw new Exception("Error al registrar la ficha de prevencion de contaminación. ");

                } else {

                    if (adjuntosLista != null) {
                        for (AdjuntoExtended adj : adjuntosLista) {
                            if (adj!= null) {
                                Integer idAdjunto = adjuntoMapper.obtenerSiguienteAdjunto();
                                adj.setIdAdjunto(idAdjunto);
                                resp = adjuntoMapper.insertar(adj);
                                if (!resp) {
                                    throw new Exception("Error al registrar la lista de adjuntos de la ficha de prevencion de contaminación. ");

                                } else {
                                    FichaPrevencionAdjunto fpa = new FichaPrevencionAdjunto();
                                    fpa.setIdAdjunto(adj.getIdAdjunto());
                                    fpa.setIdFichaPrevencion(fp.getIdPrevencion());

                                    resp = fichaPrevencionAdjuntoMapper.insertar(fpa);
                                    if (!resp) {
                                        throw new Exception("Error al registrar la relación de la lista de adjuntos de la ficha de prevencion de contaminación. ");

                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de fichas de prevencion " + e.getMessage());
        }
        return resp;
    }

    @Override
    public FichaPrevencionExtended obtenerFicha(FichaPrevencionExtended fpe) throws Exception {
        try {
            return fichateMapper.obtenerFicha(fpe);
        } catch (Exception e) {
            throw new Exception("Error al obtener la ficha de prevencion " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizaFichaConAdjuntos(FichaPrevencion fp, List<AdjuntoExtended> adjuntosLista) throws Exception {
        boolean resp = true;
        
        try {
            resp = fichateMapper.actualizar(fp);

            if (!resp) {
                throw new Exception("Error al registrar la ficha de prevencion de contaminación. ");

            } else {

                if (adjuntosLista != null) {
                    for (AdjuntoExtended adj : adjuntosLista) {

                        boolean insertar = false;
                        
                        if (adj.getIdAdjunto() == 0){
                            Integer idAdjunto = adjuntoMapper.obtenerSiguienteAdjunto();
                            adj.setIdAdjunto(idAdjunto);
                            insertar = true;
                            
                        } else {
                            Integer noRegistrosEncontrados = adjuntoMapper.obtenerNoRegistrosEncontrados(adj.getIdAdjunto());
                            insertar = (noRegistrosEncontrados == 0);
                            
                        }

                        if (insertar) {
                            
                            
                            resp = adjuntoMapper.insertar(adj);
                            
                            if (!resp) {
                                throw new Exception("Error al registrar la lista de adjuntos de la ficha de prevencion de contaminación. ");

                            } else {
                                FichaPrevencionAdjunto fpa = new FichaPrevencionAdjunto();
                                fpa.setIdAdjunto(adj.getIdAdjunto());
                                fpa.setIdFichaPrevencion(fp.getIdPrevencion());

                                resp = fichaPrevencionAdjuntoMapper.insertar(fpa);
                                if (!resp) {
                                    throw new Exception("Error al registrar la relación de la lista de adjuntos de la ficha de prevencion de contaminación. ");

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de fichas de prevencion " + e.getMessage());
        }
        return resp;
    }
    

}
