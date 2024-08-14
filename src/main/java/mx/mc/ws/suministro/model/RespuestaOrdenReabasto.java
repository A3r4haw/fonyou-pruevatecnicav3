package mx.mc.ws.suministro.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hramirez
 */
public class RespuestaOrdenReabasto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoRespuesta;
    private String descripcionRespuesta;
    private List<OrdenReabasto> solicitudReabasto;

    public RespuestaOrdenReabasto() {
        //No code needed in constructor
    }

    public RespuestaOrdenReabasto(String codigoRespuesta, String descripcionRespuesta, List<OrdenReabasto> solicitudReabasto) {
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.solicitudReabasto = solicitudReabasto;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public List<OrdenReabasto> getSolicitudReabasto() {
        return solicitudReabasto;
    }

    public void setSolicitudReabasto(List<OrdenReabasto> solicitudReabasto) {
        this.solicitudReabasto = solicitudReabasto;
    }

}
