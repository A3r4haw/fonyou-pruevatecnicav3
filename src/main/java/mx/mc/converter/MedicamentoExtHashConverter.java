package mx.mc.converter;

import java.util.WeakHashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.mc.model.Medicamento_Extended;
import mx.mc.util.CodigoBarras;

/**
 *
 * @author apalacios
 */
@FacesConverter(value = "medicamentoExtHashConverter")
public class MedicamentoExtHashConverter implements Converter {
    private static final WeakHashMap<String, Object> INSUMOS = new WeakHashMap<>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object insumo) {
        synchronized (INSUMOS) {
            Medicamento_Extended medExt = (Medicamento_Extended)insumo;
            if (!INSUMOS.containsKey(medExt.getClaveInstitucional())) {
                INSUMOS.put(medExt.getClaveInstitucional(), insumo);                
            }
            return medExt.getClaveInstitucional();
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String claveInstitucional) {
        return INSUMOS.get(claveInstitucional);
    }
}
