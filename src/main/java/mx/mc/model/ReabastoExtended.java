package mx.mc.model;

/**
 * @author AORTIZ
 */
public class ReabastoExtended extends Reabasto{

    private static final long serialVersionUID = 1L;
    
    private String nombreEstructura;
    private String nombreProveedor;
    private String nombreTipoOrden;
    private String nombreEstatus;
    private String nombreUsrSolicitane;
    private String textoBusqueda;
    private String descripcionEstructura;
    private String clavePresupuestal;
    private String claveEstructura;
    private Integer idTipoAlmacen;
    private String folioAlternativo;
    private String nombreUsuarioSurte;
    private String matriculaPersonal;
    private String estatusFolioAlternativo;   
    private String nombreUsrRecibe;

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreTipoOrden() {
        return nombreTipoOrden;
    }

    public void setNombreTipoOrden(String nombreTipoOrden) {
        this.nombreTipoOrden = nombreTipoOrden;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }

	public String getNombreUsrSolicitane() {
		return nombreUsrSolicitane;
	}

	public void setNombreUsrSolicitane(String nombreUsrSolicitane) {
		this.nombreUsrSolicitane = nombreUsrSolicitane;
	}

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public String getDescripcionEstructura() {
        return descripcionEstructura;
    }

    public void setDescripcionEstructura(String descripcionEstructura) {
        this.descripcionEstructura = descripcionEstructura;
    }

    public String getClavePresupuestal() {
        return clavePresupuestal;
    }

    public void setClavePresupuestal(String clavePresupuestal) {
        this.clavePresupuestal = clavePresupuestal;
    }

    public String getClaveEstructura() {
        return claveEstructura;
    }

    public void setClaveEstructura(String claveEstructura) {
        this.claveEstructura = claveEstructura;
    }

    public Integer getIdTipoAlmacen() {
        return idTipoAlmacen;
    }

    public void setIdTipoAlmacen(Integer idTipoAlmacen) {
        this.idTipoAlmacen = idTipoAlmacen;
    }   

    public String getFolioAlternativo() {
        return folioAlternativo;
    }

    public void setFolioAlternativo(String folioAlternativo) {
        this.folioAlternativo = folioAlternativo;
    }

    public String getNombreUsuarioSurte() {
        return nombreUsuarioSurte;
    }

    public void setNombreUsuarioSurte(String nombreUsuarioSurte) {
        this.nombreUsuarioSurte = nombreUsuarioSurte;
    }

    public String getMatriculaPersonal() {
        return matriculaPersonal;
    }

    public void setMatriculaPersonal(String matriculaPersonal) {
        this.matriculaPersonal = matriculaPersonal;
    }

    public String getEstatusFolioAlternativo() {
        return estatusFolioAlternativo;
    }

    public void setEstatusFolioAlternativo(String estatusFolioAlternativo) {
        this.estatusFolioAlternativo = estatusFolioAlternativo;
    }    

    public String getNombreUsrRecibe() {
        return nombreUsrRecibe;
    }

    public void setNombreUsrRecibe(String nombreUsrRecibe) {
        this.nombreUsrRecibe = nombreUsrRecibe;
    }

}
