package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.Estructura;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.PacienteService;


/**
 *
 * @author mcalderon
 *
 */
public class PrescripcionesLazy extends LazyDataModel<Paciente_Extended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescripcionesLazy.class);
    private static final long serialVersionUID = 1L;

    private ParamBusquedaReporte paramBusquedaReporte;
    private transient PacienteService pacienteService;
    private List<Paciente_Extended> pacienteExtendList;
    
    private List<Integer> listEstatusPaciente;
    private List<Estructura> listaEstructuras;
    private String idUnidadMedica;
    
    private int totalReg;

    public PrescripcionesLazy() {
        //No code needed in constructor
    }

    public PrescripcionesLazy(PacienteService pacienteService, ParamBusquedaReporte paramBusquedaReporte,List<Integer> listEstatusPaciente,List<Estructura> listaEstructuras ,String idUnidadMedica ) {
        this.pacienteService = pacienteService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        pacienteExtendList = new ArrayList<>();
        
        this.listEstatusPaciente = listEstatusPaciente;
        this.listaEstructuras = listaEstructuras;
        this.idUnidadMedica = idUnidadMedica;
    }

    @Autowired
    @Override
    public List<Paciente_Extended> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (pacienteExtendList != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                
                 if(paramBusquedaReporte.isNuevaBusqueda()) {
	        		 startingAt = 0;
	        	 }

                pacienteExtendList = pacienteService.obtenerPacientePorIdUnidadActLazy(paramBusquedaReporte, startingAt, maxPerPage, listaEstructuras, idUnidadMedica, listEstatusPaciente);
                paramBusquedaReporte.setNuevaBusqueda(false);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Prescripciones. {}", ex.getMessage());
                pacienteExtendList = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            pacienteExtendList = new ArrayList<>();

        }
        return pacienteExtendList;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = pacienteService.obtenerTotalPacientePorIdUnidadActLazy(paramBusquedaReporte, listaEstructuras, idUnidadMedica, listEstatusPaciente);
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
    public Paciente_Extended getRowData(String rowKey) {
        for(Paciente_Extended pac : pacienteExtendList){
            if(pac.getIdPaciente().equals(rowKey)){
                return pac;
            }
        
        }
       return null;
    }

    @Override
    public Object getRowKey(Paciente_Extended object) {
        if (object == null) {
            return null;
        }
        return object.getIdPaciente();
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

    
}
