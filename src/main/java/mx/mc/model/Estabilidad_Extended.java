package mx.mc.model;

/**
 *
 * @author cervanets
 */
public class Estabilidad_Extended extends Estabilidad {

    private static final long serialVersionUID = 1L;

    public String claveInstitucional;
    public String nombreCorto;
    public String nombreUnidadConcentracion;
    public String nombreFabricante;
    public String nombreContenedor;
    public String viaAdministracion;

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombreUnidadConcentracion() {
        return nombreUnidadConcentracion;
    }

    public void setNombreUnidadConcentracion(String nombreUnidadConcentracion) {
        this.nombreUnidadConcentracion = nombreUnidadConcentracion;
    }

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }

    public String getNombreContenedor() {
        return nombreContenedor;
    }

    public void setNombreContenedor(String nombreContenedor) {
        this.nombreContenedor = nombreContenedor;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    
}
