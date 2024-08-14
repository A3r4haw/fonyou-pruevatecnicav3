package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.EstructuraAlmacenServicioMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.model.EstructuraAlmacenServicio;
import org.primefaces.model.SortOrder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 *
 */
@Service
public class EstructuraAlmacenServicioServiceImpl extends GenericCrudServiceImpl<EstructuraAlmacenServicio, String> implements EstructuraAlmacenServicioService {

    @Autowired
    private EstructuraAlmacenServicioMapper estructuraAlmacenServicioMapper;

    @Autowired
    public EstructuraAlmacenServicioServiceImpl(GenericCrudMapper<EstructuraAlmacenServicio, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public EstrucAlmacenServicio_Extend obtenerEstructuraAsignada(String idEstructuraAlmacen) throws Exception {
        EstrucAlmacenServicio_Extend almacenServicioExtend = new EstrucAlmacenServicio_Extend();
        try {
            almacenServicioExtend = estructuraAlmacenServicioMapper.obtenerEstructuraAsignada(idEstructuraAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener estructura Asignada - " + ex.getMessage());
        }
        return almacenServicioExtend;
    }

    @Override
    public List<EstructuraAlmacenServicio> obtenerAlmacenServicio(String idEstructuraAlmacen) throws Exception {
        List<EstructuraAlmacenServicio> estructuraAlmacenServicio = new ArrayList<>();
        try {
            estructuraAlmacenServicio = estructuraAlmacenServicioMapper.obtenerAlmacenServicio(idEstructuraAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener estructuraAlmacenServicio - " + ex.getMessage());
        }
        return estructuraAlmacenServicio;
    }

    @Override
    public EstrucAlmacenServicio_Extend validarAsignacionServicio(String idEstructuraServicio) throws Exception {
        EstrucAlmacenServicio_Extend almacenServicioExtend = new EstrucAlmacenServicio_Extend();
        try {
            almacenServicioExtend = estructuraAlmacenServicioMapper.validarAsignacionServicio(idEstructuraServicio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la asignación del servicio seleccionado - " + ex.getMessage());
        }
        return almacenServicioExtend;
    }

    @Override
    public EstrucAlmacenServicio_Extend getAlmacenServicioByClaveEstructura(String claveEstructura) throws Exception {
        EstrucAlmacenServicio_Extend almacenServicioExtend = new EstrucAlmacenServicio_Extend();
        try {
            almacenServicioExtend = estructuraAlmacenServicioMapper.getAlmacenServicioByClaveEstructura(claveEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la asignación del servicio seleccionado - " + ex.getMessage());
        }
        return almacenServicioExtend;
    }

    @Override
    public List<EstrucAlmacenServicio_Extend> obtenerAlmacenServicios(String idEntidadHosp, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) throws Exception {
        List<EstrucAlmacenServicio_Extend> almacenesList = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            String valueAlm = null;
            String valueSer = null;
            if (map.size() > 0) {
                if (map.containsKey("almacen")) {
                    valueAlm = map.get("almacen").toString();
                }
                if (map.containsKey("servicio")) {
                    valueSer = map.get("servicio").toString();
                }
            }
            almacenesList = estructuraAlmacenServicioMapper.obtenerAlmacenServicios(idEntidadHosp, startingAt, maxPerPage, valueAlm, valueSer, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la asignación del servicio seleccionado - " + ex.getMessage());
        }
        return almacenesList;
    }

    @Override
    public Long obtenerTotalAlmacenServicios(String idEntidadHosp, Map<String, Object> map) throws Exception {
        Long total = Long.MIN_VALUE;
        String valueAlm = null;
        String valueSer = null;
        try {
            if (map.size() > 0) {
                if (map.containsKey("almacen")) {
                    valueAlm = map.get("almacen").toString();
                }
                if (map.containsKey("servicio")) {
                    valueSer = map.get("servicio").toString();
                }
            }
            total = estructuraAlmacenServicioMapper.obtenerTotalAlmacenServicios(idEntidadHosp, valueAlm, valueSer);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la asignación del servicio seleccionado - " + ex.getMessage());
        }
        return total;
    }

    @Override
    public List<EstrucAlmacenServicio_Extend> obtenerServiciosOfAlmacen(String idAlmacen) throws Exception {
        List<EstrucAlmacenServicio_Extend> lista = new ArrayList<>();
        try {
            lista = estructuraAlmacenServicioMapper.obtenerServiciosOfAlmacen(idAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los servicios del almacén seleccionado - " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public boolean eliminarAlmacenServicioIdAlmacen(String idAlmacen) throws Exception {
        try {
            return estructuraAlmacenServicioMapper.eliminarAlmacenServicioIdAlmacen(idAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar por eliminarAlmacenServicioIdAlmacen - " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarAlmacenServicioList(String idAlmacen, List<EstrucAlmacenServicio_Extend> almacenServicioList) throws Exception {
        try {
            boolean res = true;
            EstructuraAlmacenServicio easTmp = new EstrucAlmacenServicio_Extend();
            easTmp.setIdEstructuraAlmacen(idAlmacen);
            EstructuraAlmacenServicio eas = estructuraAlmacenServicioMapper.obtener(easTmp);
            if (eas != null) {
                res = estructuraAlmacenServicioMapper.eliminarAlmacenServicioIdAlmacen(idAlmacen);
            }
            if (!res) {
                throw new Exception("Error al eliminar por eliminarAlmacenServicioIdAlmacen ");

            } else {
                if (almacenServicioList != null) {
                    if (!almacenServicioList.isEmpty()) {
                        almacenServicioList.forEach((item) -> {
                            Integer prioridad = estructuraAlmacenServicioMapper.obtenerTotalNumeroServicios(item.getIdEstructuraServicio());
//                            if (noServicios != null && noServicios > 0) {
//                                Integer prioridad = (noServicios == 0) ? 1 : noServicios + 1;
                            item.setPrioridadSurtir(prioridad + 1);
//                            }
                        });

                        res = estructuraAlmacenServicioMapper.insertarAlmacenServicioList(almacenServicioList);
                        if (!res) {
                            throw new Exception("Error al eliminar por eliminarAlmacenServicioIdAlmacen ");
                        }
                    }
                }
            }

            return res;
        } catch (Exception ex) {
            throw new Exception("Error al insertar en insertarAlmacenServicioList - " + ex.getMessage());
        }
    }

    @Override
    public List<EstrucAlmacenServicio_Extend> obtenerServiciosXOrdenar() throws Exception {
        try {
            return estructuraAlmacenServicioMapper.obtenerServiciosXOrdenar();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Servicios X Ordenar - " + ex.getMessage());
        }
    }

    @Override
    public List<EstrucAlmacenServicio_Extend> obtenerAlmacenesOfServicio(String idServicio) throws Exception {
        try {
            return estructuraAlmacenServicioMapper.obtenerAlmacenesOfServicio(idServicio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Servicios X Ordenar - " + ex.getMessage());
        }
    }

    @Override
    public boolean guardarPrioridadAlmacenes(List<EstrucAlmacenServicio_Extend> almacenesXOrdenarList) throws Exception {
        try {
            return estructuraAlmacenServicioMapper.guardarPrioridadAlmacenes(almacenesXOrdenarList);
        } catch (Exception ex) {
            throw new Exception("Error al guardar la prioridad del almacen - " + ex.getMessage());
        }
    }

    @Override
    public boolean insertarAlmacenServicioList(List<EstrucAlmacenServicio_Extend> almacenServicioList) throws Exception {
        try {
            return estructuraAlmacenServicioMapper.insertarAlmacenServicioList(almacenServicioList);
        } catch (Exception ex) {
            throw new Exception("Error al guardar la prioridad del almacen - " + ex.getMessage());
        }
    }
}
