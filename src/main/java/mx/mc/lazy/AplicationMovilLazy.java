/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import mx.mc.model.AplicationMovil;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ulai
 */
public class AplicationMovilLazy extends LazyDataModel<AplicationMovil>{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LazyDataModel.class);

    private List<AplicationMovil> aplicationMovilList = new ArrayList<>();
    private List<AplicationMovil> aplicationMovilListAux;
    private int totalReg;
    
    public AplicationMovilLazy() {
        //No code needed in constructor
    }

    public AplicationMovilLazy(List<AplicationMovil> aplicationMovilList) {
        this.aplicationMovilList = aplicationMovilList;
        aplicationMovilListAux = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<AplicationMovil> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {
        aplicationMovilListAux = aplicationMovilList;
        if (aplicationMovilListAux != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = aplicationMovilListAux.size();
                setRowCount(obtenerTotalResultados());
                 setTotalReg(total);
            }
            setPageSize(maxPerPage);
           
        } else {
            aplicationMovilListAux = new ArrayList<>();
        }
        if(string != null ) {
            Collections.sort(aplicationMovilListAux, new LazySorter(string, so));
        }
        return aplicationMovilListAux;
    }

    private int obtenerTotalResultados() {
         totalReg = aplicationMovilListAux.size();
        return aplicationMovilListAux.size();
    }
    
    
    

    @Override
    public AplicationMovil getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(AplicationMovil object) {
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
    
    public class LazySorter implements Comparator<AplicationMovil> {
        private final String sortField;
        private final SortOrder sortOrder;

        public LazySorter(String sortField, SortOrder sortOrder) {
            this.sortField = sortField;
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(AplicationMovil car1, AplicationMovil car2) {
            try {          
                Field field1 = car1.getClass().getDeclaredField(this.sortField);
                Field field2 = car2.getClass().getDeclaredField(this.sortField);
                field1.setAccessible(true);
                field2.setAccessible(true);
                Object value1 = field1.get(car1);
                Object value2 = field2.get(car2);

                int value = ((Comparable)value1).compareTo(value2);
                return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
            }
            catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
