package mx.mc.service;

import java.util.List;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.NotaDispenColectDetalleMapper;
import mx.mc.model.NotaDispenColect;
import org.springframework.beans.factory.annotation.Autowired;
import mx.mc.mapper.NotaDispenColectMapper;
import mx.mc.mapper.SolucionMapper;
import mx.mc.model.Folios;
import mx.mc.model.NotaDispenColectDetalle_Extended;
import mx.mc.model.NotaDispenColect_Extended;
import mx.mc.model.Solucion;
import mx.mc.util.Comunes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cervanets
 */
@Service
public class NotaDispenColectServiceImpl extends GenericCrudServiceImpl<NotaDispenColect, String> implements NotaDispenColectService {

    @Autowired
    private NotaDispenColectMapper notaDispenColectMapper;
    @Autowired
    private NotaDispenColectDetalleMapper notaDispenColectDetalleMapper;
    @Autowired
    private FoliosMapper foliosMapper;
    @Autowired
    private SolucionMapper solucionMapper;
    
    public NotaDispenColectServiceImpl(GenericCrudMapper<NotaDispenColect, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarNotaDispenColect(NotaDispenColect_Extended ndc, List<NotaDispenColectDetalle_Extended> ndcl) throws Exception {
        boolean res = false;
        try {
            int tipoDoc = TipoDocumento_Enum.NOTA_DISPEN_COLECT.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            String folioDoc = Comunes.generaFolio(folio);
            
            folio.setSecuencia(Comunes.separaFolio(folioDoc));
            res = foliosMapper.actualizaFolios(folio);
            if (!res) {
                throw new Exception("Error al actualizar el folio de la lista de distribución. " );
                
            } else {
                ndc.setFolio(folioDoc);
                res = notaDispenColectMapper.insertar(ndc);
                if (!res){
                    throw new Exception("Error registrar nota de dispensacion colectiva. " );
                } else {
                    for (NotaDispenColectDetalle_Extended item : ndcl) {
                        String idSolucion = null;
                        String idSurtimiento = item.getIdSurtimiento();
                        Solucion s = (Solucion) solucionMapper.obtenerSolucion(idSolucion, idSurtimiento);
                        s.setIdEstatusSolucion(EstatusSolucion_Enum.MEZCLA_EN_DISTRIBUCIÓN.getValue());
                        s.setUpdateFecha(new java.util.Date());
                        s.setUpdateIdUsuario(ndc.getInsertIdUsuario());
                        
                        res = solucionMapper.actualizar(s);
                        if (!res) {
                            throw new Exception("Error actualizar estatus de lista de soluciones. ");
                        }
                        item.setIdSolucion(s.getIdSolucion());
                        item.setIdEstatusDispenColect(ndc.getIdEstatusDispenColect());
                    }
                    if (!res){
                        throw new Exception("Error registrar lista de mezclas de nota de dispensacion colectiva. " );
                    } else {
                        res = notaDispenColectDetalleMapper.registrarLista(ndcl);
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error Registrar Lista de Notas dispensacion colectiva. " + ex.getMessage());
        }
        return res;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarNotaDispenColect(NotaDispenColect_Extended ndc, List<NotaDispenColectDetalle_Extended> ndcl) throws Exception{
        boolean res = false;
        try {
            res = notaDispenColectMapper.actualizar(ndc);
            if (!res){
                throw new Exception("Error actualizar nota de dispensacion colectiva. " );
            } else {
//                res = notaDispenColectDetalleMapper.actualizarLista(ndcl);
                res = notaDispenColectDetalleMapper.eliminarLista(ndc.getIdNotaDispenColect());
                if (!res){
                    throw new Exception("Error actualizar lista de mezclas de nota de dispensacion colectiva. " );
                } else {
                    res = notaDispenColectDetalleMapper.registrarLista(ndcl);
                }
            }
            
        } catch (Exception ex) {
            throw new Exception("Error actualizar Lista de Notas dispensacion colectiva. " + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<NotaDispenColect_Extended>obtenerListaNotas(NotaDispenColect_Extended ndc) throws Exception{
        try {
            return notaDispenColectMapper.obtenerListaNotas(ndc);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Lista de Notas dispensacion colectiva. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarNotaDispenColect(NotaDispenColect_Extended ndc, List<NotaDispenColectDetalle_Extended> ndcl) throws Exception{
        boolean res = false;
        try {
            res = notaDispenColectMapper.actualizar(ndc);
            if (!res){
                throw new Exception("Error al cancelar nota de dispensacion colectiva. " );
            } else {
                res = notaDispenColectDetalleMapper.actualizarLista(ndcl);
                if (!res){
                    throw new Exception("Error al cancelar lista de mezclas de nota de dispensacion colectiva. " );
                } else {
                    if (ndcl!= null){
                        if (!ndcl.isEmpty()){
                            for (NotaDispenColectDetalle_Extended item : ndcl ){
                                Solucion s = new Solucion();
                                s.setIdSolucion(item.getIdSolucion());
                                s.setIdEstatusSolucion(EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue());
                                s.setUpdateFecha(new java.util.Date());
                                s.setUpdateIdUsuario(ndc.getUpdateIdUsuario());
                                res = solucionMapper.actualizarEstatus(s);
                                if (!res){
                                    throw new Exception("Error al cancelar Actualizar mezcla de nota de dispensacion colectiva cancelada. " );
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
            throw new Exception("Error actualizar Lista de Notas dispensacion colectiva. " + ex.getMessage());
        }
        return res;
    }
    
}
