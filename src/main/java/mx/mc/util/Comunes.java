package mx.mc.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.mc.enums.Accion_Enum;
import mx.mc.magedbean.SesionMB;
import mx.mc.model.Config;
import mx.mc.model.Folios;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;

/**
 *
 * @author hramirez
 */
public class Comunes {

    public static boolean isPaciente(Integer pacienteNumero, String nombre, String primerApellido) {
        return (pacienteNumero != null
                && pacienteNumero > 0
                && nombre != null
                && primerApellido != null);
    }

    public static String generaNumeroReceta() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar cf1 = Calendar.getInstance();
        cf1.setTime(new java.util.Date());
        long miliseg = cf1.getTimeInMillis();

        StringBuilder res = new StringBuilder();
        res.append(String.valueOf(year));
        res.append(String.valueOf(miliseg));

        return res.toString();
    }

    public static String limpia(String cadena) {
        String res = "";
        if (cadena != null) {
            res = cadena.trim();
        }
        return res;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * Método que genera UUID remplazando guiones para optimización de espacio
     * En la Base de de datos el tipo de dato debera ser binary(16)
     *
     * @return
     */
    public static String getUUID2() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * Metodo que obtiene los datos del usuario en la sesion
     *
     * @return Usuario
     */
    public static Usuario obtenerUsuarioSesion() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        return sesion.getUsuarioSelected();
    }

    public static boolean isAdministrador() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        return sesion.isAdministrador();
    }

    public static boolean isJefeArea() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        return sesion.isJefeArea();
    }

    public static boolean isValidInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metodo que Genera el Folio con los datos del objeto Folio ejemplo: Folio:
     * DR-0000000001
     *
     * @param folio
     * @return
     */
    public static String generaFolio(Folios folio) {

        String folioo = "";
        String aux = "";
        try {
            if (folio != null) {
                for (int i = 0; i < folio.getLongitud() - folio.getSecuencia().toString().length(); i++) {
                    aux += "0";
                }

                BigInteger secuencia = folio.getSecuencia().add(BigInteger.ONE);
                folioo = folio.getPrefijo() + aux + secuencia;
            }
        } catch (Exception ex) {

        }

        return folioo;
    }

    /**
     * Metodo que separa el Folio a BigInteger Ejemplo: DR-0000000001 --> 1
     *
     * @param folio
     * @return
     */
    public static BigInteger separaFolio(String folio) {
        BigInteger secuencia = BigInteger.ZERO;
        try {
            String aux = "";
            char[] arreglo = folio.toCharArray();
            for (char caracter : arreglo) {
                if (Character.isDigit(caracter)) {
                    aux += caracter;
                }
            }
            secuencia = new BigInteger(aux);
        } catch (Exception ex) {
        }
        return secuencia;
    }

    public static String obtenValorConfiguracion(List<Config> configList, String parametro) {
        String valor = "";
        if (configList != null && !configList.isEmpty()) {
            List<Config> cl = configList.stream().filter(p -> p.getNombre().equals(parametro)).collect(Collectors.toList());
            if (!cl.isEmpty() && cl.get(0).isActiva()) {
                valor = cl.get(0).getValor();
            }
        }
        return valor;
    }
    
    public static int obtenValorConfiguracionInt(List<Config> configList, String parametro) {
        int valor = -1;
        if (configList != null && !configList.isEmpty()) {
            List<Config> cl = configList.stream().filter(p -> p.getNombre().equals(parametro)).collect(Collectors.toList());
            if (!cl.isEmpty() && cl.get(0).isActiva()) {
                valor = Integer.valueOf(cl.get(0).getValor());
            }
        }
        return valor;
    }    
    
    public static Double obtenValorConfiguracionDec(List<Config> configList, String parametro) {
        Double valor = 0d;
        if (configList != null && !configList.isEmpty()) {
            List<Config> cl = configList.stream().filter(p -> p.getNombre().equals(parametro)).collect(Collectors.toList());
            if (!cl.isEmpty() && cl.get(0).isActiva()) {
                valor = Double.valueOf(cl.get(0).getValor());
            }
        }
        return valor;
    }    
    
    public static PermisoUsuario obtenerPermisos(String sufijo) {
        PermisoUsuario p = new PermisoUsuario();
        Usuario usuario = obtenerUsuarioSesion();
        if (usuario != null) {
            for (TransaccionPermisos item : usuario.getPermisosList()) {
                if (item.getCodigo().equals(sufijo)) {                    
                    switch (item.getAccion()) {
                        case "CREAR":                            
                            p.setPuedeCrear(item.getAccion().equals(Accion_Enum.CREAR.getValue()));
                            break;
                        case "VER":                            
                            p.setPuedeVer(item.getAccion().equals(Accion_Enum.VER.getValue()));
                            break;
                        case "EDITAR":                            
                            p.setPuedeEditar(item.getAccion().equals(Accion_Enum.EDITAR.getValue()));
                            break;
                        case "ELIMINAR":
                            p.setPuedeEliminar(item.getAccion().equals(Accion_Enum.ELIMINAR.getValue()));
                            break;
                        case "PROCESAR":
                            p.setPuedeProcesar(item.getAccion().equals(Accion_Enum.PROCESAR.getValue()));
                            break;
                        case "AUTORIZAR":                            
                            p.setPuedeAutorizar(item.getAccion().equals(Accion_Enum.AUTORIZAR.getValue()));
                            break;
                    }
                }
            }
        }
        return p;
    }

    public static String getPath(){
    ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolutePath = (sc.getRealPath("/"));
        String so = System.getProperty("os.name");
        if (so.toLowerCase().contains("windows")) {
            absolutePath = absolutePath + "resources\\tmp\\";
        } else {
            absolutePath = absolutePath + "resources/tmp/";
        }
        return absolutePath;
    }
    
    /**
     * Redondea Decimales de in BigDecimal a su cantidad inmediata superior si el residuo es diferente de cero
     * @param c
     * @param dp
     * @return 
     */
    private BigDecimal redondeaCantidadRequerida(BigDecimal c, BigDecimal dp) {
        BigDecimal redondeo = new BigDecimal(1);
        if (c != null && c.compareTo(BigDecimal.ZERO) == 1
                && dp != null && dp.compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal cociente = new BigDecimal(dp.divide(c).intValue());
            double mod = dp.doubleValue() % c.doubleValue();
            BigDecimal residuo = new BigDecimal(mod);
            cociente = (!residuo.equals(BigDecimal.ZERO)) ? cociente.add(BigDecimal.ONE): cociente;
            redondeo = cociente;
        }
        return redondeo;
    }
    
    
}
