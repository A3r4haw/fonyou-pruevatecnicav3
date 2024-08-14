/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Config;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.mc.service.ConfigService;
/**
 *
 * @author bbautista
 */
public class ConfigLazy  extends LazyDataModel<Config>{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLazy.class);
    private static final long serialVersionUID = 1L;

    private Config paramConfig;
    private int totalReg;
    
    private transient ConfigService configService;
    private List<Config> listConfig;

    public ConfigLazy(){
    
    }
    
    public ConfigLazy(ConfigService configService, Config params){
        this.configService = configService;
        this.paramConfig = params;
        listConfig = new ArrayList<>();
    }
    
    @Override
    public List<Config> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {
        if(listConfig != null){
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                listConfig = configService.obtenerConfigOrdenadoPorCadena(paramConfig, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteEmisionVales. {}", ex.getMessage());
                listConfig = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listConfig = new ArrayList<>();

        }
        return listConfig;
    }
    private int obtenerTotalResultados() {
        try {
            if (paramConfig != null) {
                Long total = configService.obtenerTotalConfigOrdenadoPorCadena(paramConfig);

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
    public Config getRowData(String rowKey){
        for (Config conf : listConfig) {
            if (conf.getIdConfig().toString().equals(rowKey)) {
                return conf;
            }
        }
        return null;
    }
 
    
    @Override
    public Object getRowKey(Config object){
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
