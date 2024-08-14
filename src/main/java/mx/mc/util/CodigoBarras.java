package mx.mc.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.init.Constantes;
import mx.mc.magedbean.SesionMB;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.CodigoInsumo;
import mx.mc.service.ClaveProveedorBarrasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hramirez
 */
public class CodigoBarras implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String FORMATO_FECHA_MUS = "ddMMyyyy";
    private static final String FORMATO_FECHA_GS1 = "yyMMdd";
    private static final Logger LOGGER = LoggerFactory.getLogger(CodigoBarras.class);
    private static Boolean isCodificacionGS1 = null;
    private static ClaveProveedorBarrasService skuService;

    private static boolean getCodificacionGS1() {
        if (isCodificacionGS1 == null) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            isCodificacionGS1 = sesion.isCodificacionGS1();
        }
        return isCodificacionGS1;
    }

    public static String generaCodigoDeBarras(String claveInstitucional, String lote, Date fechaCaducidad, Integer cantidad) {
        String codigo;
        try {
            if (getCodificacionGS1()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_GS1);
                String fecha = dateFormat.format(fechaCaducidad);
                String gtin = GS1Parser.AI_GTIN;
                if (cantidad != null) {
                    gtin = GS1Parser.AI_GTIN_CONTAINED;
                }
                codigo = "(" + gtin + ")" + claveInstitucional
                        + "(" + GS1Parser.AI_LOT_NUMBER + ")" + lote
                        + "(" + GS1Parser.AI_EXPIRATION_DATE + ")" + fecha;
                if (cantidad != null) {
                    codigo += "(" + GS1Parser.AI_QUANTITY + ")" + cantidad.toString();
                }
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_MUS);
                String fecha = dateFormat.format(fechaCaducidad);
                codigo = claveInstitucional + Constantes.SEPARADOR_CODIGO + lote + Constantes.SEPARADOR_CODIGO + fecha;
                if (cantidad != null) {
                    codigo += "," + cantidad.toString();
                }
            }
        } catch (Exception ex) {
            codigo = null;
            LOGGER.error("Error al generar Código de Barras: {}", ex.getMessage());
        }
        return codigo;
    }

    private static CodigoInsumo parsearCodigoDeBarrasGS1(String codigo) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_GS1);
        CodigoInsumo ci = new CodigoInsumo();
        try {
            List<GS1Parser.GS1Item> items = GS1Parser.parse(codigo);
            for (GS1Parser.GS1Item item : items) {
                switch (item.getAi()) {
                    case GS1Parser.AI_GTIN:
                    case GS1Parser.AI_GTIN_CONTAINED:
                        ci.setClave(item.getValue());
                        break;
                    case GS1Parser.AI_LOT_NUMBER:
                        ci.setLote(item.getValue());
                        break;
                    case GS1Parser.AI_EXPIRATION_DATE:
                        String dateStr = item.getValue();
                        int day = Integer.parseInt(item.getValue().substring(4));
                        if (day == 0) {
                            dateStr = dateStr.substring(0, 4) + "01";
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(dateStr));
                            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                            dateStr = dateStr.substring(0, 4) + String.format("%02d", lastDay);
                        }
                        if (dateStr.substring(0, 2).equals("99")) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(dateStr));
                            cal.set(Calendar.YEAR, 9999);
                            ci.setFecha(cal.getTime());
                        } else {
                            ci.setFecha(sdf.parse(dateStr));
                        }
                        break;
                    case GS1Parser.AI_QUANTITY:
                        ci.setCantidad(Integer.valueOf(item.getValue()));
                        break;
                    default:
                }
            }
        } catch (Exception ex) {
            ci = null;
            LOGGER.error("Error al Parsear Código de Barras GS1: {}", ex.getMessage());
        }
        return ci;
    }

    private static CodigoInsumo parsearCodigoDeBarrasMus(String codigo) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_MUS);
        try {
            CodigoInsumo ci = new CodigoInsumo();
            String[] parts = codigo.split(Constantes.SEPARADOR_CODIGO);
            ci.setClave(parts[0]);
            ci.setLote(parts[1]);
            ci.setFecha(sdf.parse(parts[2]));
            if (parts.length == 4 && Comunes.isValidInteger(parts[3])) {
                if (Integer.parseInt(parts[3]) < 0) {
                    ci.setCantidad(1);
                } else {
                    ci.setCantidad(Integer.parseInt(parts[3]));
                }
            }
            if (ci.getClave() != null && ci.getLote() != null && ci.getFecha() != null) {
                return ci;
            }
        } catch (NumberFormatException | ParseException | ArrayIndexOutOfBoundsException ex ) {
            LOGGER.error("Error al Parsear Código de Barras: {}", ex.getMessage());
        }
        return null;
    }

    public static List<CodigoInsumo> parsearCodigoDeBarrasList(List<String> codigos, Boolean isCodGS1) {
        List<CodigoInsumo> ciL = new ArrayList<>();
        try {
            for (String code : codigos) {
                CodigoInsumo ci = parsearCodigoDeBarras(code, isCodGS1);
                if (ci != null) {
                    ciL.add(ci);
                }
            }
            return ciL;
        } catch (Exception ex) {
            LOGGER.error("Error al Parsear lista de Código de Barras: {}", ex.getMessage());
        }
        return null;
    }

    public static CodigoInsumo parsearCodigoDeBarras(String codigo) {
        if (getCodificacionGS1()) {
            return parsearCodigoDeBarrasGS1(codigo);
        } else {
            return parsearCodigoDeBarrasMus(codigo);
        }
    }

    public static CodigoInsumo parsearCodigoDeBarras(String codigo, Boolean isCodGS1) {
        isCodificacionGS1 = isCodGS1;
        if (isCodificacionGS1) {
            return parsearCodigoDeBarrasGS1(codigo);
        } else {
            return parsearCodigoDeBarrasMus(codigo);
        }
    }
    
    public static ClaveProveedorBarras buscaClaveSKU(String sku ) throws Exception{
        ClaveProveedorBarras clave = null;
        try{
            clave = skuService.obtenerClave(sku);        
        }catch(Exception ex){
            throw new Exception("Error al obener clave SKU " + ex.getMessage());
        }        
        return clave;
    }
}
