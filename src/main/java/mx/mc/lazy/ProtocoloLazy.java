package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.ProtocoloExtended;
import mx.mc.service.ProtocoloService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hramirez
 */
public class ProtocoloLazy extends LazyDataModel<ProtocoloExtended> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocoloLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ProtocoloService protocoloService;
    private String cadenaBusqueda;
    private List<ProtocoloExtended> listaProtocolos;

    private int totalReg;

    public ProtocoloLazy() {

    }

    public ProtocoloLazy(ProtocoloService protocoloService, String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
        this.protocoloService = protocoloService;
        this.listaProtocolos = new ArrayList<>();

    }

    @Autowired
    @Override
    public List<ProtocoloExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {

        if (listaProtocolos != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                listaProtocolos = protocoloService.obtenerListaProtocolos(cadenaBusqueda, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {
                LOGGER.error("Error al obtener la lista de interacciones medicamentosas", ex.getMessage());
                listaProtocolos = new ArrayList<>();
            }
        } else {
            listaProtocolos = new ArrayList<>();
        }
        return listaProtocolos;
    }

    private int obtenerTotalResultados() {
        try {
            Long total = protocoloService.obtenerTotalProtocolos(cadenaBusqueda);
            totalReg = total.intValue();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }

    @Override
    public ProtocoloExtended getRowData(String rowKey) {
        for (ProtocoloExtended protocolo : listaProtocolos) {
            if (protocolo.getIdProtocolo().equals(rowKey)) {
                return protocolo;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(ProtocoloExtended object) {
        if (object == null) {
            return null;
        }
        return object.getIdProtocolo();
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
    
}
