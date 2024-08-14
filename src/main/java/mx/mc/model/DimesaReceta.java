package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class DimesaReceta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idEstructuraHospitalaria;
    private String cama;
    private String descripcionServicio;
    private String descripcion;
    private String estatus;
    private Date fecha;
    private Date fechaReferencia;
    private String folio;
    private String folioPago;
    private String piso;
    private String servicio;
    private String tipo;
    private List<DimesaRecetaMaterial> materiales;
    private DimesaPaciente recetaPaciente;
    private DimesaMedico recetaMedico;

    private String apellidoMaterno;
    private String apellidoPaterno;
    private String descripcionPadecimiento;
    private String descripcionPrograma;
    private String direccion;
    private Date fechaNacimiento;
    private String nombre;
    private String numeroAfiliacion;
    private String padecimiento;
    private String programa;
    private String sexo;
    private String telefono;

    private String medicoApellidoMaterno;
    private String medicoApellidoPaterno;
    private String medicoCedula;
    private String medicoNombre;

    public DimesaReceta() {
        //No code needed in constructor
    }
    
    public String getIdEstructuraHospitalaria() {
        return idEstructuraHospitalaria;
    }

    public void setIdEstructuraHospitalaria(String idEstructuraHospitalaria) {
        this.idEstructuraHospitalaria = idEstructuraHospitalaria;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaReferencia() {
        return fechaReferencia;
    }

    public void setFechaReferencia(Date fechaReferencia) {
        this.fechaReferencia = fechaReferencia;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolioPago() {
        return folioPago;
    }

    public void setFolioPago(String folioPago) {
        this.folioPago = folioPago;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<DimesaRecetaMaterial> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<DimesaRecetaMaterial> materiales) {
        this.materiales = materiales;
    }

    public DimesaPaciente getRecetaPaciente() {
        return recetaPaciente;
    }

    public void setRecetaPaciente(DimesaPaciente recetaPaciente) {
        this.recetaPaciente = recetaPaciente;
    }

    public DimesaMedico getRecetaMedico() {
        return recetaMedico;
    }

    public void setRecetaMedico(DimesaMedico recetaMedico) {
        this.recetaMedico = recetaMedico;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getDescripcionPadecimiento() {
        return descripcionPadecimiento;
    }

    public void setDescripcionPadecimiento(String descripcionPadecimiento) {
        this.descripcionPadecimiento = descripcionPadecimiento;
    }

    public String getDescripcionPrograma() {
        return descripcionPrograma;
    }

    public void setDescripcionPrograma(String descripcionPrograma) {
        this.descripcionPrograma = descripcionPrograma;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroAfiliacion() {
        return numeroAfiliacion;
    }

    public void setNumeroAfiliacion(String numeroAfiliacion) {
        this.numeroAfiliacion = numeroAfiliacion;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMedicoApellidoMaterno() {
        return medicoApellidoMaterno;
    }

    public void setMedicoApellidoMaterno(String medicoApellidoMaterno) {
        this.medicoApellidoMaterno = medicoApellidoMaterno;
    }

    public String getMedicoApellidoPaterno() {
        return medicoApellidoPaterno;
    }

    public void setMedicoApellidoPaterno(String medicoApellidoPaterno) {
        this.medicoApellidoPaterno = medicoApellidoPaterno;
    }

    public String getMedicoCedula() {
        return medicoCedula;
    }

    public void setMedicoCedula(String medicoCedula) {
        this.medicoCedula = medicoCedula;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    @Override
    public String toString() {
        return "DimesaReceta{" + "idEstructuraHospitalaria=" + idEstructuraHospitalaria + ", cama=" + cama + ", descripcionServicio=" + descripcionServicio + ", descripcion=" + descripcion + ", Estatus=" + estatus + ", fecha=" + fecha + ", fechaReferencia=" + fechaReferencia + ", folio=" + folio + ", folioPago=" + folioPago + ", piso=" + piso + ", servicio=" + servicio + ", tipo=" + tipo + ", materiales=" + materiales + ", recetaPaciente=" + recetaPaciente + ", recetaMedico=" + recetaMedico + ", apellidoMaterno=" + apellidoMaterno + ", apellidoPaterno=" + apellidoPaterno + ", descripcionPadecimiento=" + descripcionPadecimiento + ", descripcionPrograma=" + descripcionPrograma + ", direccion=" + direccion + ", fechaNacimiento=" + fechaNacimiento + ", nombre=" + nombre + ", numeroAfiliacion=" + numeroAfiliacion + ", padecimiento=" + padecimiento + ", programa=" + programa + ", sexo=" + sexo + ", telefono=" + telefono + ", medicoApellidoMaterno=" + medicoApellidoMaterno + ", medicoApellidoPaterno=" + medicoApellidoPaterno + ", medicoCedula=" + medicoCedula + ", medicoNombre=" + medicoNombre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.idEstructuraHospitalaria);
        hash = 83 * hash + Objects.hashCode(this.cama);
        hash = 83 * hash + Objects.hashCode(this.descripcionServicio);
        hash = 83 * hash + Objects.hashCode(this.descripcion);
        hash = 83 * hash + Objects.hashCode(this.estatus);
        hash = 83 * hash + Objects.hashCode(this.fecha);
        hash = 83 * hash + Objects.hashCode(this.fechaReferencia);
        hash = 83 * hash + Objects.hashCode(this.folio);
        hash = 83 * hash + Objects.hashCode(this.folioPago);
        hash = 83 * hash + Objects.hashCode(this.piso);
        hash = 83 * hash + Objects.hashCode(this.servicio);
        hash = 83 * hash + Objects.hashCode(this.tipo);
        hash = 83 * hash + Objects.hashCode(this.materiales);
        hash = 83 * hash + Objects.hashCode(this.recetaPaciente);
        hash = 83 * hash + Objects.hashCode(this.recetaMedico);
        hash = 83 * hash + Objects.hashCode(this.apellidoMaterno);
        hash = 83 * hash + Objects.hashCode(this.apellidoPaterno);
        hash = 83 * hash + Objects.hashCode(this.descripcionPadecimiento);
        hash = 83 * hash + Objects.hashCode(this.descripcionPrograma);
        hash = 83 * hash + Objects.hashCode(this.direccion);
        hash = 83 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 83 * hash + Objects.hashCode(this.nombre);
        hash = 83 * hash + Objects.hashCode(this.numeroAfiliacion);
        hash = 83 * hash + Objects.hashCode(this.padecimiento);
        hash = 83 * hash + Objects.hashCode(this.programa);
        hash = 83 * hash + Objects.hashCode(this.sexo);
        hash = 83 * hash + Objects.hashCode(this.telefono);
        hash = 83 * hash + Objects.hashCode(this.medicoApellidoMaterno);
        hash = 83 * hash + Objects.hashCode(this.medicoApellidoPaterno);
        hash = 83 * hash + Objects.hashCode(this.medicoCedula);
        hash = 83 * hash + Objects.hashCode(this.medicoNombre);
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
        final DimesaReceta other = (DimesaReceta) obj;
        if (!Objects.equals(this.idEstructuraHospitalaria, other.idEstructuraHospitalaria)) {
            return false;
        }
        if (!Objects.equals(this.cama, other.cama)) {
            return false;
        }
        if (!Objects.equals(this.descripcionServicio, other.descripcionServicio)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.folioPago, other.folioPago)) {
            return false;
        }
        if (!Objects.equals(this.piso, other.piso)) {
            return false;
        }
        if (!Objects.equals(this.servicio, other.servicio)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.apellidoMaterno, other.apellidoMaterno)) {
            return false;
        }
        if (!Objects.equals(this.apellidoPaterno, other.apellidoPaterno)) {
            return false;
        }
        if (!Objects.equals(this.descripcionPadecimiento, other.descripcionPadecimiento)) {
            return false;
        }
        if (!Objects.equals(this.descripcionPrograma, other.descripcionPrograma)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.numeroAfiliacion, other.numeroAfiliacion)) {
            return false;
        }
        if (!Objects.equals(this.padecimiento, other.padecimiento)) {
            return false;
        }
        if (!Objects.equals(this.programa, other.programa)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.medicoApellidoMaterno, other.medicoApellidoMaterno)) {
            return false;
        }
        if (!Objects.equals(this.medicoApellidoPaterno, other.medicoApellidoPaterno)) {
            return false;
        }
        if (!Objects.equals(this.medicoCedula, other.medicoCedula)) {
            return false;
        }
        if (!Objects.equals(this.medicoNombre, other.medicoNombre)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.fechaReferencia, other.fechaReferencia)) {
            return false;
        }
        if (!Objects.equals(this.materiales, other.materiales)) {
            return false;
        }
        if (!Objects.equals(this.recetaPaciente, other.recetaPaciente)) {
            return false;
        }
        if (!Objects.equals(this.recetaMedico, other.recetaMedico)) {
            return false;
        }
        return Objects.equals(this.fechaNacimiento, other.fechaNacimiento);
    }

}
