/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoRecepcion;

/**
 *
 * @author gcruz
 */
public interface ReabastoAutomaticoService extends GenericCrudService<ClaveProveedorBarras_Extend, String> {
   
    public boolean recibirReabastoAutomatico(List<ReabastoRecepcion> listReabastoRecepcion, List<ClaveProveedorBarras> listClaveProveedorBarras,
                    Reabasto unReabasto, List<ReabastoInsumo> listReabastoInsumo, List<ReabastoEnviado> listReabastoEnviado, List<ClaveProveedorBarras> listClaveProveedorExiste) throws Exception;
}
