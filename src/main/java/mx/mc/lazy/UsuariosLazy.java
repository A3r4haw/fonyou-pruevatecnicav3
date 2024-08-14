package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.VistaUsuario;
import mx.mc.service.UsuarioService;

/**
 *
 * @author mcalderon
 *
 */
public class UsuariosLazy extends LazyDataModel<VistaUsuario> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuariosLazy.class);

    private ParamBusquedaReporte paramBusquedaReporte;
    private int totalReg;
    
    private transient UsuarioService usuarioService;
    private List<VistaUsuario> listUsuarios;

    public UsuariosLazy() {
        //No code needed in constructor
    }

    public UsuariosLazy(UsuarioService usuarioService, ParamBusquedaReporte paramBusquedaReporte) {
        this.usuarioService = usuarioService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listUsuarios = new ArrayList<>();
    }

    @Override
    public List<VistaUsuario> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {

        if (listUsuarios != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                listUsuarios = usuarioService.obtenerUsuariosOrdenadoPorCadena(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortOrder);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteEmisionVales. {}", ex.getMessage());
                listUsuarios = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listUsuarios = new ArrayList<>();

        }
        return listUsuarios;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = usuarioService.obtenerTotalUsuariosOrdenadoPorCadena(paramBusquedaReporte);

                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.info("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public VistaUsuario getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(VistaUsuario object) {
        if (object == null) {
            return null;
        }
        return object;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

    
}
