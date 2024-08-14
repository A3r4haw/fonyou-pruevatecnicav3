/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class ParamBusquedaProtocoloDTO {
    
    private String numeroPaciente;
    private List<String> listaDiagnosticos;
    private List<MedicamentoDTO> listaMedicametos;
    private String idPrescripcion;
    private String folioSurtimiento;

    public ParamBusquedaProtocoloDTO() {
    }

    public ParamBusquedaProtocoloDTO(String numeroPaciente, List<String> listaDiagnosticos, List<MedicamentoDTO> listaMedicametos, String idPrescripcion, String folioSurtimiento) {
        this.numeroPaciente = numeroPaciente;
        this.listaDiagnosticos = listaDiagnosticos;
        this.listaMedicametos = listaMedicametos;
        this.idPrescripcion = idPrescripcion;
        this.folioSurtimiento = folioSurtimiento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.numeroPaciente);
        hash = 61 * hash + Objects.hashCode(this.listaDiagnosticos);
        hash = 61 * hash + Objects.hashCode(this.listaMedicametos);
        hash = 61 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 61 * hash + Objects.hashCode(this.folioSurtimiento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParamBusquedaProtocoloDTO other = (ParamBusquedaProtocoloDTO) obj;
        if (!Objects.equals(this.numeroPaciente, other.numeroPaciente)) {
            return false;
        }
        if (!Objects.equals(this.listaDiagnosticos, other.listaDiagnosticos)) {
            return false;
        }
        if (!Objects.equals(this.listaMedicametos, other.listaMedicametos)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.folioSurtimiento, other.folioSurtimiento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParamBusquedaProtocoloDTO{" + "numeroPaciente=" + numeroPaciente + ", listaDiagnosticos=" + listaDiagnosticos + ", listaMedicametos=" + listaMedicametos + ",idPrescripcion=" + idPrescripcion + ",folioSurtimiento=" + folioSurtimiento + '}';
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public List<String> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<String> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<MedicamentoDTO> getListaMedicametos() {
        return listaMedicametos;
    }

    public void setListaMedicametos(List<MedicamentoDTO> listaMedicametos) {
        this.listaMedicametos = listaMedicametos;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }
        
}
