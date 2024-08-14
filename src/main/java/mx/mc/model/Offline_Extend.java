/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.List;

/**
 *
 * @author Ulai
 */
public class Offline_Extend extends Offline {
    
    private static final long serialVersionUID = 1L;
        private String idSurtimientoInsumo;
        private String idSurtimiento;
        private String estatus;
        private String mensaje;
        private List<String> estatusList;
        private List<String> mensajeList;
        private List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;
        private List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
        private Surtimiento_Extend surtimientoExtend;
        private VistaRecepcionMedicamento viewRecepcionMed;
        

        public String getIdSurtimiento() {
            return idSurtimiento;
        }

        public void setIdSurtimiento(String idSurtimiento) {
            this.idSurtimiento = idSurtimiento;
        }
        
        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public String getIdSurtimientoInsumo() {
            return idSurtimientoInsumo;
        }

        public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
            this.idSurtimientoInsumo = idSurtimientoInsumo;
        }
        
        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
        
        @Override
        public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
            return surtimientoInsumoExtendedList;
        }

        public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
            this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
        }

        public List<SurtimientoEnviado_Extend> getSurtimientoEnviadoExtendList() {
            return surtimientoEnviadoExtendList;
        }

        public void setSurtimientoEnviadoExtendList(List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList) {
            this.surtimientoEnviadoExtendList = surtimientoEnviadoExtendList;
        }

        @Override
        public Surtimiento_Extend getSurtimientoExtend() {
            return surtimientoExtend;
        }

        @Override
        public void setSurtimientoExtend(Surtimiento_Extend surtimiento_Extend) {
            this.surtimientoExtend = surtimiento_Extend;
        }

        public VistaRecepcionMedicamento getViewRecepcionMed() {
            return viewRecepcionMed;
        }

        public void setViewRecepcionMed(VistaRecepcionMedicamento viewRecepcionMed) {
            this.viewRecepcionMed = viewRecepcionMed;
        }

    public List<String> getEstatusList() {
        return estatusList;
    }

    public void setEstatusList(List<String> estatusList) {
        this.estatusList = estatusList;
    }

    public List<String> getMensajeList() {
        return mensajeList;
    }

    public void setMensajeList(List<String> mensajeList) {
        this.mensajeList = mensajeList;
    }
        
        
    
}
