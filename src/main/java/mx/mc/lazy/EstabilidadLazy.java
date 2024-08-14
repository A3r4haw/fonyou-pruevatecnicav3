package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Estabilidad_Extended;
import mx.mc.service.EstabilidadService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class EstabilidadLazy extends LazyDataModel<Estabilidad_Extended> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstabilidadLazy.class);
    private static final long serialVersionUID = 1L;

    private transient EstabilidadService estabilidadService;

    private String cadenaBusqueda;
    private List<Estabilidad_Extended> listaEstabilidades;

    private int totalReg;

    public EstabilidadLazy() {

    }

    public EstabilidadLazy(EstabilidadService estabilidadService, String cadenaBusqueda) {
        this.estabilidadService = estabilidadService;
        this.cadenaBusqueda = cadenaBusqueda;
        this.listaEstabilidades = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<Estabilidad_Extended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {

        listaEstabilidades = new ArrayList<>();

        if (listaEstabilidades != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                listaEstabilidades = estabilidadService.obtenerListaEstabilidades(cadenaBusqueda, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {
                LOGGER.error("Error al obtener la lista de estabilidades", ex.getMessage());
            }
        } else {
            listaEstabilidades = new ArrayList<>();
        }
        return listaEstabilidades;
    }

    private int obtenerTotalResultados() {
        Long total = 0L;
        try {
            total = estabilidadService.obtenerTotalEstabilidades(cadenaBusqueda);
            totalReg = total.intValue();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }

    @Override
    public Estabilidad_Extended getRowData(String rowKey) {
        for (Estabilidad_Extended estabilidad : listaEstabilidades) {
            if (estabilidad.getIdEstabilidad().equals(rowKey)) {
                return estabilidad;
            }
        }
        return null;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

}
