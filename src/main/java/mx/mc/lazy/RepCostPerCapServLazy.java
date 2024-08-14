package mx.mc.lazy;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import mx.mc.model.DataResultReport;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepCostPerCapServLazy extends LazyDataModel<DataResultReport> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepCostPerCapServLazy.class);
    private static final long serialVersionUID = 1L;

    private List<DataResultReport> datasource;

    public RepCostPerCapServLazy(List<DataResultReport> datasource) {
        this.datasource = datasource;
    }

    @Override
    public DataResultReport getRowData(String rowKey) {
        for (DataResultReport item : datasource) {
            if (item.getIdUsuario().equals(rowKey)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DataResultReport car) {
        return car.getIdUsuario();
    }

    @Override
    public List<DataResultReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<DataResultReport> listaDatos = new ArrayList<>();

        //filter
        for (DataResultReport item : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(item.getClass().getField(filterProperty).get(item));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                listaDatos.add(item);
            }
        }

        //rowCount
        int dataSize = listaDatos.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return listaDatos.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return listaDatos.subList(first, first + (dataSize % pageSize));
            }
        }
        return listaDatos;
    }

}
