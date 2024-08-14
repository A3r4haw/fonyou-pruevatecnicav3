package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author bbautista
 */
public class DetalleReabastoInsumo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idReabastoInsumo;
    private String idMedicamento;
    private String lote;
    private Integer cantidad;
    private Date fecha;
    private Integer cantidadCaja;

    public DetalleReabastoInsumo() {
    }

    public DetalleReabastoInsumo(String idReabastoInsumo, String idMedicamento, String lote, Integer cantidad, Date fecha, Integer cantidadCaja) {
        this.idReabastoInsumo = idReabastoInsumo;
        this.idMedicamento = idMedicamento;
        this.lote = lote;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.cantidadCaja = cantidadCaja;
    }

    public String getIdReabastoInsumo() {
        return idReabastoInsumo;
    }

    public void setIdReabastoInsumo(String idReabastoInsumo) {
        this.idReabastoInsumo = idReabastoInsumo;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

	public Integer getCantidadCaja() {
		return cantidadCaja;
	}

	public void setCantidadCaja(Integer cantidadCaja) {
		this.cantidadCaja = cantidadCaja;
	}

}
