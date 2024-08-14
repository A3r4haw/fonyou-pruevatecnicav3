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
@FacesConverter(value = "medicamentoExtendedHashConverter")
public class MedicamentoExtendedHashConverter implements Converter {
    private static final WeakHashMap<String, Object> INSUMOS = new WeakHashMap<>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object insumo) {
        synchronized (INSUMOS) {
            Medicamento_Extended medExt = (Medicamento_Extended)insumo;
            String key = CodigoBarras.generaCodigoDeBarras(medExt.getClaveInstitucional(), medExt.getLote(), medExt.getFechaCaducidad(), 0);
            if (!INSUMOS.containsKey(key)) {
                INSUMOS.put(key, insumo);                
            }
            return key;
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String key) {
        return INSUMOS.get(key);
    }
}
