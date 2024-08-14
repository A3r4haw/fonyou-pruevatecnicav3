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
public class ParamBusquedaAlertaDTO {
    
    private String numeroPaciente;
    private String numeroVisita;
    private String numeroMedico;
    private List<String> listaDiagnosticos;
    private String folioPrescripcion;
    private List<MedicamentoDTO> listaMedicametos;
    private boolean esSolucionOncologica;
    private String folioSurtimiento;
    
    public ParamBusquedaAlertaDTO() {
        
    }

    public ParamBusquedaAlertaDTO(String numeroPaciente, String numeroVisita, String numeroMedico, List<String> listaDiagnosticos, String folioPrescripcion, List<MedicamentoDTO> listaMedicametos, boolean esSolucionOncologica, String folioSurtimiento) {
        this.numeroPaciente = numeroPaciente;
        this.numeroVisita = numeroVisita;
        this.numeroMedico = numeroMedico;
        this.listaDiagnosticos = listaDiagnosticos;
        this.folioPrescripcion = folioPrescripcion;
        this.listaMedicametos = listaMedicametos;
        this.esSolucionOncologica = esSolucionOncologica;
        this.folioSurtimiento = folioSurtimiento;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.numeroPaciente);
        hash = 79 * hash + Objects.hashCode(this.numeroVisita);
        hash = 79 * hash + Objects.hashCode(this.numeroMedico);
        hash = 79 * hash + Objects.hashCode(this.listaDiagnosticos);
        hash = 79 * hash + Objects.hashCode(this.folioPrescripcion);
        hash = 79 * hash + Objects.hashCode(this.listaMedicametos);
        hash = 79 * hash + Objects.hashCode(this.esSolucionOncologica);
        hash = 79 * hash + Objects.hashCode(this.folioSurtimiento);
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
        final ParamBusquedaAlertaDTO other = (ParamBusquedaAlertaDTO) obj;
        if (!Objects.equals(this.numeroPaciente, other.numeroPaciente)) {
            return false;
        }
        if (!Objects.equals(this.numeroVisita, other.numeroVisita)) {
            return false;
        }
        if (!Objects.equals(this.numeroMedico, other.numeroMedico)) {
            return false;
        }
        if (!Objects.equals(this.folioPrescripcion, other.folioPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.listaDiagnosticos, other.listaDiagnosticos)) {
            return false;
        }
        if (!Objects.equals(this.listaMedicametos, other.listaMedicametos)) {
            return false;
        }
        if (!Objects.equals(this.esSolucionOncologica, other.esSolucionOncologica)) {
            return false;
        }
        if (!Objects.equals(this.folioSurtimiento, other.folioSurtimiento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParamBusquedaAlerta{" + "numeroPaciente=" + numeroPaciente + ", numeroVisita=" + numeroVisita + ", numeroMedico=" + numeroMedico + ", listaDiagnosticos=" + listaDiagnosticos + ", folioPrescripcion=" + folioPrescripcion + ", listaMedicametos=" + listaMedicametos + ", esSolucionOncologica=" + esSolucionOncologica + ", folioSurtimiento=" + folioSurtimiento + '}';
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public String getNumeroVisita() {
        return numeroVisita;
    }

    public void setNumeroVisita(String numeroVisita) {
        this.numeroVisita = numeroVisita;
    }

    public String getNumeroMedico() {
        return numeroMedico;
    }

    public void setNumeroMedico(String numeroMedico) {
        this.numeroMedico = numeroMedico;
    }

    public List<String> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<String> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public List<MedicamentoDTO> getListaMedicametos() {
        return listaMedicametos;
    }

    public void setListaMedicametos(List<MedicamentoDTO> listaMedicametos) {
        this.listaMedicametos = listaMedicametos;
    }

    public boolean isEsSolucionOncologica() {
        return esSolucionOncologica;
    }

    public void setEsSolucionOncologica(boolean esSolucionOncologica) {
        this.esSolucionOncologica = esSolucionOncologica;
    }    

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }
    
}
