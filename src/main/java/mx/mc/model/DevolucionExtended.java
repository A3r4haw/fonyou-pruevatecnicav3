package mx.mc.model;
import java.util.List;


public class DevolucionExtended extends Devolucion {

	private static final long serialVersionUID = 1L;
	
	private String nombreOrigen;
	private String nombreDestino;
	private String nombreEstatus;
	private Integer idTipoAlmacenDestino;
	private List<DevolucionDetalleExtended> listDetalleDevolucion;
	
	public String getNombreOrigen() {
		return nombreOrigen;
	}
	
	public void setNombreOrigen(String nombreOrigen) {
		this.nombreOrigen = nombreOrigen;
	}
	
	public String getNombreDestino() {
		return nombreDestino;
	}
	
	public void setNombreDestino(String nombreDestino) {
		this.nombreDestino = nombreDestino;
	}
	
	public String getNombreEstatus() {
		return nombreEstatus;
	}
	
	public void setNombreEstatus(String nombreEstatus) {
		this.nombreEstatus = nombreEstatus;
	}
	
	public Integer getIdTipoAlmacenDestino() {
		return idTipoAlmacenDestino;
	}

	public void setIdTipoAlmacenDestino(Integer idTipoAlmacenDestino) {
		this.idTipoAlmacenDestino = idTipoAlmacenDestino;
	}

	public List<DevolucionDetalleExtended> getListDetalleDevolucion() {
		return listDetalleDevolucion;
	}

	public void setListDetalleDevolucion(List<DevolucionDetalleExtended> listDetalleDevolucion) {
		this.listDetalleDevolucion = listDetalleDevolucion;
	}
}
