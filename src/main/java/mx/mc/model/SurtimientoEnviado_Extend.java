package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hramirez
 */
public class SurtimientoEnviado_Extend extends SurtimientoEnviado implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clave;
//    private String lote;
    private String idEstructura;
    private Integer activo;
    private String claveProveedor;
    private boolean conforme;
    private Integer cantidadDevolver;
    private Integer cantidadRecibida;
    private Integer tipoDevolucion;
    private boolean merma;

    private Date fechaCaducidad;
    private String nombreMédico;
    private String nombreUsuarioSurte;
    private String nombreUsuarioPrepara;
    private String nombrePaciente;
    private String folioPrescripcion;
    private String folioSurtimiento;
    private String fechaMovimiento;
    private String nombreFabricante;
    private Integer cantidadPorEnviar;

    public SurtimientoEnviado_Extend() {
        //No code needed in constructor
    }

    @Override
    public String getClave() {
        return clave;
    }

    @Override
    public void setClave(String clave) {
        this.clave = clave;
    }

//    @Override
//    public String getLote() {
//        return lote;
//    }
//
//    @Override
//    public void setLote(String lote) {
//        this.lote = lote;
//    }

    public Integer getTipoDevolucion() {
        return tipoDevolucion;
    }

    public void setTipoDevolucion(Integer tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
    }

    public boolean isMerma() {
        return merma;
    }

    public void setMerma(boolean merma) {
        this.merma = merma;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public boolean isConforme() {
        return conforme;
    }

    public void setConforme(boolean conforme) {
        this.conforme = conforme;
    }

    public Integer getCantidadDevolver() {
        return cantidadDevolver;
    }

    public void setCantidadDevolver(Integer cantidadDevolver) {
        this.cantidadDevolver = cantidadDevolver;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getNombreMédico() {
        return nombreMédico;
    }

    public void setNombreMédico(String nombreMédico) {
        this.nombreMédico = nombreMédico;
    }

    public String getNombreUsuarioSurte() {
        return nombreUsuarioSurte;
    }

    public void setNombreUsuarioSurte(String nombreUsuarioSurte) {
        this.nombreUsuarioSurte = nombreUsuarioSurte;
    }

    public String getNombreUsuarioPrepara() {
        return nombreUsuarioPrepara;
    }

    public void setNombreUsuarioPrepara(String nombreUsuarioPrepara) {
        this.nombreUsuarioPrepara = nombreUsuarioPrepara;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }

    public Integer getCantidadPorEnviar() {
        return cantidadPorEnviar;
    }

    public void setCantidadPorEnviar(Integer cantidadPorEnviar) {
        this.cantidadPorEnviar = cantidadPorEnviar;
    }

    

}
