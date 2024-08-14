/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.mc.enums.EstatusMinistracion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.DevolucionMinistracionDetalleMapper;
import mx.mc.mapper.DevolucionMinistracionMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.SurtimientoMinistradoMapper;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.DevolucionMinistracionDetalle;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.DevolucionMinistracionExtended;
import mx.mc.model.Folios;
import mx.mc.model.Paciente;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.util.Comunes;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class DevolucionMinistracionServiceImpl
        extends GenericCrudServiceImpl<DevolucionMinistracion, String>
        implements DevolucionMinistracionService {

    @Autowired
    private FoliosMapper foliosMapper;

    @Autowired
    private DevolucionMinistracionMapper devolucionMinistracionMapper;
    
    @Autowired
    private SurtimientoMinistradoMapper surtimientoMinistradoMapper;

    @Autowired
    private DevolucionMinistracionDetalleMapper devolucionMinistracionDetalleMapper;

    @Autowired
    public DevolucionMinistracionServiceImpl(GenericCrudMapper<DevolucionMinistracion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<DevolucionMinistracion> obtenerListaDevolucion() throws Exception {
        List<DevolucionMinistracion> devoList = new ArrayList<>();
        try {
            devoList = devolucionMinistracionMapper.obtenerListaDevolucion();
        } catch (Exception ex) {
            throw new Exception("Error al obtener lista de Devolucion: " + ex.getMessage());
        }
        return devoList;
    }

    @Override
    public List<DevolucionMinistracionExtended> obtenerListaDevolucionExtended(
            DevolucionMinistracionExtended devolucionMinistracionExtended) throws Exception {

        List<DevolucionMinistracionExtended> devoList = new ArrayList<>();
        try {
            devoList = devolucionMinistracionMapper.obtenerListaDevolucionExtended(devolucionMinistracionExtended);
        } catch (Exception ex) {
            throw new Exception("Error al obtener lista de DevolucionExtended: " + ex.getMessage());
        }
        return devoList;
    }

    @Override
    public List<DevolucionMinistracion> obtenerBusquedaDevolucion(String cadena) throws Exception {
        List<DevolucionMinistracion> vistaResultado = new ArrayList<>();
        try {
            vistaResultado = devolucionMinistracionMapper.obtenerBusquedaDevolucion(cadena);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda:" + ex.getMessage());
        }
        return vistaResultado;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertDevolucion(DevolucionMinistracion devolucion, List<DevolucionMinistracionDetalle> devolucionList) throws Exception {
        boolean res = false;
        try {
            res = devolucionMinistracionMapper.insertar(devolucion);
            if (res) {
                res = devolucionMinistracionDetalleMapper.insertarLista(devolucionList);

                if (!res) {
                    throw new Exception("Ocurrio un error al momento de insertar el detalle de la devolucion");
                }
            } else {
                throw new Exception("Ocurrio un error al momento de insertar la devolucion");
            }

        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al Guardar la devolución: " + ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizaDevolucion(DevolucionMinistracion devolucion, List<DevolucionMinistracionDetalle> devolucionList) throws Exception {
        boolean res = false;
        try {
            res = devolucionMinistracionMapper.actualizar(devolucion);
            if (res) {
                res = devolucionMinistracionDetalleMapper.actualizarLista(devolucionList);
                if (!res) {
                    throw new Exception("Ocurrio un error al momento de actualizar el detalle de la devolucion");
                }
            } else {
                throw new Exception("Ocurrio un error al momento de actualizar la devolucion");
            }

        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al Actualizar la devolución: " + ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
        return res;
    }

    @Override
    public List<DevolucionMinistracionExtended> obtenerListaDevMedMinistracion(String idEstructura, String cadenaBusqueda, int startingAt, int maxPerPage, List<String> tipo) throws Exception {
        List<DevolucionMinistracionExtended> devoList = new ArrayList<>();
        try {
            devoList = devolucionMinistracionMapper.obtenerListaDevMedMinistracion(idEstructura, cadenaBusqueda, startingAt, maxPerPage, tipo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener obtenerListaDevMedMinistracion: " + ex.getMessage());
        }
        return devoList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertDevolucionExt(DevolucionMinistracionExtended devolucion, List<DevolucionMinistracionDetalleExtended> devolucionExtList) throws Exception {
        boolean res = false;
        try {
            Folios folio = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.DEVOLUCION_MINISTRACION.getValue());
            devolucion.setFolio(Comunes.generaFolio(folio));
            //Agregamos los datos para actualizar el Folio
            folio.setSecuencia(Comunes.separaFolio(devolucion.getFolio()));
            folio.setUpdateFecha(new Date());
            folio.setUpdateIdUsuario(devolucion.getInsertIdUsuario());
            res = foliosMapper.actualizaFolios(folio);
            if (res) {
                res = devolucionMinistracionMapper.insertar(devolucion);

                if (res) {
                    List<DevolucionMinistracionDetalle> devolucionList = new ArrayList<>();
                    List<SurtimientoMinistrado_Extend> surtimientoMinistrado = new ArrayList<>();

                    for (DevolucionMinistracionDetalleExtended detalle : devolucionExtList) {
                        for (SurtimientoEnviado_Extend surte_env : detalle.getSurtimientoEnviadoExtList()) {
                            if (detalle.getIdSurtimientoInsumo().equals(surte_env.getIdSurtimientoInsumo())) {
                                DevolucionMinistracionDetalle devDetalle = new DevolucionMinistracionDetalle();
                                SurtimientoMinistrado_Extend ministrado = new SurtimientoMinistrado_Extend();

                                devDetalle.setIdDevolucionDetalle(Comunes.getUUID());
                                devDetalle.setIdDevolucionMinistracion(devolucion.getIdDevolucionMinistracion());
                                devDetalle.setIdInsumo(surte_env.getIdInsumo());
                                devDetalle.setIdInventario(surte_env.getIdInventarioSurtido());
                                devDetalle.setIdMotivoDevolucion(surte_env.getTipoDevolucion());
                                devDetalle.setCantidad(surte_env.getCantidadDevolver());
                                devDetalle.setConforme(surte_env.isConforme());
                                devDetalle.setIdEstatusDevolucion(devolucion.getIdEstatusDevolucion());
                                devDetalle.setInsertFecha(detalle.getInsertFecha());
                                devDetalle.setInsertIdUsuario(detalle.getInsertIdUsuario());

                                ministrado.setIdSurtimientoEnviado(surte_env.getIdSurtimientoEnviado());
                                ministrado.setLote(surte_env.getLote());
                                ministrado.setCantidad(surte_env.getCantidadDevolver());

                                surtimientoMinistrado.add(ministrado);
                                devolucionList.add(devDetalle);
                            }
                        }
                    }
                    res = devolucionMinistracionDetalleMapper.insertarLista(devolucionList);
                    if (res) {
                        //actualizar los medicamentos pendientes por ministrar ministrado=no ministrado, surtimineto=cancelado                        
                        List<SurtimientoMinistrado_Extend> listSurtimientoMinistrado = new ArrayList<>();

                        for (SurtimientoMinistrado_Extend devol : surtimientoMinistrado) {
                            List<SurtimientoMinistrado_Extend> ministrado = surtimientoMinistradoMapper.obtenerListSurtimientoMinistrado(devol.getIdSurtimientoEnviado());
                            int cantidad = 0;
                            boolean dev=true;
                            for (SurtimientoMinistrado_Extend minis : ministrado) {
                                if (devol.getCantidad() == 0) {
                                    break;
                                }
                                if (devol.getCantidad() > minis.getCantidad()) {
                                    cantidad = devol.getCantidad() - minis.getCantidad();
                                    devol.setCantidad(cantidad);
                                } else if (devol.getCantidad() < minis.getCantidad()) {
                                    minis.setCantidad(minis.getCantidad() - devol.getCantidad());
                                    cantidad = 0;
                                    devol.setCantidad(cantidad);
                                    dev=false;
                                } else {
                                    cantidad = minis.getCantidad() - devol.getCantidad();
                                    devol.setCantidad(cantidad);
                                }
                                if(dev){
                                    minis.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                                    minis.setIdEstatusMinistracion(EstatusMinistracion_Enum.NO_MINISTRADO.getValue());
                                }
                                listSurtimientoMinistrado.add(minis);
                            }
                        }
                        if(!listSurtimientoMinistrado.isEmpty()){
                            res = surtimientoMinistradoMapper.updateSurtMinistradoForDevolucion(listSurtimientoMinistrado);
                            if (!res) {
                                throw new Exception("Ocurrio un error al actualizar la ministración");
                            }
                        }
                    } else {
                        throw new Exception("Ocurrio un error al momento de insertar el detalle de la devolucion");
                    }
                } else {
                    throw new Exception("Ocurrio un error al momento de insertar la devolucion");
                }
            } else {
                throw new Exception("Error al actualizar el Folio");
            }
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error en insertDevolucionExt: " + ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizaDevolucionExt(DevolucionMinistracionExtended devolucion, List<DevolucionMinistracionDetalleExtended> devolucionExtList) throws Exception {
        boolean res = false;
        try {
            res = devolucionMinistracionMapper.actualizar(devolucion);
            if (res) {
                devolucionMinistracionDetalleMapper.eliminarRegistradas(devolucion.getIdDevolucionMinistracion());
                List<DevolucionMinistracionDetalle> devolucionList = new ArrayList<>();
                List<SurtimientoMinistrado_Extend> surtimientoMinistrado = new ArrayList<>();

                for (DevolucionMinistracionDetalleExtended detalle : devolucionExtList) {
                    for (SurtimientoEnviado_Extend surte_env : detalle.getSurtimientoEnviadoExtList()) {
                        if (detalle.getIdSurtimientoInsumo().equals(surte_env.getIdSurtimientoInsumo())) {
                            DevolucionMinistracionDetalle devDetalle = new DevolucionMinistracionDetalle();
                            SurtimientoMinistrado_Extend ministrado = new SurtimientoMinistrado_Extend();

                            devDetalle.setIdDevolucionDetalle(Comunes.getUUID());
                            devDetalle.setIdDevolucionMinistracion(devolucion.getIdDevolucionMinistracion());
                            devDetalle.setIdInsumo(surte_env.getIdInsumo());
                            devDetalle.setIdInventario(surte_env.getIdInventarioSurtido());
                            devDetalle.setIdMotivoDevolucion(surte_env.getTipoDevolucion());
                            devDetalle.setCantidad(surte_env.getCantidadDevolver());
                            devDetalle.setConforme(surte_env.isConforme());
                            devDetalle.setIdEstatusDevolucion(devolucion.getIdEstatusDevolucion());
                            devDetalle.setUpdateFecha(new Date());
                            devDetalle.setUpdateIdUsuario(detalle.getInsertIdUsuario());
                            devDetalle.setInsertFecha(detalle.getInsertFecha());
                            devDetalle.setInsertIdUsuario(detalle.getInsertIdUsuario());

                            ministrado.setIdSurtimientoEnviado(surte_env.getIdSurtimientoEnviado());
                            ministrado.setLote(surte_env.getLote());
                            ministrado.setCantidad(surte_env.getCantidadDevolver());

                            surtimientoMinistrado.add(ministrado);
                            devolucionList.add(devDetalle);
                        }
                    }
                }
                res = devolucionMinistracionDetalleMapper.actualizarLista(devolucionList);
                if (res) {
                    //actualizar los medicamentos pendientes por ministrar ministrado=no ministrado, surtimineto=cancelado                        
                    List<SurtimientoMinistrado_Extend> listSurtimientoMinistrado = new ArrayList<>();

                    for (SurtimientoMinistrado_Extend devol : surtimientoMinistrado) {
                        List<SurtimientoMinistrado_Extend> ministrado = surtimientoMinistradoMapper.obtenerListSurtimientoMinistrado(devol.getIdSurtimientoEnviado());
                        int cantidad = 0;
                        for (SurtimientoMinistrado_Extend minis : ministrado) {
                            if (devol.getCantidad() == 0) {
                                break;
                            }
                            if (devol.getCantidad() > minis.getCantidad()) {
                                cantidad = devol.getCantidad() - minis.getCantidad();
                                devol.setCantidad(cantidad);
                            } else if (devol.getCantidad() < minis.getCantidad()) {
                                minis.setCantidad(minis.getCantidad() - devol.getCantidad());
                                cantidad = 0;
                                devol.setCantidad(cantidad);
                            } else {
                                cantidad = minis.getCantidad() - devol.getCantidad();
                                devol.setCantidad(cantidad);
                            }
                            minis.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                            minis.setIdEstatusMinistracion(EstatusMinistracion_Enum.NO_MINISTRADO.getValue());
                            listSurtimientoMinistrado.add(minis);
                        }
                    }
                    res = surtimientoMinistradoMapper.updateSurtMinistradoForDevolucion(listSurtimientoMinistrado);
                    if (!res) {
                        throw new Exception("Ocurrio un error al actualizar la ministración");
                    }
                } else {
                    throw new Exception("Ocurrio un error al momento de actualizar el detalle de la devolucion");
                }
            } else {
                throw new Exception("Ocurrio un error al momento de actualizar la devolucion");
            }
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error en actualizaDevolucionExt: " + ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
        return res;
    }

    @Override
    public DevolucionMinistracionExtended obtenerByFolioSurtimientoForDev(String folioSurtimiento, Paciente pacienteDev, String filter) throws Exception {
        DevolucionMinistracionExtended devolucion = new DevolucionMinistracionExtended();
        
        String idAlmacen = "";
        if (filter.equalsIgnoreCase("folio")) {
            try {
                idAlmacen = devolucionMinistracionMapper.obteneridEstructuraAlmacenOfSurtimiento(folioSurtimiento);
                devolucion = devolucionMinistracionMapper.obtenerByFolioSurtimientoForDev(folioSurtimiento, idAlmacen, filter);
            } catch (Exception ex) {
                Logger.getLogger(DevolucionMinistracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("No se pudo obtener el almacen del surtimiento por folio");
            }
        } else if (filter.equalsIgnoreCase("paciente")) {
            try {
                String idEstructura = devolucionMinistracionMapper.obtenerAlmacenPadreOfSurtimiento(pacienteDev.getIdEstructura());
                devolucion = devolucionMinistracionMapper.obtenerByFolioSurtimientoForDev(pacienteDev.getNombreCompleto(), idEstructura, filter);
            } catch (Exception ex) {
                Logger.getLogger(DevolucionMinistracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("No se pudo obtener el almacen del surtimiento por paciente");
            }
        }
        
        return devolucion;
    }

    @Override
    public List<DevolucionMinistracionExtended> obtenerByFolioSurtimientoForDevList(String folioSurtimiento, Paciente pacienteDev, String filter) throws Exception {
        List<DevolucionMinistracionExtended> devolucion = new ArrayList<>();
        
        String idAlmacen = "";
        if (filter.equalsIgnoreCase("folio")) {
            try {
                idAlmacen = devolucionMinistracionMapper.obteneridEstructuraAlmacenOfSurtimiento(folioSurtimiento);
                String idEstructura = devolucionMinistracionMapper.obtenerAlmacenPadreOfSurtimiento(idAlmacen);
                devolucion = devolucionMinistracionMapper.obtenerByFolioSurtimientoForDevList(folioSurtimiento, idEstructura, filter);
            } catch (Exception ex) {
                Logger.getLogger(DevolucionMinistracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("No se pudo obtener el almacen del surtimiento por folio");
            }
        } else if (filter.equalsIgnoreCase("paciente")) {
            try {
                devolucion = devolucionMinistracionMapper.obtenerByFolioSurtimientoForDevList(pacienteDev.getNombreCompleto(), pacienteDev.getIdEstructura(), filter);
            } catch (Exception ex) {
                Logger.getLogger(DevolucionMinistracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("No se pudo obtener el almacen del surtimiento por paciente");
            }
        }
        
        return devolucion;
    }
    
    @Override
    public List<DevolucionMinistracionExtended> obtenerBusquedaDevolucionLazy(String cadena, String almacen, int startingAt,int maxPerPage, String sortField, SortOrder sortOrder) throws Exception{
        List<DevolucionMinistracionExtended> devolucionMinisList = new ArrayList<>();
        try{            
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            devolucionMinisList = devolucionMinistracionMapper.obtenerBusquedaDevolucionLazy(cadena, almacen, startingAt, maxPerPage, sortField,order);
        }catch(Exception ex){
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return devolucionMinisList;
    }
    
    @Override
    public Long obtenerBusquedaDevolucionTotalLazy(String cadena, String almacen) throws Exception {
        try{
            return devolucionMinistracionMapper.obtenerBusquedaDevolucionTotalLazy(cadena,almacen);
        }catch(Exception ex){
            throw new Exception("Error al obtener el total de la busqueda" + ex.getMessage());
        }
    }
}
