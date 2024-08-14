/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.init.Constantes;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.NutricionParenteralMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.SolucionMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.model.Folios;
import mx.mc.model.NutricionParenteral;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.util.Comunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class SolucionServiceImpl extends GenericCrudServiceImpl<Solucion, String> implements SolucionService {

    @Autowired
    SolucionMapper solucionMapper;

    @Autowired
    SurtimientoMapper surtimientoMapper;

    @Autowired
    SurtimientoInsumoMapper surtimientoInsumoMapper;

    @Autowired
    PrescripcionMapper prescripcionMapper;

    @Autowired
    PrescripcionInsumoMapper prescripcionInsumoMapper;

    @Autowired
    SurtimientoEnviadoMapper surtimientoEnviadoMapper;
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    NutricionParenteralMapper nutricionParenteralMapper;
    
//    @Autowired
//    NutricionParenteralDetalleMapper nutricionParenteralDetalleMapper;

    @Autowired
    public SolucionServiceImpl(GenericCrudMapper<Solucion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizaSolucion(Solucion solucion, Surtimiento surtimiento, List<SurtimientoInsumo> surInsumoLista, List<SurtimientoEnviado> surEnviadoLista) throws Exception {
        boolean res = Constantes.INACTIVO;
        try {
            res = surtimientoMapper.actualizar(surtimiento);
            if (res) {
                res = solucionMapper.actualizar(solucion);
                if (res) {
                    if (surInsumoLista != null) {
                        if (!surInsumoLista.isEmpty()) {
                            res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surInsumoLista);
                        }
                    }
                    if (res) {
                        if (surEnviadoLista != null) {
                            if (!surEnviadoLista.isEmpty()) {
                                res = surtimientoEnviadoMapper.actualizaCantidadRecibidaList(surEnviadoLista);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al actualizaSolucion. " + ex.getMessage());
        }
        return res;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean mezclaRechazoYReproceso(Solucion solucion, Surtimiento surtimiento, List<SurtimientoInsumo> surInsumoLista, List<SurtimientoEnviado> surEnviadoLista) throws Exception {
        boolean res = Constantes.INACTIVO;
        try {
            res = surtimientoMapper.actualizar(surtimiento);
            if (res) {
                res = solucionMapper.actualizar(solucion);
                if (res) {
                    if (surInsumoLista != null) {
                        if (!surInsumoLista.isEmpty()) {
                            res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surInsumoLista);
                        }
                    }
                    if (res) {
                        if (surEnviadoLista != null) {
                            if (!surEnviadoLista.isEmpty()) {
                                res = surtimientoEnviadoMapper.actualizaCantidadRecibidaList(surEnviadoLista);
                            }
                        }
                    }
                    if (res){
                        int tipoDoc = TipoDocumento_Enum.ORDENPREPARACIONMEZCLA.getValue();
                        Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                        surtimiento.setFolio(Comunes.generaFolio(folio));
                        folio.setSecuencia(Comunes.separaFolio(surtimiento.getFolio()));
                        res = foliosMapper.actualizaFolios(folio);
                        
                        if (res){
                            surtimiento.setIdSurtimiento(Comunes.getUUID());
                            surtimiento.setFolio(Comunes.generaFolio(folio));
                            surtimiento.setFechaProgramada(new java.util.Date());
                            surtimiento.setInsertFecha(new java.util.Date());
                            surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtimiento.setUpdateFecha(null);
                            surtimiento.setUpdateIdUsuario(null);
                            surtimiento.setComentario(null);
                            res = surtimientoMapper.insertar(surtimiento);
                            if (res){
                                for (SurtimientoInsumo item : surInsumoLista){
                                    item.setIdSurtimientoInsumo(Comunes.getUUID());
                                    item.setIdSurtimiento(surtimiento.getIdSurtimiento());
                                    item.setInsertFecha(new java.util.Date());
                                    item.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                    item.setUpdateFecha(null);
                                    item.setUpdateIdUsuario(null);
                                    item.setCantidadEnviada(0);
                                    item.setCantidadRecepcion(0);
                                    item.setTotalEnviado(0);
                                }
                                res = surtimientoInsumoMapper.registraSurtimientoInsumoList(surInsumoLista);
                                if (res){
                                    solucion.setIdSolucion(Comunes.getUUID());
                                    solucion.setIdSurtimiento(surtimiento.getIdSurtimiento());
                                    solucion.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                                    solucion.setInsertFecha(new java.util.Date());
                                    
                                    solucion.setIdMotivoCancelacion(null);
                                    solucion.setFechaValida(null);
                                    solucion.setIdUsuarioValida(null);
                                    solucion.setComentValOrdenPrep(null);
                                    
                                    solucion.setFechaValPrescr(null);
                                    solucion.setIdUsuarioValPrescr(null);
                                    solucion.setComentValPrescr(null);
                                                                                                            
                                    solucion.setFechaValOrdenPrep(null);
                                    solucion.setIdUsuarioValOrdenPrep(null);
                                    solucion.setComentValOrdenPrep("Orden generada debido a cancelación. ");
                                    
                                    solucion.setFechaPrepara(null);
                                    solucion.setIdUsuarioPrepara(null);
                                    solucion.setComentariosPreparacion(null);
                                                                        
                                    solucion.setIdMotivoCancelacion(tipoDoc);
                                    res = solucionMapper.insertar(solucion);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al actualizaSolucion. " + ex.getMessage());
        }
        return res;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean mezclaIncorrecta(Solucion solucion, Prescripcion prescripcion, List<PrescripcionInsumo> listaPrescripcionInsumo,
            Surtimiento surtimiento, List<SurtimientoInsumo> listaSurtimientoInsumo) throws Exception {
        boolean resp = Constantes.INACTIVO;
        try {
            resp = prescripcionMapper.actualizar(prescripcion);
            if (resp) {
                resp = prescripcionInsumoMapper.actualizarListaPrescripcionInsumo(listaPrescripcionInsumo);
                if (resp) {
                    resp = surtimientoMapper.insertar(surtimiento);
                    if (resp) {
                        resp = surtimientoInsumoMapper.registraSurtimientoInsumoList(listaSurtimientoInsumo);
                        if (resp) {
                            resp = solucionMapper.actualizar(solucion);
                            if (!resp) {
                                throw new Exception("Error al actualizar la solucion ");
                            }
                        } else {
                            throw new Exception("Error al registrar los surtimientoInsumos ");
                        }
                    } else {
                        throw new Exception("Error al registrar el surtimiento ");
                    }
                } else {
                    throw new Exception("Error al actualiar las prescripcionesInsumos ");
                }
            } else {
                throw new Exception("Error al actualizar la prescripcion  ");
            }
        } catch (Exception ex) {
            throw new Exception("Error en metodo de mezclaIncorrecta, generando nuevos surtimientos:  " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public SolucionExtended obtenerDatosSolucionByIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return solucionMapper.obtenerDatosSolucionByIdSurtimiento(idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Error al buscar datos de solución para reporte por idSurtimiento:  " + ex.getMessage());
        }
    }

    @Override
    public List<SolucionExtended> obtenerAutoCompSolucion(String nombre, String idEstructura) throws Exception {
        try {
            return solucionMapper.obtenerAutoCompSolucion(nombre, idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de soluciones: " + ex.getMessage());
        }
    }

    @Override
    public SolucionExtended obtenerSolucion(String idSolucion, String idSurtimiento) throws Exception {
        try {
            return solucionMapper.obtenerSolucion(idSolucion, idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la solucion: " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean rechazarMezcla(Solucion solucion, Surtimiento surtimiento, List<SurtimientoInsumo_Extend> listaSurtimientoInsumo, boolean agruparSolucionesXPrescripcion) throws Exception {
        boolean resp = Constantes.INACTIVO;
        try {
            if(agruparSolucionesXPrescripcion) {
                //TODO GCR.- Esta parte se genera aunque por el momento no se actualiza en BD, se revisara si es necesario
                List<Surtimiento> listSurtimientos = surtimientoMapper.obtenerSurtimientosByIdPrescripcion(surtimiento.getIdPrescripcion());
                for(Surtimiento surt : listSurtimientos) {
                    surt.setUpdateFecha(surtimiento.getUpdateFecha());
                    surt.setUpdateIdUsuario(surtimiento.getUpdateIdUsuario());
                    surt.setIdEstatusSurtimiento(surtimiento.getIdEstatusSurtimiento());
                }
                List<SurtimientoInsumo> listSurtimientoInsumos = surtimientoInsumoMapper.obtenerSurtimientosInsumos(surtimiento.getIdPrescripcion());
                for(SurtimientoInsumo surtIns : listSurtimientoInsumos) {
                    for(SurtimientoInsumo si : listaSurtimientoInsumo) {
                        if(surtIns.getIdPrescripcionInsumo().equals(si.getIdPrescripcionInsumo())) {
                            surtIns.setIdSurtimientoInsumo(si.getIdSurtimientoInsumo());
                            surtIns.setIdSurtimiento(si.getIdSurtimiento());
                            surtIns.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtIns.setUpdateFecha(new java.util.Date());
                            surtIns.setUpdateIdUsuario(solucion.getIdUsuarioValOrdenPrep());
                        }
                    }
                }
                List<Solucion> listaSoluciones = solucionMapper.obtenerSolucionesByIdPrescripcion(surtimiento.getIdPrescripcion());
                for(Solucion so : listaSoluciones) {
                    so.setUpdateFecha(solucion.getUpdateFecha());
                    so.setUpdateIdUsuario(solucion.getIdUsuarioNoAutoriza());
                    so.setFechaValPrescr(solucion.getFechaValPrescr());
                    so.setIdEstatusSolucion(solucion.getIdEstatusSolucion());
                    so.setIdUsuarioNoAutoriza(solucion.getIdUsuarioNoAutoriza());
                    so.setMotivoNoAutoriza(solucion.getMotivoNoAutoriza());
                    so.setComentariosRechazo(solucion.getComentariosRechazo());
                }
                
                resp = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(listSurtimientoInsumos);
                if (!resp) {
                    throw new Exception("Error al actualizar los surtimientos insumo ");
                } else {
                    resp = solucionMapper.actualizarListaSoluciones(listaSoluciones);
                    if (!resp) {
                        throw new Exception("Error al actualizar las soluciones ");
                    }
                }
                
            } else {
                resp = surtimientoMapper.actualizar(surtimiento);
                if (!resp) {
                    throw new Exception("Error al actualizar el surtimiento ");
                } else {
                    resp = solucionMapper.actualizar(solucion);
                    if (!resp) {
                        throw new Exception("Error al actualizar la solucion ");
                    } else {
                        NutricionParenteral np = new NutricionParenteralExtended();
                        np.setIdSurtimiento(surtimiento.getIdSurtimiento());
                        np = nutricionParenteralMapper.obtener(np);
                        if (np != null) {
                            String idNutricionParenteral = np.getIdNutricionParenteral();
                            if (idNutricionParenteral != null) {
                                np = new NutricionParenteralExtended();
                                np.setIdNutricionParenteral(idNutricionParenteral);
                                np.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue());
                                np.setUpdateFecha(new java.util.Date());
                                np.setUpdateIdUsuario(solucion.getUpdateIdUsuario());
                                resp = nutricionParenteralMapper.actualizar(np);
                                if (!resp) {
                                    throw new Exception("Error al actualizar la solucion ");
                                }
                            }
                        }
                        
                        
                        List<SurtimientoInsumo> items = new ArrayList<>();
                        for (SurtimientoInsumo_Extend insumo : listaSurtimientoInsumo) {
                            SurtimientoInsumo item = new SurtimientoInsumo();
                            item.setIdSurtimientoInsumo(insumo.getIdSurtimientoInsumo());
                            item.setIdSurtimiento(insumo.getIdSurtimiento());
                            item.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            item.setUpdateFecha(new java.util.Date());
                            item.setUpdateIdUsuario(solucion.getIdUsuarioValOrdenPrep());
                            items.add(item);
                        }
                        resp = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(items);
                        if (!resp) {
                            throw new Exception("Error al actualizar la solucion ");
                        }
                    }
                }
            }            
        } catch (Exception ex) {
            throw new Exception("Error en metodo de mezclaIncorrecta, generando nuevos surtimientos:  " + ex.getMessage());
        }
        return resp;
    }
    
    @Override
    public Integer obtenerNumeroSolucionesProcesadas(String idPrescripcion) throws Exception{
        try {
            return solucionMapper.obtenerNumeroSolucionesProcesadas(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al obtener numero de soluciones procesadas: " + ex.getMessage());
        }
    }
    
    @Override
    public String obtenerDescripcionMezcla(String idSurtimiento) throws Exception{
        try {
            return solucionMapper.obtenerDescripcionMezcla(idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener descripción de mezcla :: " + ex.getMessage());
        }
    }

}
