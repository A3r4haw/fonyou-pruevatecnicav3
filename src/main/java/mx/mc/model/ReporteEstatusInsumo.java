/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author mcalderon
 */
public class ReporteEstatusInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String claveInstitucional;
    private String nombreCorto;
    private int maximo;
    private int minimo;
    private int disponible;
    private int consumoPromedio;
    private int pendientePorRecibir;
    private int consumoEsperado;

    public ReporteEstatusInsumo() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 19 * hash + Objects.hashCode(this.nombreCorto);
        hash = 19 * hash + this.maximo;
        hash = 19 * hash + this.minimo;
        hash = 19 * hash + this.disponible;
        hash = 19 * hash + this.consumoPromedio;
        hash = 19 * hash + this.pendientePorRecibir;
        hash = 19 * hash + this.consumoEsperado;
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
        final ReporteEstatusInsumo other = (ReporteEstatusInsumo) obj;
        if (this.maximo != other.maximo) {
            return false;
        }
        if (this.minimo != other.minimo) {
            return false;
        }
        if (this.disponible != other.disponible) {
            return false;
        }
        if (this.consumoPromedio != other.consumoPromedio) {
            return false;
        }
        if (this.pendientePorRecibir != other.pendientePorRecibir) {
            return false;
        }
        if (this.consumoEsperado != other.consumoEsperado) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        return Objects.equals(this.nombreCorto, other.nombreCorto);
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveIstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getConsumoPromedio() {
        return consumoPromedio;
    }

    public void setConsumoPromedio(int consumoPromedio) {
        this.consumoPromedio = consumoPromedio;
    }

    public int getPendientePorRecibir() {
        return pendientePorRecibir;
    }

    public void setPendientePorRecibir(int pendientePorRecibir) {
        this.pendientePorRecibir = pendientePorRecibir;
    }

    public int getConsumoEsperado() {
        return consumoEsperado;
    }

    public void setConsumoEsperado(int consumoEsperado) {
        this.consumoEsperado = consumoEsperado;
    }
    
    

}
