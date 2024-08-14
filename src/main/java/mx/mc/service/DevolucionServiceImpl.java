package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.init.Constantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.mc.mapper.DevolucionDetalleMapper;
import mx.mc.mapper.DevolucionMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.model.Devolucion;
import mx.mc.model.DevolucionExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.util.Comunes;

/**
 * 
 * @author gcruz
 *
 */
@Service
public class DevolucionServiceImpl extends GenericCrudServiceImpl<Devolucion, String> implements DevolucionService {
    
    @Autowired
    private DevolucionMapper devolucionMapper;
    
    @Autowired
    private DevolucionDetalleMapper devolucionDetalleMapper;
    
    @Autowired
    private InventarioMapper inventarioMapper;
    
    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    public DevolucionServiceImpl(GenericCrudMapper<Devolucion, String> genericCrudMapper) {
    	super(genericCrudMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public boolean insertarDevolucion(DevolucionExtended devolucionExtended, List<Inventario> listInventario) throws Exception {
    	boolean res = false;
    	try {
            //Obtener el Folio por tipo de documento
            Folios folios = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.DEVOLUVIONES_DE_REABASTO.getValue());
            devolucionExtended.setFolio(Comunes.generaFolio(folios));
            //Actualizar el folio
            folios.setSecuencia(Comunes.separaFolio(devolucionExtended.getFolio()));
            folios.setUpdateFecha(new Date());
            folios.setUpdateIdUsuario(devolucionExtended.getIdUsrDevolucion());
            
            res=foliosMapper.actualizaFolios(folios);
            if(!res) 
                throw new Exception("Ocurrio un error al momento de Actualizar el folio en insertarDevolucion");   
            else{
    		res = devolucionMapper.insertar(devolucionExtended);
    		if(!res) {
                    throw new Exception("Ocurrio un error al momento de insertar la devolucion en insertarDevolucion");
    		} else {    			
                    res = devolucionDetalleMapper.insertListDevolucionDetalle(devolucionExtended.getListDetalleDevolucion());
                    if(!res) {
                        throw new Exception("Ocurrio un error al momento de insertar el detalle de devolucion en insertarDevolucion!!");
                    } else {
                        res = inventarioMapper.actualizaInvByInvInsumoEstr(listInventario);
                        if(!res)
                            throw new Exception("Ocurrio un error al momento de actualizar los inventarios en insertarDevolucion.");    				
                    }
    		}
            }
    	} catch(Exception ex) {
    		throw new Exception("Ocurrio un error al momento de realizar la devolucion en insertarDevolucion!! " +ex.getMessage());
    	}
		
		return res;
	}

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)        
        @Override
    public boolean insertarDevolucionRea(DevolucionExtended devolucionExtended, List<Inventario> listInventario, List<MovimientoInventario> listMovimientos) throws Exception {
        boolean res = false;
            try {
                //Obtener el Folio por tipo de documento
                Folios folios = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.DEVOLUVIONES_DE_REABASTO.getValue());
                devolucionExtended.setFolio(Comunes.generaFolio(folios));
                //Actualizar el folio
                folios.setSecuencia(Comunes.separaFolio(devolucionExtended.getFolio()));
                folios.setUpdateFecha(new Date());
                folios.setUpdateIdUsuario(devolucionExtended.getIdUsrDevolucion());
                for(MovimientoInventario item : listMovimientos){
                    item.setFolioDocumento(devolucionExtended.getFolio());
                }
                
                res = foliosMapper.actualizaFolios(folios);
                if (!res) {
                    throw new Exception("Ocurrio un error al momento de Actualizar el folio en insertarDevolucionRea");
                } else {
                    res = devolucionMapper.insertar(devolucionExtended);
                    if (!res) {
                        throw new Exception("Ocurrio un error al momento de insertar la devolucion en insertarDevolucionRea");
                    } else {
                        res = devolucionDetalleMapper.insertListDevolucionDetalle(devolucionExtended.getListDetalleDevolucion());
                        if (!res) {
                            throw new Exception("Ocurrio un error al momento de insertar el detalle de devolucion en insertarDevolucionRea!!");
                        } else {
                            res = inventarioMapper.actualizaInvByInvInsumoEstr(listInventario);
                            if (!res) {
                                throw new Exception("Ocurrio un error al momento de actualizar los inventarios en insertarDevolucionRea");
                            }else{
                                res = movimientoInventarioMapper.insertarMovimientosInventarios(listMovimientos);
                                if(!res){
                                    throw  new Exception("Ocurrio un Error al guardar los Movimientos de Inventarios en insertarDevolucionRea");
                                }
                            }
                        }
                    }
                }
                
            } catch (Exception e) {
                throw new Exception("Ocurrio un error al momento de realizar la devolucion en insertarDevolucionRea!! " +e.getMessage());                
            }
            return res;
    }
        
	@Override
	public List<DevolucionExtended> obtenerListDevExtended(Devolucion devolucion) throws Exception {
		try {
			return devolucionMapper.obtenerListDevExtended(devolucion);
		} catch (Exception ex) {
			throw new Exception("Error al momento de obtener la lista de devoluciones de la estructura" + ex.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public boolean actualizarDevolucion(DevolucionExtended devolucionExtended) throws Exception {
		boolean res = false;
		try {
			res = devolucionMapper.actualizarEstatusDevolucion(devolucionExtended);
			if (!res) {
				throw new Exception("Ocurrio un error al momento de actualizar la devolucion");
			} else {
				res = devolucionDetalleMapper.actualizarListDetalleDevolucion(devolucionExtended.getListDetalleDevolucion());
				if(!res) {
					throw new Exception("Ocurrio un error al momento de actualizar el detalle devolucion");
				}
			}
		} catch (Exception ex) {
			throw new Exception("Ocurrio un error al actualizar la devolucion!! " + ex.getMessage());
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public boolean insertarDevolucionAndDetalle(DevolucionExtended devolucionExtended) throws Exception {
		boolean res = false;
    	try {
    		res = devolucionMapper.insertar(devolucionExtended);
    		if(!res) {
    			throw new Exception("Ocurrio un error al momento de insertar la devolucion en insertarDevolucionAndDetalle");
    		} else {    			
    			res = devolucionDetalleMapper.insertListDevolucionDetalle(devolucionExtended.getListDetalleDevolucion());
    			if(!res) {
    				throw new Exception("Ocurrio un error al momento de insertar el detalle de devolucion en insertarDevolucionAndDetalle!!");
    			}     			
    		}
    	} catch(Exception ex) {
    		throw new Exception("Ocurrio un error al momento de realizar la devolucion en insertarDevolucionAndDetalle!! " +ex.getMessage());
    	}		
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public boolean ingresarDevolucion(DevolucionExtended devolucionExtended, List<Inventario> listInventarios,
			List<MovimientoInventario> listMovimientoInventario) throws Exception {
            	
            boolean res = false;            
            int tipoDoc = TipoDocumento_Enum.INGRESODEVOLUCIONREABASTO.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);                        
            
            for(MovimientoInventario item : listMovimientoInventario){
                item.setFolioDocumento(Comunes.generaFolio(folio));
            }

        folio.setSecuencia(Comunes.separaFolio(listMovimientoInventario.get(0).getFolioDocumento()));
        
        res = foliosMapper.actualizaFolios(folio);
        if (!res) {
            throw new Exception("Ocurrio un error al actualizar el folio de movimientoInventario");
        } else {
            res = devolucionMapper.actualizarEstatusDevolucion(devolucionExtended);
            if (!res) {
                throw new Exception("Ocurrio un error al cambiar el estatus de devolución a Ingresada!!!");
            } else {
                res = devolucionDetalleMapper.actualizarListDetalleDevolucion(devolucionExtended.getListDetalleDevolucion());
                if (!res) {
                    throw new Exception("Ocurrio un error al actulaizar la lista de detalle devolución!!!");
                } else {
                    if (listInventarios != null){
                        if (!listInventarios.isEmpty()){
                            res = inventarioMapper.actualizaInvByInvInsumoEstr(listInventarios);
                        }
                    }

                    if (!res) {
                        throw new Exception("Ocurrio un error al actualizar el inventario de Devolución!!!");
                    } else {
                        if (listMovimientoInventario!= null){
                            if (!listMovimientoInventario.isEmpty()){
                                res = movimientoInventarioMapper.insertarMovimientosInventarios(listMovimientoInventario);
                                if(!res) {
                                    throw new Exception("Ocurrio un error al insertar movimientos de inventario de ingreso y salida de Devolución!!!");
                                }
                            }
                        }
                    }
                }
            }
            return res;
        }
    }

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)        
        @Override
    public boolean insertarDevolucionInsumoAlmacenServicio(DevolucionExtended devolucionExtended, List<Inventario> listInventario, List<MovimientoInventario> listMovimientos,
            Estructura estrOrigen) throws Exception {
        boolean res = false;
        try {
            //Obtener el Folio por tipo de documento
            Folios folios = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.DEVOLUCION_MANUAL.getValue());
            devolucionExtended.setFolio(Comunes.generaFolio(folios));
            //Actualizar el folio
            folios.setSecuencia(Comunes.separaFolio(devolucionExtended.getFolio()));
            folios.setUpdateFecha(new Date());
            folios.setUpdateIdUsuario(devolucionExtended.getIdUsrDevolucion());
            for (MovimientoInventario item : listMovimientos) {
                item.setFolioDocumento(devolucionExtended.getFolio());
            }

            res = foliosMapper.actualizaFolios(folios);
            if (!res) {
                throw new Exception("Ocurrio un error al momento de Actualizar el folio en insertarDevolucionInsumoAlmacenServicio");
            } else {
                res = devolucionMapper.insertar(devolucionExtended);
                if (!res) {
                    throw new Exception("Ocurrio un error al momento de insertar la devolucion en insertarDevolucionInsumoAlmacenServicio");
                } else {
                    res = devolucionDetalleMapper.insertListDevolucionDetalle(devolucionExtended.getListDetalleDevolucion());
                    if (!res) {
                        throw new Exception("Ocurrio un error al momento de insertar el detalle de devolucion en insertarDevolucionInsumoAlmacenServicio !!");
                    } else {
                        //if (estrOrigen.getIdTipoAreaEstructura().equals(Constantes.TIPO_AREA_ALMACEN)) {
                            if (listInventario.isEmpty()) {
                                res = movimientoInventarioMapper.insertarMovimientosInventarios(listMovimientos);
                                if (!res) {
                                    throw new Exception("Ocurrio un Error al guardar los Movimientos de Inventarios.");
                                }
                            } else {
                                res = inventarioMapper.actualizaInvByInvInsumoEstr(listInventario);
                                if (!res) {
                                    throw new Exception("Ocurrio un error al momento de actualizar los inventarios en insertarDevolucionInsumoAlmacenServicio");
                                } else {
                                    res = movimientoInventarioMapper.insertarMovimientosInventarios(listMovimientos);
                                    if (!res) {
                                        throw new Exception("Ocurrio un Error al guardar los Movimientos de Inventarios.");
                                    }
                                }
                            }
//                        } else {
//                            res = movimientoInventarioMapper.insertarMovimientosInventarios(listMovimientos);
//                            if (!res) {
//                                throw new Exception("Ocurrio un Error al guardar los Movimientos de Inventarios en insertarDevolucionInsumoAlmacenServicio");
//                            }
//                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new Exception("Ocurrio un error al momento de realizar la devolucion en insertarDevolucionInsumoAlmacenServicio!! " + e.getMessage());
        }
        return res;
    }
}
