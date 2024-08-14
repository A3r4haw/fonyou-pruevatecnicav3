/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.ClaveProveedorBarrasMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.ReabastoEnviadoMapper;
import mx.mc.mapper.ReabastoInsumoMapper;
import mx.mc.mapper.ReabastoMapper;
import mx.mc.mapper.ReabastoRecepcionMapper;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.Folios;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoRecepcion;
import mx.mc.util.Comunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class ReabastoAutomaticoServiceImpl extends GenericCrudServiceImpl<ClaveProveedorBarras_Extend, String> implements ReabastoAutomaticoService {
    
    @Autowired
    private ReabastoRecepcionMapper reabastoRecepcionMapper;
    
    @Autowired
    private ClaveProveedorBarrasMapper claveProveedorBarrasMapper;
    
    @Autowired
    private ReabastoMapper reabastoMapper;        
    
    @Autowired
    private ReabastoInsumoMapper reabastoInsumoMapper;
    
    @Autowired
    private ReabastoEnviadoMapper reabastoEnviadoMapper;
    
    @Autowired
    private FoliosMapper foliosMapper;
   
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean recibirReabastoAutomatico(List<ReabastoRecepcion> listReabastoRecepcion, List<ClaveProveedorBarras> listClaveProveedorBarras,
            Reabasto unReabasto, List<ReabastoInsumo> listReabastoInsumo, List<ReabastoEnviado> listReabastoEnviado, List<ClaveProveedorBarras> listClaveProveedorExiste) throws Exception {
        boolean res = false;
        try {
            if (unReabasto.getFolio()== null || unReabasto.getFolio().isEmpty()) {
                int tipoDoc = TipoDocumento_Enum.ORDEN_REABASTO.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);

                unReabasto.setFolio(Comunes.generaFolio(folio));
                folio.setSecuencia(Comunes.separaFolio(unReabasto.getFolio()));
                res = foliosMapper.actualizaFolios(folio);

                if (!res) {
                    throw new Exception("Error al registrar Folio en la orden de reabasto manual. ");
                }
            }
            
            res = reabastoMapper.insertar(unReabasto);
            if (!res) {
                throw new Exception("Ocurrio un error al insertar la orden de reabasto");
            } else {
                res = reabastoInsumoMapper.insertarListReabastoInsumos(listReabastoInsumo);
                if (!res) {
                    throw new Exception("Ocurrio un error al insertar la lista de insumos de reabasto");
                } else {
                    res = reabastoEnviadoMapper.insertListReabatoEnviado(listReabastoEnviado);
                    if (!res) {
                        throw new Exception("Ocurrio un error al insertar la lista de insumos enviado de reabasto");
                    } else {
                        res = reabastoRecepcionMapper.insertarListaReabastoRecepcion(listReabastoRecepcion);
                        if (!res) {
                            throw new Exception("Ocurrio un error al insertar la lista de recepcion de reabasto");
                        } else {
                            if (!listClaveProveedorBarras.isEmpty()) {
                                res = claveProveedorBarrasMapper.insertarListClaveProvedorBarras(listClaveProveedorBarras);
                                if (!res) {
                                    throw new Exception("Ocurrio un error al insertar la lista de claveProveedorBarras de reabasto");
                                }
                            } else if (!listClaveProveedorExiste.isEmpty()) {
                                res = claveProveedorBarrasMapper.updateListClaveProveedorBarras(listClaveProveedorExiste);
                                if (!res) {
                                    throw new Exception("Ocurrio un error al actualizar la lista de claveProveedorBarras de reabasto");
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de recibir la orden Automatica " + ex.getMessage());
        }
        return res;
    }
    
}
