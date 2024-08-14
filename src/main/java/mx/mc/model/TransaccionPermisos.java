package mx.mc.model;

/**
 *
 * @author hramirez
 */
public class TransaccionPermisos extends Transaccion {

    private static final long serialVersionUID = -1L;

    private String nombreUsuario;

    private String idRol;
    private String rol;
    private boolean rolActivo;

    private String idModulo;
    private String modulo;
    private String iconModulo;
    private boolean moduloActivo;

    private String idAccion;
    private String accion;
    
									  

    private boolean permitido;

    private String idTransaccion;
    private String nombreTransaccion;
    private String descripcionTrans;
    private String iconTransaccion;
    private boolean transaccionActivo;
        
    private String idNode;
    private String idNodeParent;
    
    private boolean puedeCrear;
    private boolean puedeVer;
    private boolean puedeEditar;
    private boolean puedeEliminar;
    private boolean puedeProcesar;
    private boolean puedeAutorizar;

    public TransaccionPermisos() {
        //No code needed in constructor
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isRolActivo() {
        return rolActivo;
    }

    public void setRolActivo(boolean rolActivo) {
        this.rolActivo = rolActivo;
    }

    @Override
    public String getIdModulo() {
        return idModulo;
    }

    @Override
    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public boolean isModuloActivo() {
        return moduloActivo;
    }

    public void setModuloActivo(boolean moduloActivo) {
        this.moduloActivo = moduloActivo;
    }

    public String getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(String idAccion) {
        this.idAccion = idAccion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public boolean isTransaccionActivo() {
        return transaccionActivo;
    }

    public void setTransaccionActivo(boolean transaccionActivo) {
        this.transaccionActivo = transaccionActivo;
    }

    public boolean isPermitido() {
        return permitido;
    }

    public void setPermitido(boolean permitido) {
        this.permitido = permitido;
    }

    public String getIconTransaccion() {
        return iconTransaccion;
    }

    public void setIconTransaccion(String iconTransaccion) {
        this.iconTransaccion = iconTransaccion;
    }

    public String getIconModulo() {
        return iconModulo;
    }

    public void setIconModulo(String iconModulo) {
        this.iconModulo = iconModulo;
    }

    @Override
    public String getIdTransaccion() {
        return idTransaccion;
    }

    @Override
    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNombreTransaccion() {
        return nombreTransaccion;
    }

    public void setNombreTransaccion(String nombreTransaccion) {
        this.nombreTransaccion = nombreTransaccion;
    }

    public String getDescripcionTrans() {
        return descripcionTrans;
    }

    public void setDescripcionTrans(String descripcionTrans) {
        this.descripcionTrans = descripcionTrans;
    }

    public boolean isPuedeCrear() {
        return puedeCrear;
    }

    public void setPuedeCrear(boolean puedeCrear) {
        this.puedeCrear = puedeCrear;
    }

    public boolean isPuedeVer() {
        return puedeVer;
    }

    public void setPuedeVer(boolean puedeVer) {
        this.puedeVer = puedeVer;
    }

    public boolean isPuedeEditar() {
        return puedeEditar;
    }

    public void setPuedeEditar(boolean puedeEditar) {
        this.puedeEditar = puedeEditar;
    }

    public boolean isPuedeEliminar() {
        return puedeEliminar;
    }

    public void setPuedeEliminar(boolean puedeEliminar) {
        this.puedeEliminar = puedeEliminar;
    }

    public boolean isPuedeProcesar() {
        return puedeProcesar;
    }

    public void setPuedeProcesar(boolean puedeProcesar) {
        this.puedeProcesar = puedeProcesar;
    }

    public boolean isPuedeAutorizar() {
        return puedeAutorizar;
    }

    public void setPuedeAutorizar(boolean puedeAutorizar) {
        this.puedeAutorizar = puedeAutorizar;
    }

    public String getIdNodeParent() {
        return idNodeParent;
    }

    public void setIdNodeParent(String idNodeParent) {
        this.idNodeParent = idNodeParent;
    }

    public String getIdNode() {
        return idNode;
    }

    public void setIdNode(String idNode) {
        this.idNode = idNode;
    }

}
