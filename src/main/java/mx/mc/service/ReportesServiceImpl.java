package mx.mc.service;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import mx.mc.enums.Acumulados_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Etiqueta;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.CamaExtended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.NotaDispenColect_Extended;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Paciente;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.Prescripcion;
import mx.mc.model.Reaccion;
import mx.mc.model.RepInsumoNoMinistrado;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.mapper.DiagnosticoMapper;
import mx.mc.mapper.UsuarioMapper;
import mx.mc.mapper.ViaAdministracionMapper;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.model.Diagnostico;
import mx.mc.model.FichaPrevencionExtended;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.model.Protocolo;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.ViaAdministracion;

/**
 * @author hramirez
 */
@Service
public class ReportesServiceImpl implements ReportesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportesServiceImpl.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_PARAM);
    private Usuario usuarioSession;
    private String pathJasper;

    @Qualifier("dataSource")
    @Autowired
    private DataSource datasource;
    
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private DiagnosticoMapper diagnosticoMapper;
    @Autowired
    private ViaAdministracionMapper viaAdministracionMapper;

    private boolean exportToPdfFile(String pathToPdf, String jasperFileName, Map<String, Object> parametros) {
        Connection conn = null;
        try {
            File pdf = new File(pathToPdf);
            String fullPath = ReportesServiceImpl.class.getClassLoader().getResource(jasperFileName).getPath();
            conn = datasource.getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath, parametros, conn);
            conn.close();
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdf.getPath());
            return true;
        } catch (SQLException | JRException e) {
            LOGGER.error("Error en exportPDF:", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("Error en exportToPdfFile - No se pudo cerrar la conexión:", e);
                }
            }
        }
        return false;
    }
    
    private StreamedContent getPdfStream(String jasperFileName, Map<String, Object> parametros, String pdfName) {
        Connection conn = null;
        try {
            String fullPath = ReportesServiceImpl.class.getClassLoader().getResource(jasperFileName).getPath();
            conn = datasource.getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath, parametros, conn);
            conn.close();
            byte[] buffer = JasperExportManager.exportReportToPdf(jasperPrint);
            if (buffer != null) {
                InputStream fis = new ByteArrayInputStream(buffer);
                return new DefaultStreamedContent(fis, "application/pdf; charset=UTF-8", pdfName);
            }
        } catch (SQLException | JRException ex) {
            LOGGER.error("Error en getPdfStream:", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("Error en getPdfStream - No se pudo cerrar la conexión:", e);
                }
            }
        }
        return null;
    }
    
    private byte[] getPdfBytes(String jasperFileName, Map<String, Object> parametros) {
        Connection conn = null;
        try {
            String fullPath = ReportesServiceImpl.class.getClassLoader().getResource(jasperFileName).getPath();
            conn = datasource.getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath, parametros, conn);
            conn.close();
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (SQLException | JRException ex) {
            LOGGER.error("Error en getPdfBytes:", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("Error en getPdfBytes - No se pudo cerrar la conexión:", e);
                }
            }
        }
        return null;
    }
    
    private boolean getXlsxStream(String xlsxFileName, String jasperFileName, Map<String, Object> parametros) {
        Connection conn = null;
        try {
            String fullPath = ReportesServiceImpl.class.getClassLoader().getResource(jasperFileName).getPath();
            conn = datasource.getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath, parametros, conn);
            conn.close();
            if (jasperPrint != null) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=" + xlsxFileName);
                ServletOutputStream stream = response.getOutputStream();
                System.setProperty("java.awt.headless", "true");
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                SimpleOutputStreamExporterOutput oseo = new SimpleOutputStreamExporterOutput(stream);
                exporter.setExporterOutput(oseo);
                exporter.exportReport();
                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().responseComplete();
                return true;
            }
        } catch (IOException | SQLException | JRException ex) {
            LOGGER.error("Error en getXlsxStream:", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("Error en getXlsxStream - No se pudo cerrar la conexión:", e);
                }
            }
        }
        return false;
    }
    
    private String getListaMedicamentosAsString(List<Medicamento> listaMedicamentos, String campo) {
        if (listaMedicamentos == null || listaMedicamentos.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();
        for (short i = 0; i < listaMedicamentos.size(); i++) {
            Medicamento medicamento = listaMedicamentos.get(i);
            if (campo.equals("CLAVEINSTITUCIONAL"))
                cadena.append("'").append(medicamento.getClaveInstitucional()).append("'");
            else
                cadena.append("'").append(medicamento.getIdMedicamento()).append("'");
            if (i < (listaMedicamentos.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString();
    }
    
    private String getListaUsuariosAsString(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null || listaUsuarios.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();
        for (short i = 0; i < listaUsuarios.size(); i++) {
            Usuario usuario = listaUsuarios.get(i);
            cadena.append("'").append(usuario.getIdUsuario()).append("'");
            if (i < (listaUsuarios.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString();
    }
    
    private String getListaNombreUsuariosAsString(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null || listaUsuarios.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();        
        for (short i = 0; i < listaUsuarios.size(); i++) {
            Usuario usuario = listaUsuarios.get(i);
            cadena.append("'").append(usuario.getNombre());
            if(!usuario.getApellidoPaterno().isEmpty()) {
                cadena.append(" ").append(usuario.getApellidoPaterno());
            }
            if(!usuario.getApellidoMaterno().isEmpty()) {
                cadena.append(" ").append(usuario.getApellidoMaterno());
            }
            cadena.append("'");
            if (i < (listaUsuarios.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString();
    }
    
    private String getListaIdsAsString(List<Integer> listaIds) {
        if (listaIds == null || listaIds.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();
        for (short i = 0; i < listaIds.size(); i++) {
            cadena.append("'").append(listaIds.get(i)).append("'");
            if (i < (listaIds.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString();
    }
    
    private String getListaPacientesAsString(List<Paciente> listaPacientes, String campo) {
        if (listaPacientes == null || listaPacientes.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();
        for (short i = 0; i < listaPacientes.size(); i++) {
            Paciente paciente = listaPacientes.get(i);
            switch (campo) {
                case "NOMBRE_PACIENTE":
                    cadena.append("'").append(paciente.getNombreCompleto()).append("'");
                    break;
                case "APELLIDO_PATERNO":
                    cadena.append("'").append(paciente.getApellidoPaterno()).append("'");
                    break;
                case "APELLIDO_MATERNO":
                    cadena.append("'").append(paciente.getApellidoMaterno()).append("'");
                    break;
                default:
                    cadena.append("'").append(paciente.getIdPaciente()).append("'");
                    break;
            }
            if (i < (listaPacientes.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString();
    }
    
    private String getListaEstructurasAsString(List<String> listaEstructuras) {
        if (listaEstructuras == null || listaEstructuras.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();
        for (short i = 0; i < listaEstructuras.size(); i++) {
            cadena.append("'").append(listaEstructuras.get(i)).append("'");
            if (i < (listaEstructuras.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString().replace("\"", "");
    }
    
    private String getListaNoMinistradosAsString(List<RepInsumoNoMinistrado> listaNoMinistrados) {
        if (listaNoMinistrados == null || listaNoMinistrados.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();
        for (short i = 0; i < listaNoMinistrados.size(); i++) {
            RepInsumoNoMinistrado insumo = listaNoMinistrados.get(i);
            cadena.append("'").append(insumo.getNombreCorto()).append("'");
            if (i < (listaNoMinistrados.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString();
    }
    
    private String getListaCapsulasAsString(List<EnvioNeumatico> listaCapsulas) {
        if (listaCapsulas == null || listaCapsulas.isEmpty())
            return null;
        StringBuilder cadena = new StringBuilder();
        for (short i = 0; i < listaCapsulas.size(); i++) {
            EnvioNeumatico capsula = listaCapsulas.get(i);
            cadena.append("'").append(capsula.getIdCapsula()).append("'");
            if (i < (listaCapsulas.size() - 1)) {
                cadena.append(",");
            }
        }
        return cadena.toString();
    }

    @Override
    public StreamedContent generaReportePorRequerimientoNo(
            Integer requerimientoNo, String nombreAlmacen, String requerimientoFecha, String requerimientoFolio, String isGabinete
    ) throws Exception {
        LOGGER.trace("mx.mc.service.ReportesServiceImpl.generaReportePorRequerimientoNo()");
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.jpg").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.jpg").getPath();
        String titulo1 = RESOURCES.getString("TITULO1");
        String titulo2 = RESOURCES.getString("TITULO2");
        String titulo3 = RESOURCES.getString("TITULO3");
        String titulo4 = RESOURCES.getString("TITULO4");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("NOMBRE_ALMACEN", nombreAlmacen);
        parametros.put("REQUERIMIENTO_NO", requerimientoNo);
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "");
        parametros.put("TITULO4", (titulo4 != null) ? titulo4 : "");
        parametros.put("TITULO5", (nombreAlmacen != null) ? nombreAlmacen : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("REQUERIMIENTO_FECHA", (requerimientoFecha != null) ? requerimientoFecha : "");
        parametros.put("REQUERIMIENTO_FOLIO", (requerimientoFolio != null) ? requerimientoFolio : "");
        parametros.put("ISGABINETE", (isGabinete != null) ? isGabinete : "0");
        return getPdfStream("jasper/reabasto.jasper", parametros, "Orden_Reabasto_" + requerimientoFolio + ".pdf");
    }

    @Override
    public StreamedContent generaReporteDevolucion(
            Integer requerimientoNo, String nombreAlmacen, String requerimientoFecha, String requerimientoFolio, String isGabinete
    ) throws Exception {
        LOGGER.trace("mx.mc.service.ReportesServiceImpl.generaReporteDevolucion()");
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.jpg").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.jpg").getPath();
        String titulo1 = RESOURCES.getString("TITULO1");
        String titulo2 = RESOURCES.getString("TITULO2");
        String titulo3 = RESOURCES.getString("TITULO3");
        String titulo4 = RESOURCES.getString("TITULO6");
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("REQUERIMIENTO_NO", requerimientoNo);
        parametros.put("NOMBRE_ALMACEN", nombreAlmacen);
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "");
        parametros.put("TITULO4", (titulo4 != null) ? titulo4 : "");
        parametros.put("TITULO5", (nombreAlmacen != null) ? nombreAlmacen : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("ISGABINETE", (isGabinete != null) ? isGabinete : "0");
        parametros.put("REQUERIMIENTO_FECHA", (requerimientoFecha != null) ? requerimientoFecha : "");
        parametros.put("REQUERIMIENTO_FOLIO", (requerimientoFolio != null) ? requerimientoFolio : "");
        return getPdfStream("jasper/devolucion.jasper", parametros, "Orden_Devolucion_" + requerimientoFolio + ".pdf");
    }

    @Override
    public byte[] reporteSolicitudReabasto(Reabasto reabasto, int idTipoAlmacen, String nombreUsuario, String claveUsuario) throws Exception {
        LOGGER.trace("mx.mc.service.ReportesServiceImpl.reporteSolicitudReabasto()");
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloSolReabasto = RESOURCES.getString("SOLICITUDREABASTO");
        String titulo2 = reabasto.getNombreEntidad();
        String titulo3 = reabasto.getDomicilio();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("Almacen", reabasto.getAlmacen());
        parametros.put("Proveedor", reabasto.getProveedor());
        parametros.put("idTipoAlmacen", idTipoAlmacen);
        parametros.put("TITULO1", (tituloSolReabasto != null) ? tituloSolReabasto : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("IDREABASTO", reabasto.getIdReabasto());
        parametros.put("REQUERIMIENTO_FECHA", reabasto.getFechaSolicitud());
        parametros.put("REQUERIMIENTO_FOLIO", reabasto.getFolio());
        parametros.put("NOMBRE_USUARIO", nombreUsuario);
        parametros.put("CLAVE_USUARIO", claveUsuario);
        parametros.put("IDFOLIOALTERNATIVO", "");
        parametros.put("USER_NAME", "");// Se comenta ya que solo se solicita la orden, todavia no se recibe((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        
        return getPdfBytes("jasper/reporteSolicitudReabasto.jasper", parametros);
    }

    @Override
    public byte[] reporteSolicitudReabasto(Reabasto reabasto, List<FolioAlternativoFolioMus> listFolioAlternativos, int idTipoAlmacen, String nombreUsuario, String claveUsuario) throws Exception {
        LOGGER.trace("mx.mc.service.ReportesServiceImpl.reporteSolicitudReabasto()");
        byte[] buffer = null;
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titSolicitudReabasto = RESOURCES.getString("SOLICITUDREABASTO");
        String titulo2 = reabasto.getNombreEntidad();
        String titulo3 = reabasto.getDomicilio();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("Almacen", reabasto.getAlmacen());
        parametros.put("Proveedor", reabasto.getProveedor());
        parametros.put("idTipoAlmacen", idTipoAlmacen);
        parametros.put("TITULO1", (titSolicitudReabasto != null) ? titSolicitudReabasto : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("NOMBRE_USUARIO", nombreUsuario);
        parametros.put("CLAVE_USUARIO", claveUsuario);
        parametros.put("REQUERIMIENTO_FECHA", reabasto.getFechaSolicitud());
        parametros.put("REQUERIMIENTO_FOLIO", reabasto.getFolio());
        parametros.put("IDREABASTO", reabasto.getIdReabasto());
        Connection conn = null;
        try {
            String fullPath = ReportesServiceImpl.class.getClassLoader().getResource("jasper/reporteSolicitudReabasto.jasper").getPath();
            JasperPrint jasperPrint1 = null;
            conn = datasource.getConnection();
            for (int i = 0; i < listFolioAlternativos.size(); i++) {
                parametros.put("FOLIO_ALTERNATIVO", listFolioAlternativos.get(i).getFolioAlternativo());
                parametros.put("IDFOLIOALTERNATIVO", listFolioAlternativos.get(i).getIdFolioAlternativo());                
                JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath, parametros, conn);
                if (i == 0) {
                    jasperPrint1 = jasperPrint;
                } else {
                    for (int j = 0; j < jasperPrint.getPages().size(); j++) {
                        jasperPrint1.addPage(jasperPrint.getPages().get(j));
                    }
                }
            }
            conn.close();
            buffer = JasperExportManager.exportReportToPdf(jasperPrint1);;

        } catch (JRException e) {
            LOGGER.error("Error en generar reporteSolicitudReabasto: ", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("Error en reporteSolicitudReabasto - No se pudo cerrar la conexión:", e);
                }
            }
        }
        return buffer;
    }

    @Override
    public byte[] reporteSolicitudColectivo(Reabasto reabasto, List<Reabasto> colectivos, int idTipoAlmacen, String nombreUsuario, String claveUsuario) throws Exception {
        LOGGER.trace("mx.mc.service.ReportesServiceImpl.reporteSolicitudReabasto()");
        byte[] buffer = null;
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();

        String titulo1 = RESOURCES.getString("SOLICITUDREABASTO");
        String titulo2 = reabasto.getNombreEntidad();
        String titulo3 = reabasto.getDomicilio();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("PROVEEDOR", reabasto.getProveedor());
        parametros.put("idTipoAlmacen", idTipoAlmacen);
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("NOMBRE_USUARIO", nombreUsuario);
        parametros.put("CLAVE_USUARIO", claveUsuario);
        parametros.put("REQUERIMIENTO_FECHA", reabasto.getFechaSolicitud());
        parametros.put("REQUERIMIENTO_FOLIO", reabasto.getFolio());
        parametros.put("IDREABASTO", reabasto.getIdReabasto());
        parametros.put("TIPOREABASTO",reabasto.getIdTipoOrden());
        Connection conn = null;
        try {            
            String fullPath = ReportesServiceImpl.class.getClassLoader().getResource("jasper/repSolicitudReabastoXServicio.jasper").getPath();
            JasperPrint jasperPrint1 = null;
            int i=0;
            conn = datasource.getConnection();
            for (Reabasto colectivo : colectivos) {
                parametros.put("ALMACEN",reabasto.getIdTipoOrden() == Constantes.TIPO_ORDEN_EXTRA ? reabasto.getAlmacen():colectivo.getAlmacen());
                parametros.put("DESTINO", colectivo.getAlmacen());
                parametros.put("IDESTRUCTURA", colectivo.getIdEstructura());
                parametros.put("FOLIO_ALTERNATIVO", colectivo.getFolio());                
                JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath, parametros, conn);
                if (i == 0) {
                    jasperPrint1 = jasperPrint;
                } else {
                    for (int j = 0; j < jasperPrint.getPages().size(); j++) {
                        jasperPrint1.addPage(jasperPrint.getPages().get(j));
                    }
                }
                i++;
            }
            conn.close();
            buffer = JasperExportManager.exportReportToPdf(jasperPrint1);
        } catch (JRException e) {
            LOGGER.error("Error al generar reporteSolicitudColectivo: ",e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("Error en reporteSolicitudColectivo - No se pudo cerrar la conexión:", e);
                }
            }
        }
        return buffer;
    }

    @Override
    public byte[] imprimePrescripcion(Paciente_Extended pa, CamaExtended ce, Usuario u, Paciente_Extended pe, Prescripcion_Extended p) throws Exception {
        LOGGER.trace("mx.mc.service.ReportesServiceImpl.imprimePrescripcion()");
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1");
        String titulo2 = RESOURCES.getString("TITULO2");
        String titulo3 = RESOURCES.getString("DIRECCION1");
        String tituloReceta = RESOURCES.getString("TIT_RECETA");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FOLIO", p.getFolio());
        parametros.put("FECHA", p.getFechaFirma());
        parametros.put("ESTATUS", p.getEstatusPrescripcion());
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("TIT_RECETA", (tituloReceta != null) ? tituloReceta : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("IDREABASTO", p.getIdPrescripcion());
        parametros.put("REQUERIMIENTO_FECHA", p.getFechaPrescripcion());
        parametros.put("REQUERIMIENTO_FOLIO", p.getFolio());
        parametros.put("MEDICO", p.getNombreMedico());
        parametros.put("EDAD", pe.getEdadPaciente());
        parametros.put("SERVICIO", pa.getNombreEstructura());
        if (ce != null) {
            parametros.put("CAMA", (ce.getNombreCama() != null) ? ce.getNombreCama() : "");
        } else {
            parametros.put("CAMA", "Sin cama");
        }
        String val = Character.toString(pe.getSexo());
        parametros.put("SEXO", val);
        parametros.put("NOMBRE_COMPLETO", pe.getNombreCompleto() + " " + pe.getApellidoPaterno() + " " + pe.getApellidoMaterno());
        parametros.put("IDPACIENTE", pe.getIdPaciente());
        parametros.put("CLV_HABIENCIA", pe.getClaveDerechohabiencia());
        parametros.put("DERECHO_HABIENCIA", pe.getTipoPaciente());
        parametros.put("NUM_PACIENTE", pe.getPacienteNumero());
        parametros.put("NOTAS", p.getComentarios());
        parametros.put("IDPRESCRIPCION", p.getIdPrescripcion());
        parametros.put("CEDULA", u.getCedProfesional());
        parametros.put("ESPECIALIDAD", u.getCedEspecialidad());
        
        return getPdfBytes("jasper/prescripciones.jasper", parametros);
        
    }

    @Override
    public boolean imprimeSurtimiento(Surtimiento_Extend s, String pathTmp, String url) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1");
        String titulo2 = RESOURCES.getString("TITULO2");
        String direccion1 = RESOURCES.getString("DIRECCION1");
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FOLIO", s.getFolio());
        parametros.put("FECHA", s.getFechaProgramada());
        parametros.put("ESTATUS", s.getEstatusSurtimiento());
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (direccion1 != null) ? direccion1 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("IDREABASTO", s.getIdPrescripcion());
        parametros.put("REQUERIMIENTO_FECHA", s.getFechaProgramada());
        parametros.put("REQUERIMIENTO_FOLIO", s.getFolio());
        return exportToPdfFile(pathTmp, "jasper/surtimiento.jasper", parametros);
    }

    @Override
    public boolean imprimeSurtimientoPrescExt(
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario,
            String pathTmp, String url) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        //TODO esta informacion se obtendra de a cuerdo a su estructura
        if (Constantes.IDESTRUCTURA_EXTERNA_CLINICA.equals(surtimientoExtendedSelected.getIdEstructuraAlmacen())) {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_CLINICA);
        } else {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_HOSPITAL);
        }
        parametros.put("hora", "" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        parametros.put("fecha", "" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parametros.put("usuario", nombreUsuario);
        parametros.put("folioReceta", surtimientoExtendedSelected.getFolioPrescripcion());
        parametros.put("cveDerechohabiente", surtimientoExtendedSelected.getPacienteNumero());
        parametros.put("nombreMedico", surtimientoExtendedSelected.getNombreMedico());
        parametros.put("folioSurtimiento", surtimientoExtendedSelected.getFolio());
        return exportToPdfFile(pathTmp, "jasper/ticketSurtimiento.jasper", parametros);
    }

    @Override
    public byte[] imprimeSurtimientoPrescInt(
            RepSurtimientoPresc repSurtimientoPresc,
            String estatusSurtimiento,
            Integer cantIsumos) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FOLIO_PRESC", repSurtimientoPresc.getFolioPrescripcion());
        parametros.put("FOLIO_SURT", repSurtimientoPresc.getFolioSurtimiento());
        parametros.put("FECHA_ACTUAL", repSurtimientoPresc.getFechaActual());
        parametros.put("NOMBRE_PACIENTE", repSurtimientoPresc.getNombrePaciente());
        parametros.put("CLAVE_PACIENTE", repSurtimientoPresc.getClavePaciente());
        parametros.put("SERVICIO", repSurtimientoPresc.getServicio());
        parametros.put("CAMA", repSurtimientoPresc.getCama());
        parametros.put("PISO", repSurtimientoPresc.getPiso());
        parametros.put("NOMBRE_SERVICIO_COPIA", repSurtimientoPresc.getNombreCopia());
        parametros.put("IDESTATUSSURTIMIENTO", repSurtimientoPresc.getIdEstatusSurtimiento());
        parametros.put("ESTATUS_SURTIMIENTO", estatusSurtimiento);
        parametros.put("CANT_MEDICAMENTOS", cantIsumos);
        parametros.put("FECHA_SOLICITADO", repSurtimientoPresc.getFechaSolicitado());
        parametros.put("FECHA_ATENDIDO", repSurtimientoPresc.getFechaAtendido());
        parametros.put("TURNO", repSurtimientoPresc.getTurno());
        return getPdfBytes("jasper/repSurtPresc.jasper", parametros);
    }

    @Override
    public boolean imprimeSurtPresManualHosp(
            RepSurtimientoPresc repSurtimientoPresc,
            String pathTmp,
            String url,
            String estatusSurtimiento,
            Integer cantIsumos) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FOLIO_PRESC", repSurtimientoPresc.getFolioPrescripcion());
        parametros.put("FOLIO_SURT", repSurtimientoPresc.getFolioSurtimiento());
        parametros.put("FECHA_ACTUAL", repSurtimientoPresc.getFechaActual());
        parametros.put("NOMBRE_PACIENTE", repSurtimientoPresc.getNombrePaciente());
        parametros.put("CLAVE_PACIENTE", repSurtimientoPresc.getClavePaciente());
        parametros.put("SERVICIO", repSurtimientoPresc.getServicio());
        parametros.put("CAMA", repSurtimientoPresc.getCama());
        parametros.put("PISO", "piso");
        parametros.put("ESTATUS_SURTIMIENTO", estatusSurtimiento);
        parametros.put("CANT_MEDICAMENTOS", cantIsumos);
        parametros.put("FECHA_SOLICITADO", repSurtimientoPresc.getFechaSolicitado());
        parametros.put("FECHA_ATENDIDO", repSurtimientoPresc.getFechaAtendido());
        parametros.put("TURNO", "turno");
        return exportToPdfFile(pathTmp, "jasper/repSurtPresc.jasper", parametros);
    }

    @Override
    public byte[] imprimeSurtimientoPrescManual(
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        //TODO esta informacion se obtendra de a cuerdo a su estructura
        if (Constantes.IDESTRUCTURA_EXTERNA_CLINICA.equals(surtimientoExtendedSelected.getIdEstructuraAlmacen())) {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_CLINICA);
        } else {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_HOSPITAL);
        }
        parametros.put("folioReceta", surtimientoExtendedSelected.getFolioPrescripcion());
        parametros.put("hora", "" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        parametros.put("fecha", "" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parametros.put("usuario", nombreUsuario);
        parametros.put("cveDerechohabiente", surtimientoExtendedSelected.getPacienteNumero());
        parametros.put("nombreMedico", surtimientoExtendedSelected.getNombreMedico());
        parametros.put("folioSurtimiento", surtimientoExtendedSelected.getFolio());
        
        return getPdfBytes("jasper/ticketSurtimiento.jasper", parametros);
    }

    @Override
    public byte[] imprimeSurtimientoPrescManualChiconcuac(Prescripcion prescripcion,
            EntidadHospitalaria entidadHospitalaria,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        //TODO esta informacion se obtendra de a cuerdo a su estructura
        /*if (Constantes.IDESTRUCTURA_EXTERNA_CLINICA.equals(surtimientoExtendedSelected.getIdEstructuraAlmacen())) {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_CLINICA);
        } else {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_HOSPITAL);
        }*/
        parametros.put("tituloCabecera", entidadHospitalaria.getNombre());
        parametros.put("tituloCabecera2", entidadHospitalaria.getDomicilio());
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("hora", "" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        parametros.put("fecha", "" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parametros.put("fechaReceta", "" + new SimpleDateFormat("dd/MM/yyyy").format(prescripcion.getFechaPrescripcion()));
        parametros.put("usuario", nombreUsuario);
        parametros.put("cveDerechohabiente", surtimientoExtendedSelected.getClaveDerechohabiencia());
        parametros.put("nombreMedico", surtimientoExtendedSelected.getNombreMedico());
        parametros.put("nombrePaciente", surtimientoExtendedSelected.getNombrePaciente());
        parametros.put("folioReceta", prescripcion.getFolio());
        parametros.put("folioSurtimiento", surtimientoExtendedSelected.getFolio());
        parametros.put("consecutivo", surtimientoExtendedSelected.getFolio().substring(3));
        
        return getPdfBytes("jasper/ticketSurtimientoChiconcuac.jasper", parametros);
    }

    @Override
    public byte[] imprimeSurtimientoPrescColectivaChiconcuac(Prescripcion prescripcion,
            EntidadHospitalaria entidadHospitalaria,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception {

        Map<String, Object> parametros = new HashMap<>();

        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        parametros.put("tituloCabecera", entidadHospitalaria.getNombre());
        parametros.put("tituloCabecera2", entidadHospitalaria.getDomicilio());
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("folioReceta", prescripcion.getFolio());
        parametros.put("hora", "" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        parametros.put("fecha", "" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parametros.put("fechaReceta", "" + new SimpleDateFormat("dd/MM/yyyy").format(prescripcion.getFechaPrescripcion()));
        parametros.put("usuario", nombreUsuario);
        parametros.put("cveDerechohabiente", "");
        parametros.put("nombreMedico", surtimientoExtendedSelected.getNombreMedico());
        parametros.put("nombrePaciente", surtimientoExtendedSelected.getNombrePaciente());
        parametros.put("folioSurtimiento", surtimientoExtendedSelected.getFolio());
        parametros.put("consecutivo", surtimientoExtendedSelected.getFolio().substring(3));
        parametros.put("SUBREPORT_DIR", "/jasper/");
        
        return getPdfBytes("jasper/ticketSurtimientoColectSub_2.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteColectivaChiconcuac(Prescripcion prescripcion,
            EntidadHospitalaria entidadHospitalaria,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        //todo Queda pendiente ver el logo que va aparecer, asi como en reporte esta en duro el nombre del Hospital
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        parametros.put("tituloCabecera", entidadHospitalaria.getNombre());
        parametros.put("tituloCabecera2", entidadHospitalaria.getDomicilio());
        parametros.put("LOGO", (rutaLogo != null) ? rutaLogo : null);
        parametros.put("LOGO2", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("folioReceta", prescripcion.getFolio());
        parametros.put("hora", "" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        parametros.put("fecha", "" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parametros.put("fechaReceta", "" + new SimpleDateFormat("dd/MM/yyyy").format(prescripcion.getFechaPrescripcion()));
        parametros.put("usuario", nombreUsuario);
        parametros.put("cveDerechohabiente", "");
        parametros.put("nombreMedico", surtimientoExtendedSelected.getNombreMedico());
        parametros.put("nombrePaciente", surtimientoExtendedSelected.getNombrePaciente());
        parametros.put("consecutivo", surtimientoExtendedSelected.getFolio().substring(3));
        parametros.put("folioSurtimiento", surtimientoExtendedSelected.getFolio());
        parametros.put("medico", surtimientoExtendedSelected.getNombreMedico());
        
        return getPdfBytes("jasper/repColectivos.jasper", parametros);
    }

    @Override
    public boolean imprimeSurtimientoVales(
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario,
            String pathTmp, String url) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        //TODO esta informacion se obtendra de a cuerdo a su estructura
        if (Constantes.IDESTRUCTURA_EXTERNA_CLINICA.equals(surtimientoExtendedSelected.getIdEstructuraAlmacen())) {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_CLINICA);
        } else {
            parametros.put("tituloCabecera", Constantes.DIRECCION_EXTERNA_HOSPITAL);
        }
        parametros.put("folioReceta", surtimientoExtendedSelected.getFolioPrescripcion());
        parametros.put("hora", "" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        parametros.put("fecha", "" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        parametros.put("usuario", nombreUsuario);
        parametros.put("cveDerechohabiente", surtimientoExtendedSelected.getClaveDerechohabiencia());
        parametros.put("nombreMedico", surtimientoExtendedSelected.getNombreMedico());
        parametros.put("folioSurtimiento", surtimientoExtendedSelected.getFolio());
        return exportToPdfFile(pathTmp, "jasper/valeSurtimiento.jasper", parametros);
    }

    @Override
    public byte[] imprimirOrdenRecibir(ReabastoExtended p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("RECIBIRORDEN");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FECHA", p.getFechaSolicitud());
        parametros.put("ESTATUS", p.getEstatus());
        parametros.put("FOLIO", p.getFolio());
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("IDREABASTO", p.getIdReabasto());
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("Proveedor", p.getNombreProveedor());
        parametros.put("REQUERIMIENTO_FECHA", p.getFechaSolicitud());
        parametros.put("REQUERIMIENTO_FOLIO", p.getFolio());
        parametros.put("REQUERIMIENTO_USER_ENTREGA", p.getNombreUsuarioSurte());
        parametros.put("REQUERIMIENTO_USER_RECIBE", p.getNombreUsrRecibe());
        
        return getPdfBytes("jasper/recepcionOrden.jasper", parametros);        
    }

    @Override
    public byte[] imprimirOrdenIngresar(ReabastoExtended p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1_REP_INGRESO");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("FECHA", p.getFechaSolicitud());
        parametros.put("FOLIO", p.getFolio());
        parametros.put("ESTATUS", p.getEstatus());        
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("IDREABASTO", p.getIdReabasto());
        parametros.put("REQUERIMIENTO_FOLIO", p.getFolio());
        parametros.put("REQUERIMIENTO_FECHA", p.getFechaSolicitud());
        parametros.put("REQUERIMIENTO_FECHA_ENTREGA", p.getFechaIngresoInventario());
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("Proveedor", p.getNombreProveedor());
        parametros.put("REQUERIMIENTO_USER_ENTREGA", p.getNombreUsuarioSurte());
        parametros.put("REQUERIMIENTO_USER_RECIBE", p.getNombreUsrRecibe());
        
        return getPdfBytes("jasper/recepcionIngreso.jasper", parametros);
    }

    @Override
    public byte[] imprimirOrdenSurtir(ReabastoExtended p, EntidadHospitalaria entidad, String titulo) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();        
        String titulo1 = titulo;
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        String matricula = p.getMatriculaPersonal();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FOLIO", p.getFolio());
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("FECHA", p.getFechaSurtida());
        parametros.put("ESTATUS", p.getEstatus());
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("IDREABASTO", p.getIdReabasto());
        parametros.put("REQUERIMIENTO_FECHA", p.getFechaSolicitud());
        parametros.put("REQUERIMIENTO_FOLIO", p.getFolio());
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("Proveedor", p.getNombreProveedor());
        parametros.put("NOMBRE_USUARIO", (p.getNombreUsuarioSurte() != null) ? p.getNombreUsuarioSurte() : "");
        parametros.put("USER_NAME", (p.getNombreUsrRecibe() != null) ? p.getNombreUsrRecibe() : "");
        parametros.put("CLAVE_USUARIO", (matricula != null && p.getNombreUsuarioSurte() != null) ? matricula : "");
        
        return getPdfBytes("jasper/surtimiento.jasper", parametros);
    }

    @Override
    public byte[] imprimePulcera(Paciente_Extended p) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1");
        String titulo2 = RESOURCES.getString("TITULO2");
        String titulo3 = RESOURCES.getString("DIRECCION1");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FOLIO", p.getClaveDerechohabiencia());
        parametros.put("FECHA", p.getFechaNacimiento());
        parametros.put("ESTATUS", p.getEstatusPaciente());
        parametros.put("IDREABASTO", "858575");
        parametros.put("REQUERIMIENTO_FECHA", new Date());
        parametros.put("REQUERIMIENTO_FOLIO", "78978fuu");
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        return getPdfBytes("jasper/prescripcion.jasper", parametros);
    }

    @Override
    public boolean imprimirEtiqueta(Etiqueta etiqueta) throws Exception {
        boolean result = false;
        Socket socket;

        String nombrePaciente = etiqueta.getNombrePaciente();
        String numeroPaciente = etiqueta.getNumeroPaciente();
        String clavePaciente = etiqueta.getClavePaciente();
        String titulo = etiqueta.getTitulo();
        String fechaHora = etiqueta.getFechaHora();

        String mensaje = "CT~~CD,~CC^~CT~\n"
                + "^XA~TA000~JSN^LT0^MNM^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD21^JUS^LRN^CI0^XZ\n"
                + "^XA\n"
                + "^MMT\n"
                + "\n"
                + "\n"
                + "^PW300\n"
                + "^LL2100\n"
                + "^LS0\n"
                + "^FT40,378^BQN,2,7\n"
                + "^FH\\^FDMA," + numeroPaciente + "^FS\n"
                + "^FT122,432^A0R,58,57^FH\\^FD" + nombrePaciente + "^FS\n"
                + "^FT144,1118^A0R,37,36^FH\\^FD" + clavePaciente + " " + numeroPaciente + "^FS\n"
                + "^FT238,432^A0R,42,40^FH\\^FD" + titulo + "^FS\n"
                + "^FT242,935^A0R,33,33^FH\\^FD" + fechaHora + "^FS\n"
                + "^PQ1,0,1,Y^XZ";

        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(etiqueta.getIpImpresora(), 9100), 1000);
            if (socket.isConnected()) {
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                output.writeBytes(mensaje);
                socket.close();
                result = true;
            } else {
                result = false;
            }
        } catch (IOException ex) {
            LOGGER.error("Error en metodo ImprimirEtiqueta de clase ReporteServiceImpl: {}", ex.getMessage());
        } finally {
            socket.close();
        }
        return result;
    }

    @Override
    public boolean imprimirEtiquetaItem(EtiquetaInsumo etiqueta) throws Exception {
        boolean resp = false;
        Socket socket;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");
        String caducidadQR = simpleDateFormat.format(etiqueta.getCaducidad());
        String caducidad = simpleDateFormat1.format(etiqueta.getCaducidad());

        String mensaje = etiqueta.getTemplate();

        mensaje = mensaje.replace("CLAVE$", etiqueta.getClave() != null ? etiqueta.getClave() : "");
        mensaje = mensaje.replace("LOTE$", etiqueta.getLote() != null ? etiqueta.getLote() : "");
        mensaje = mensaje.replace("CADUCIDADQR$", caducidadQR);
        mensaje = mensaje.replace("CADUCIDAD$", caducidad);
        mensaje = mensaje.replace("ORIGEN$", etiqueta.getOrigen() != null ? etiqueta.getOrigen() : "");
        mensaje = mensaje.replace("LABORATORIO$", etiqueta.getLaboratorio() != null ? etiqueta.getLaboratorio() : "");
        mensaje = mensaje.replace("FOTOSENSIBLE$", etiqueta.getFotosencible() != null ? etiqueta.getFotosencible() : "");
        mensaje = mensaje.replace("TEXTOINSTITUCIONAL$", etiqueta.getTextoInstitucional() != null ? etiqueta.getTextoInstitucional() : "");
        mensaje = mensaje.replace("DESCRIPCION$", etiqueta.getDescripcion() != null ? etiqueta.getDescripcion() : "");
        mensaje = mensaje.replace("CODIGOQR$", etiqueta.getCodigoQR() != null ? etiqueta.getCodigoQR() : "");
        mensaje = mensaje.replace("CONCENTRACION$", etiqueta.getConcentracion() != null ? etiqueta.getConcentracion().toString() : "");
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(etiqueta.getIpPrint(), 9100), 1000);
            if (socket.isConnected()) {
                for (int i = 0; i < etiqueta.getCantiad(); i++) {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeBytes(mensaje);
                }
                socket.close();
                resp = true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            LOGGER.error("Error en imprimir etiqueta: {}", ex.getMessage());
        } finally {
            socket.close();
        }
        return resp;
    }
    
    @Override
    public boolean imprimirEtiquetaCM(TemplateEtiqueta te, Integer cant, String ip) throws Exception {
        boolean resp = false;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, 9100), 1000);
            if (socket.isConnected()) {
                for (int i = 0; i < cant; i++) {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    String cont = te.getContenido().replace("#NUM_ETQ#", String.valueOf(i + 1));
                    out.writeBytes(cont);
                }
                socket.close();
                resp = true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            LOGGER.error("Error al imprimir etiqueta: {}", ex.getMessage());
            throw new Exception("Error al imprimir etiqueta: " + ex.getMessage());
        } finally {
            socket.close();
        }
        return resp;
    }

    @Override
    public byte[] imprimeReporteExistencias(ParamBusquedaReporte p, EntidadHospitalaria entidad, boolean hospChicon) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1_REP_EXIST");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("listInsumos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("sortField", p.getSortField());
        parametros.put("sortOrder", p.getSortOrder());
        
        if (hospChicon)
            return getPdfBytes("jasper/re_existencias.jasper", parametros);
        return getPdfBytes("jasper/re_existenciasT.jasper", parametros);        
    }

    @Override
    public byte[] imprimeReporteExistenciasMostarndoColumnas(ParamBusquedaReporte p, EntidadHospitalaria entidad,
            boolean mostrarOrigenInsumos, boolean mostrarClaveProveedor, boolean mostrarUnidosis, boolean mostrarCosteLote) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExistencias = RESOURCES.getString("TITULO1_REP_EXIST");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExistencias != null) ? tituloExistencias : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("listInsumos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("sortField", p.getSortField());
        parametros.put("sortOrder", p.getSortOrder());
        parametros.put("mostrarOrigenInsumos", mostrarOrigenInsumos);
        parametros.put("mostrarClaveProveedor", mostrarClaveProveedor);
        parametros.put("mostrarUnidosis", mostrarUnidosis);
        parametros.put("mostrarCosteLote", mostrarCosteLote);
        parametros.put("MOSTRARCEROS", p.getCantidadCero());
        parametros.put("USER_NAME", p.getUsuarioGenera().getNombre() +" "+ p.getUsuarioGenera().getApellidoPaterno() +" "+ p.getUsuarioGenera().getApellidoMaterno());
        return getPdfBytes("jasper/re_existencias.jasper", parametros);
//        return getPdfBytes("jasper/re_existencias_columnas.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteMovGenerales(ParamBusquedaReporte p, EntidadHospitalaria entidad, boolean mostrarOrigenInsumos) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloRepMovGen = RESOURCES.getString("TITULO1_REP_MOV_GEN");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloRepMovGen != null) ? tituloRepMovGen : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("mostrarOrigenInsumos", mostrarOrigenInsumos);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaTipoMov", getListaIdsAsString(p.getIdTipoMovimientos()));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
		parametros.put("nombreUsuarioGenera", p.getUsuarioGenera().getNombre() +" "+ p.getUsuarioGenera().getApellidoPaterno() +" "+ p.getUsuarioGenera().getApellidoMaterno());
		
        if (p.isActivaCamposReporteMovimientosGenerales())
            return getPdfBytes("jasper/repMovGeneralesCh.jasper", parametros);
        return getPdfBytes("jasper/repMovGenerales.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteLibroControlados(ParamLibMedControlados p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1_REP_LIB_MED_CONT");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idMedicamento", (p.getIdMedicamento() != null) ? p.getIdMedicamento() : null);
        parametros.put("idInventario", (p.getIdInventario() != null) ? p.getIdInventario() : null);
        parametros.put("idEstructura", (p.getIdEstructura() != null) ? p.getIdEstructura() : null);
        return getPdfBytes("jasper/repLibControlados.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteEmisionVales(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloEmisionVales = RESOURCES.getString("TITULO1_REP_EMIS_VAL");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloEmisionVales != null) ? tituloEmisionVales : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        
        return getPdfBytes("jasper/repEmisionVales.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteRecetas(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloEmisionRecetas = RESOURCES.getString("TITULO1_REP_EMIS_RECET");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloEmisionRecetas != null) ? tituloEmisionRecetas : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        if (p.getFolio() != null && p.getFolio().isEmpty()) {
            p.setFolio(null);
        }
        parametros.put("folio", p.getFolio());
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("tipoPrescripcion", p.getTipoPrescripcion());
        parametros.put("idTipoOrigen", p.getIdTipoOrigen());
        parametros.put("idEstatusPrescripcion", p.getIdEstatusPrescripcion());
        parametros.put("listPacientes", getListaPacientesAsString(p.getPacienteList(), "IDPACIENTE"));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));

        if (p.isActivaCamposRepEmisionRecetas()) {
            if (p.getTipoPrescripcion() == null){
                return getPdfBytes("jasper/repRecetasColCh.jasper", parametros);                
            }else{
                return getPdfBytes("jasper/repRecetasManCh.jasper", parametros);                
            }                            
        }
        return getPdfBytes("jasper/repRecetas.jasper", parametros);                
    }

    @Override
    public byte[] imprimeReporteCancelaciones(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloCancelaciones = RESOURCES.getString("TITULO1_REP_CANCELAC");
        String nombreEntidad = entidad != null ? entidad.getNombre() : "";
        String domicilio = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloCancelaciones != null) ? tituloCancelaciones : "");
        parametros.put("TITULO2", (nombreEntidad != null) ? nombreEntidad : "");
        parametros.put("DIRECCION1", (domicilio != null) ? domicilio : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idEstructura", p.getIdEstructura());
        if (p.getFolio() != null && p.getFolio().isEmpty()) {
            p.setFolio(null);
        }
        parametros.put("folio", p.getFolio());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        return getPdfBytes("jasper/repCancelaciones.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteAlmacenes(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloMovAlmacenes = RESOURCES.getString("TITULO1_REP_MOV_ALMAC");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloMovAlmacenes != null) ? tituloMovAlmacenes : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("idEstructuraDestino", p.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        return getPdfBytes("jasper/repMovAlmacenes.jasper", parametros);
    }

    @Override
    public boolean generaExcelAlmacen(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelAlmacen = RESOURCES.getString("TITULO1_REP_MOV_ALMAC");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelAlmacen != null) ? tituloExcelAlmacen : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idEstructuraDestino", p.getIdEstructura());
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        return getXlsxStream("RepAlmacen.xlsx", "jasper/repMovAlmacenes.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelGeneral(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelGeneral = RESOURCES.getString("TITULO1_REP_MOV_GEN");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelGeneral != null) ? tituloExcelGeneral : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idEstructuraDestino", p.getIdEstructura());
        parametros.put("listaTipoMov", getListaIdsAsString(p.getIdTipoMovimientos()));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        if (p.isPermiteAjusteInventarioGlobal())
            return getXlsxStream("repMovGeneral.xlsx", "jasper/repMovGeneralesCh.jasper", parametros);
        return getXlsxStream("repMovGeneral.xlsx", "jasper/repMovGenerales.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelGenerales(ParamBusquedaReporte p, EntidadHospitalaria entidad, boolean mostrarOrigenInsumos) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloMovsGrales = RESOURCES.getString("TITULO1_REP_MOV_GEN");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloMovsGrales != null) ? tituloMovsGrales : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("mostrarOrigenInsumos", mostrarOrigenInsumos);
        parametros.put("idEstructuraDestino", p.getIdEstructura());
        parametros.put("listaTipoMov", getListaIdsAsString(p.getIdTipoMovimientos()));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		parametros.put("nombreUsuarioGenera", p.getUsuarioGenera().getNombre() +" "+ p.getUsuarioGenera().getApellidoPaterno() +" "+ p.getUsuarioGenera().getApellidoMaterno());
        if (p.isActivaCamposReporteMovimientosGenerales())
            return getXlsxStream("repMovGeneral.xlsx", "jasper/repMovGeneralesCh.jasper", parametros);
        return getXlsxStream("repMovGeneral.xlsx", "jasper/repMovGenerales.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelVales(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelVales = RESOURCES.getString("TITULO1_REP_EMIS_VAL");
        String nombreEntidad = entidad != null ? entidad.getNombre() : "";
        String direccion = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelVales != null) ? tituloExcelVales : "");
        parametros.put("TITULO2", (nombreEntidad != null) ? nombreEntidad : "");
        parametros.put("DIRECCION1", (direccion != null) ? direccion : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("RepMovVales.xlsx", "jasper/repEmisionVales.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelRecetas(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcel1 = RESOURCES.getString("TITULO1_REP_EMIS_RECET");        
        String nombreEntidad = entidad != null ? entidad.getNombre() : "";
        String direccion = entidad != null ? entidad.getDomicilio() : "";
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcel1 != null) ? tituloExcel1 : "");
        parametros.put("TITULO2", (nombreEntidad != null) ? nombreEntidad : "");
        parametros.put("DIRECCION1", (direccion != null) ? direccion : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));

        if (p.getFolio() != null && p.getFolio().isEmpty()) {
            p.setFolio(null);
        }
        parametros.put("folio", p.getFolio());
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("listPacientes", getListaPacientesAsString(p.getPacienteList(), "IDPACIENTE"));
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);        
        if (p.isActivaCamposRepEmisionRecetas()) {
            if (p.getTipoPrescripcion() == null)
                return getXlsxStream("repMovRecetas.xlsx", "jasper/repRecetasColCh.jasper", parametros);
            return getXlsxStream("repMovRecetas.xlsx", "jasper/repRecetasManCh.jasper", parametros);
        }
        return getXlsxStream("repMovRecetas.xlsx", "jasper/repRecetas.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelCancelaciones(ParamBusquedaReporte p) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1_REP_CANCELAC");
        String titulo2 = RESOURCES.getString("TITULO2_REP_EXIST");
        String titulo3 = RESOURCES.getString("TITULO3_REP_EXIST");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));

        if (p.getFolio() != null && p.getFolio().isEmpty()) {
            p.setFolio(null);
        }
        parametros.put("folio", p.getFolio());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("repMovCancenlaciones.xlsx", "jasper/repCancelaciones.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelExistencias(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1_REP_EXIST");
        String entidadNombre = entidad != null ? entidad.getNombre() : "";
        String entidadDomicilio = entidad != null ? entidad.getDomicilio() : "";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (entidadNombre != null) ? entidadNombre : "");
        parametros.put("DIRECCION1", (entidadDomicilio != null) ? entidadDomicilio : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("listInsumos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("repMovExistencia.xlsx", "jasper/re_existencias.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelExistenciasMostarndoColumnas(ParamBusquedaReporte p, EntidadHospitalaria entidad, boolean mostrarOrigenInsumos,
            boolean mostrarClaveProveedor, boolean mostrarUnidosis, boolean mostrarCosteLote) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelExistencias = RESOURCES.getString("TITULO1_REP_EXIST");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelExistencias != null) ? tituloExcelExistencias : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("listInsumos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("mostrarOrigenInsumos", mostrarOrigenInsumos);
        parametros.put("mostrarClaveProveedor", mostrarClaveProveedor);
        parametros.put("mostrarUnidosis", mostrarUnidosis);
        parametros.put("mostrarCosteLote", mostrarCosteLote);
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        parametros.put("USER_NAME", p.getUsuarioGenera().getNombre() +" "+ p.getUsuarioGenera().getApellidoPaterno() +" "+ p.getUsuarioGenera().getApellidoMaterno());
        return getXlsxStream("repMovExistencia.xlsx", "jasper/re_existencias.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteAcumulados(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloAcumulados = RESOURCES.getString("TITULO1_REP_ACUMULADOS");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloAcumulados != null) ? tituloAcumulados : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaUsuarios", p.getListUsuarios());
        parametros.put(("idMedico"), (p.getIdMedico() != null) ? p.getIdMedico() : null);
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("tipoInsumo", p.getTipoInsumo() != 0 ? p.getTipoInsumo() : null);
        parametros.put("idTipoOrigen", p.getIdTipoOrigen() != null ? p.getIdTipoOrigen() : null);
        parametros.put("tipoReceta", p.getTipoReceta() != null ? p.getTipoReceta() : null);
        parametros.put("valTipoReceta", p.getValTipoReceta());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "CLAVEINSTITUCIONAL"));
        parametros.put("listaPacientes", getListaPacientesAsString(p.getPacienteList(), "IDPACIENTE"));
        if (p.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_CLAVE.getValue())
            return getPdfBytes("jasper/repAcumulados.jasper", parametros);
        else if (p.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_MEDICO.getValue())
                return getPdfBytes("jasper/repAcumuladosMedico.jasper", parametros);
        else if (p.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_PACIENTE.getValue())
                return getPdfBytes("jasper/repAcumuladosPaciente.jasper", parametros);
        return getPdfBytes("jasper/repAcumuladosColectivos.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteMinistracion(ParamBusquedaReporte p, EntidadHospitalaria entidad, List<String> estructuraList) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloMinistracion = RESOURCES.getString("TITULO1_REP_MINISTRACION");
        String nombreEntidad = entidad != null ? entidad.getNombre() : "";
        String domicilioEntidad = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloMinistracion != null) ? tituloMinistracion : "");
        parametros.put("TITULO2", (nombreEntidad != null) ? nombreEntidad : "");
        parametros.put("DIRECCION1", (domicilioEntidad != null) ? domicilioEntidad : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("folioPrescripcion", p.getFolio());
        parametros.put("tipoMinistrado", p.getTipoMinistrado());
        parametros.put("estructuraList", getListaEstructurasAsString(estructuraList));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaMedicos", getListaUsuariosAsString(p.getListMedicos()));
        parametros.put("listaPacientes", getListaPacientesAsString(p.getPacienteList(), "IDPACIENTE"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        return getPdfBytes("jasper/repMinistracion.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteMaterialesCuracion(ParamBusquedaReporte params, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloMatCur = RESOURCES.getString("TITULO1_REP_DISPENSACIONMC");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloMatCur != null) ? tituloMatCur : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(params.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(params.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("cama", params.getCama());
        parametros.put("idEstructura", params.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(params.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaPacientes", getListaPacientesAsString(params.getPacienteList(), "IDPACIENTE"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(params.getListUsuarios()));
        return getPdfBytes("jasper/reporteMaterialesCuracion.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelMaterialesCuracion(ParamBusquedaReporte params, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelMC = RESOURCES.getString("TITULO1_REP_DISPENSACIONMC");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelMC != null) ? tituloExcelMC : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("idEstructura", params.getIdEstructura());
        parametros.put("cama", params.getCama());
        parametros.put("fechaInicio", FechaUtil.formatoFecha(params.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(params.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(params.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaPacientes", getListaPacientesAsString(params.getPacienteList(), "IDPACIENTE"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(params.getListUsuarios()));
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("reporteMaterialesCuracion.xlsx", "jasper/reporteMaterialesCuracion.jasper", parametros);
    }

    @Override
    public boolean imprimeExcelMinistracion(ParamBusquedaReporte p, EntidadHospitalaria entidad, List<String> estructuraList) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelMinistra = RESOURCES.getString("TITULO1_REP_MINISTRACION");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelMinistra != null) ? tituloExcelMinistra : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("tipoMinistrado", p.getTipoMinistrado());
        parametros.put("folioPrescripcion", p.getFolio());
        parametros.put("estructuraList", getListaEstructurasAsString(estructuraList));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaMedicos", getListaUsuariosAsString(p.getListMedicos()));
        parametros.put("listaPacientes", getListaPacientesAsString(p.getPacienteList(), "IDPACIENTE"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("repMinistracion.xlsx", "jasper/repMinistracion.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteEstatusInsumos(ParamBusquedaReporte p, EntidadHospitalaria entidad, Estructura estructura) throws Exception {
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        //String tituloEstatusInsumos = RESOURCES.getString("IMSS");
        String claveDocumento = "";
        String subtitulo = "";
        String claveSubtitulo = "";
        if (p.getEstatusCantidadInsumo().equals("N")) {
            subtitulo = RESOURCES.getString("TITULO009_ESTATUS_AGOTADOS");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_AGOTADOS");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_AGOTADO");
        } else if (p.getEstatusCantidadInsumo().equals("D")) {
            subtitulo = RESOURCES.getString("TITULO010_BAJA_EXISTENCIA");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_EXISTENCIA");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_EXISTENCIA");
        } else if (p.getEstatusCantidadInsumo().equals("O")) {
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_SOBREEXISTENCIA");
            subtitulo = RESOURCES.getString("TITULO011_ESTATUS_SOBREEXISTENCIA");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_SOBREEXISTENCIA");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("CLAS_PRES", (estructura.getClavePresupuestal() != null) ? estructura.getClavePresupuestal() : "");
        parametros.put("SERVICIO", (estructura.getNombre() != null) ? estructura.getNombre() : "");
        parametros.put("ENT_HOSP", (entidad.getNombre() != null) ? entidad.getNombre() : "");
        //parametros.put("TITULO1", (tituloEstatusInsumos != null) ? tituloEstatusInsumos : "");
        parametros.put("SUBTITULO", (subtitulo != null) ? subtitulo : "");
        parametros.put("CLAVESUBTITULO", (claveSubtitulo != null) ? claveSubtitulo : "");
        parametros.put("TOTAL", (p.getTotal() != null) ? p.getTotal() : "");
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("estatus", p.getEstatusCantidadInsumo());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("idEstructura", p.getIdEstructura());
        return getPdfBytes("jasper/reporteEstatusInsumos.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteEstatusInsumosConce(ParamBusquedaReporte p, EntidadHospitalaria entidad,Estructura est) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String tituloIMSS = RESOURCES.getString("IMSS");
        String concentracionone = null;
        String concentraciontwo = null;
        String concentracionthree = null;
        String concentracionfour = null;
        String subtitulo = "";
        String claveSubtitulo = "";
        String claveDocumento = "";
        if (p.getEstatusCantidadInsumo().equals("DISPONIBLES")) {
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_DISPONIBLES");
            concentracionone = "true";
            claveDocumento = RESOURCES.getString("CLAVE_ARTICULOS_DISPONIBLES");
        } else if (p.getEstatusCantidadInsumo().equals("CADUCOS")) {
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_CADUCOS");
            concentraciontwo = "true";
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_EXISTENCIA");
        } else if (p.getEstatusCantidadInsumo().equals("DETERIORADOS")) {
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_DETERIORADOS");
            concentracionthree = "true";
            claveDocumento = RESOURCES.getString("CLAVE_ARTICULOS_DETERIORADOS");
        } else if (p.getEstatusCantidadInsumo().equals("SUSPENDIDOS")) {
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_SUSPENDIDOS");
            concentracionfour = "true";
            claveDocumento = "";
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("CLAS_PRES", (est.getClavePresupuestal() != null) ? est.getClavePresupuestal() : "");
        parametros.put("SERVICIO", (est.getNombre() != null) ? est.getNombre() : "");
        parametros.put("ENT_HOSP", (entidad.getNombre() != null) ? entidad.getNombre(): "");
        parametros.put("DOMICILIO", (entidad.getDomicilio() != null) ? entidad.getDomicilio() : "");
        parametros.put("TITULO1", (tituloIMSS != null) ? tituloIMSS : "");
        parametros.put("DISPONIBLES", concentracionone);
        parametros.put("CADUCOS", concentraciontwo);
        parametros.put("DETERIORADOS", concentracionthree);
        parametros.put("SUSPENDIDOS", concentracionfour);
        parametros.put("USER_NAME", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        parametros.put("SUBTITULO", (subtitulo != null) ? subtitulo : "");
        parametros.put("CLAVESUBTITULO", claveSubtitulo);
        parametros.put("TOTAL", (p.getTotal() != null) ? p.getTotal() : "");
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("OPCION", (p.getEstatusCantidadInsumo() != null) ? p.getEstatusCantidadInsumo() : "");
        parametros.put("HORA", FechaUtil.formatoFecha(p.getFechaFin(), "hh:mm:ss a"));
        parametros.put("FECHA", FechaUtil.formatoFecha(p.getFechaFin(), "dd-MM-yyyy"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        return getPdfBytes("jasper/reporteConcentracionArt.jasper", parametros);
    }

    @Override
    public byte[] imprimeSurtidosServicio(ParamBusquedaReporte p) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String tituloSurtidos = RESOURCES.getString("IMSS");
        String claveDocumento = RESOURCES.getString("CLAVE_SURTIDO_SERVICIO");
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("TITULO1", (tituloSurtidos != null) ? tituloSurtidos : "");
        parametros.put("USER_NAME", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("tipoInsumo", p.getTipoInsumo());
        parametros.put("HORA", FechaUtil.formatoFecha(p.getFechaFin(), "hh:mm:ss a"));
        parametros.put("FECHA", FechaUtil.formatoFecha(p.getFechaFin(), "dd-MM-yyyy"));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        return getPdfBytes("jasper/reporteSurtidoServicio.jasper", parametros);
    }

    @Override
    public boolean generaExcelSurtidosServicio(ParamBusquedaReporte p) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String tituloExcelSurtidos = RESOURCES.getString("IMSS");
        String claveDocumento = RESOURCES.getString("CLAVE_SURTIDO_SERVICIO");
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("TITULO1", (tituloExcelSurtidos != null) ? tituloExcelSurtidos : "");
        parametros.put("USER_NAME", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("tipoInsumo", p.getTipoInsumo());
        parametros.put("HORA", FechaUtil.formatoFecha(p.getFechaFin(), "hh:mm:ss a"));
        parametros.put("FECHA", FechaUtil.formatoFecha(p.getFechaFin(), "dd-MM-yyyy"));
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("reporteSurtidoServicio.xlsx", "jasper/reporteSurtidoServicio.jasper", parametros);
    }
    
    public boolean generaExcelBajaArticulos(ParamBusquedaReporte p, EntidadHospitalaria entidad, Estructura estructura) throws Exception {
         this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";
        String subtitulo = "";
        String claveSubtitulo = "";
        String claveDocumento = "";
        if (p.getEstatusCantidadInsumo().equals("N")) {
            subtitulo = RESOURCES.getString("TITULO012_BAJA_CADUCADO");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_CADUCADO");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_CADUCADO");
        } else if (p.getEstatusCantidadInsumo().equals("D")) {
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_DETERIORADO");
            subtitulo = RESOURCES.getString("TITULO013_BAJA_DETERIORADO");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_DETERIORADO");
        } else if (p.getEstatusCantidadInsumo().equals("O")) {
            subtitulo = RESOURCES.getString("TITULO014_BAJA_SUSPENDIDO");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_SUSPENDIDO");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_SUSPENDIDO");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("USER_NAME", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("CLAS_PRES", (estructura.getClavePresupuestal() != null) ? estructura.getClavePresupuestal() : "");
        parametros.put("SERVICIO", (estructura.getNombre() != null) ? estructura.getNombre() : "");
        parametros.put("ENT_HOSP", (entidad.getNombre() != null) ? entidad.getNombre() : "");
        parametros.put("TITULO1", (subtitulo != null) ? subtitulo : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("CLAVESUBTITULO", (claveSubtitulo != null) ? claveSubtitulo : "");
        parametros.put("TOTAL", (p.getTotal() != null) ? p.getTotal() : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("estatus", p.getEstatusCantidadInsumo());
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        return getXlsxStream("reporteBajasInsumos.xlsx", "jasper/reporteBajasInsumos.jasper", parametros);
    }

    @Override
    public boolean generaExcelEstatusInsumos(ParamBusquedaReporte p, EntidadHospitalaria entidad, Estructura estructura) throws Exception {
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String tituloExcelEstatusInsumos = RESOURCES.getString("IMSS");
        String subtitulo = "";
        String claveSubtitulo = "";
        String claveDocumento = "";
        if (p.getEstatusCantidadInsumo().equals("N")) {
            subtitulo = RESOURCES.getString("TITULO009_ESTATUS_AGOTADOS");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_AGOTADOS");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_AGOTADO");
        } else if (p.getEstatusCantidadInsumo().equals("D")) {
            subtitulo = RESOURCES.getString("TITULO010_BAJA_EXISTENCIA");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_EXISTENCIA");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_EXISTENCIA");
        } else if (p.getEstatusCantidadInsumo().equals("O")) {
            subtitulo = RESOURCES.getString("TITULO011_ESTATUS_SOBREEXISTENCIA");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_SOBREEXISTENCIA");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_SOBREEXISTENCIA");
        }
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("SERVICIO", (estructura.getNombre() != null) ? estructura.getNombre() : "");
        parametros.put("CLAS_PRES", (estructura.getClavePresupuestal() != null) ? estructura.getClavePresupuestal() : "");
        parametros.put("ENT_HOSP", (entidad.getNombre() != null) ? entidad.getNombre() : "");
        parametros.put("TITULO1", (tituloExcelEstatusInsumos != null) ? tituloExcelEstatusInsumos : "");
        parametros.put("SUBTITULO", (subtitulo != null) ? subtitulo : "");
        parametros.put("CLAVESUBTITULO", (claveSubtitulo != null) ? claveSubtitulo : "");
        parametros.put("TOTAL", (p.getTotal() != null) ? p.getTotal() : "");
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("estatus", p.getEstatusCantidadInsumo());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("repEstatusInsumos.xlsx", "jasper/reporteEstatusInsumos.jasper", parametros);
    }

    @Override
    public boolean generaExcelEstatusInsumosConce(ParamBusquedaReporte p, EntidadHospitalaria entidad,Estructura est) throws Exception {
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String tituloEstatusInsumosConce = RESOURCES.getString("IMSS");
        String subtitulo = "";
        String claveSubtitulo = "";
        String claveDocumento = "";
        String concentracionone = null;
        String concentraciontwo = null;
        String concentracionthree = null;
        String concentracionfour = null;
        if (p.getEstatusCantidadInsumo().equals("DISPONIBLES")) {
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_DISPONIBLES");
            concentracionone = "true";
            claveDocumento = RESOURCES.getString("CLAVE_ARTICULOS_DISPONIBLES");
        } else if (p.getEstatusCantidadInsumo().equals("CADUCOS")) {
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_CADUCOS");
            concentraciontwo = "true";
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_EXISTENCIA");
        } else if (p.getEstatusCantidadInsumo().equals("DETERIORADOS")) {
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_DETERIORADOS");
            concentracionthree = "true";
            claveDocumento = RESOURCES.getString("CLAVE_ARTICULOS_DETERIORADOS");
        } else if (p.getEstatusCantidadInsumo().equals("SUSPENDIDOS")) {
            claveDocumento = "";
            subtitulo = RESOURCES.getString("TITULO_REP_CONCEN_SUSPENDIDOS");
            concentracionfour = "true";
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("CLAS_PRES", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("SERVICIO", (est.getNombre() != null) ? est.getNombre() : "");
        parametros.put("ENT_HOSP", (entidad.getNombre() != null) ? entidad.getNombre() : "");
        parametros.put("DOMICILIO", (entidad.getDomicilio() != null) ? entidad.getDomicilio() : "");
        parametros.put("TITULO1", (tituloEstatusInsumosConce != null) ? tituloEstatusInsumosConce : "");
        parametros.put("DISPONIBLES", concentracionone);
        parametros.put("CADUCOS", concentraciontwo);
        parametros.put("DETERIORADOS", concentracionthree);
        parametros.put("SUSPENDIDOS", concentracionfour);
        parametros.put("CLAVESUBTITULO", claveSubtitulo);
        parametros.put("SUBTITULO", (subtitulo != null) ? subtitulo : "");
        parametros.put("TOTAL", (p.getTotal() != null) ? p.getTotal() : "");
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("OPCION", (p.getEstatusCantidadInsumo() != null) ? p.getEstatusCantidadInsumo() : "");
        parametros.put("HORA", FechaUtil.formatoFecha(p.getFechaFin(), "hh:mm:ss a"));
        parametros.put("FECHA", FechaUtil.formatoFecha(p.getFechaFin(), "dd-MM-yyyy"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("reporteConcentracionArt.xlsx", "jasper/reporteConcentracionArt.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteTerapeutico(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloTerapeutico = RESOURCES.getString("TITULO1_REP_TERAPEUTICO");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloTerapeutico != null) ? tituloTerapeutico : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd"));
        parametros.put("APELLIDO_MATERNO", getListaPacientesAsString(p.getPacienteList(), "APELLIDO_MATERNO"));
        parametros.put("APELLIDO_PATERNO", getListaPacientesAsString(p.getPacienteList(), "APELLIDO_PATERNO"));
        parametros.put("NOMBRE_PACIENTE", getListaPacientesAsString(p.getPacienteList(), "NOMBRE_PACIENTE"));
        parametros.put("listaPacientes", getListaPacientesAsString(p.getPacienteList(), "IDPACIENTE"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        return getPdfBytes("jasper/repHistorialTerapeutico.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteControlCaducidad(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo4 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO1_REP_CTRL_CADUCIDAD");
        String titulo4 = RESOURCES.getString("CLAVE_ARTICULOS_AGOTADOS");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO4", (titulo4 != null) ? titulo4 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO4", (rutaLogo4 != null) ? rutaLogo4 : null);
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd"));
        parametros.put("idEstructuraDestino", p.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        
        return getPdfBytes("jasper/ControlCaducidades2.jasper", parametros);
//        return getPdfBytes("jasper/repControlCaducidad.jasper", parametros);
    }

    @Override
    public boolean generaExcelControlCaducidad(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelCaducidad = RESOURCES.getString("TITULO1_REP_CTRL_CADUCIDAD");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelCaducidad != null) ? tituloExcelCaducidad : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idEstructuraDestino", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        parametros.put("listaUsuarios", getListaUsuariosAsString(p.getListUsuarios()));
        parametros.put("idEstructura", p.getIdEstructura());
        return getXlsxStream("RepControlCaducidad.xlsx", "jasper/ControlCaducidades2.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteEnvioNeumatico(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloEnvioNeumatico = RESOURCES.getString("TITULO1_REP_ENVIONEUMATICO");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloEnvioNeumatico != null) ? tituloEnvioNeumatico : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaUsuarios", p.getListUsuarios());
        parametros.put("listaCapsulas", getListaCapsulasAsString(p.getListaCapsulas()));
        parametros.put("idEstructura", p.getIdEstructura());
        return getPdfBytes("jasper/repEnvioNeumatico.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteRefri5000(ParamLibMedControlados p) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1;
        String claveArticulo;
        String claveDocumento;
        if (p.getValorRefri5000().equals("R")) {
            titulo1 = RESOURCES.getString("TITULO_REFRIGERACION");
            claveArticulo = RESOURCES.getString("CLAVE_ARTICULO_REFRIGERACION");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_REFRIGERACION");
        } else {
            titulo1 = RESOURCES.getString("TITULO_CLAVES_5000");
            claveArticulo = RESOURCES.getString("CLAVE_ARTICULO_5000");
            claveDocumento = RESOURCES.getString("CLAVE-DOCUMENTO_5000");
        }
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("LOGO2", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("CLAVE_ARTICULO", (claveArticulo != null) ? claveArticulo : "");
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("idMedicamento", (p.getIdMedicamento() != null) ? p.getIdMedicamento() : null);
        parametros.put("idEstructura", (p.getIdEstructura() != null) ? p.getIdEstructura() : null);
        if (p.getValorRefri5000().equals("R"))
            return getPdfBytes("jasper/reporteMedRefrigeracion.jasper", parametros);
        return getPdfBytes("jasper/reporteClaves_5000.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteBajasInsumos(ParamBusquedaReporte p, EntidadHospitalaria entidad, Estructura estructura) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";
        String subtitulo = "";
        String claveSubtitulo = "";
        String claveDocumento = "";
        if (p.getEstatusCantidadInsumo().equals("N")) {
            subtitulo = RESOURCES.getString("TITULO012_BAJA_CADUCADO");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_CADUCADO");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_CADUCADO");
        } else if (p.getEstatusCantidadInsumo().equals("D")) {
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_DETERIORADO");
            subtitulo = RESOURCES.getString("TITULO013_BAJA_DETERIORADO");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_DETERIORADO");
        } else if (p.getEstatusCantidadInsumo().equals("O")) {
            subtitulo = RESOURCES.getString("TITULO014_BAJA_SUSPENDIDO");
            claveSubtitulo = RESOURCES.getString("CLAVE_ARTICULOS_BAJA_SUSPENDIDO");
            claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_BAJA_SUSPENDIDO");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("USER_NAME", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("CLAS_PRES", (estructura.getClavePresupuestal() != null) ? estructura.getClavePresupuestal() : "");
        parametros.put("SERVICIO", (estructura.getNombre() != null) ? estructura.getNombre() : "");
        parametros.put("ENT_HOSP", (entidad.getNombre() != null) ? entidad.getNombre() : "");
        parametros.put("TITULO1", (subtitulo != null) ? subtitulo : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("CLAVESUBTITULO", (claveSubtitulo != null) ? claveSubtitulo : "");
        parametros.put("TOTAL", (p.getTotal() != null) ? p.getTotal() : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("estatus", p.getEstatusCantidadInsumo());
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put("listaMedicamentos", getListaMedicamentosAsString(p.getListInsumos(), "IDMEDICAMENTO"));
        return getPdfBytes("jasper/reporteBajasInsumos.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteCamas(ParamBusquedaReporte p, List<String> listaEstructuras, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloCamas = RESOURCES.getString("TITULO1_REP_CAMAS");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloCamas != null) ? tituloCamas : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("filtro", p.getCadenaBusqueda());
        parametros.put("listaEstructuras", getListaEstructurasAsString(listaEstructuras));
        parametros.put("listaTipoMov", getListaIdsAsString(p.getIdTipoMovimientos()));
        return getPdfBytes("jasper/reporteCamas.jasper", parametros);
    }

    @Override
    public byte[] imprimirOrdenSurtirProveedorFarmacia(ReabastoExtended p) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloSurtProvFarmacia = RESOURCES.getString("SURTIMIENTOPROVEFARMA");
        String titulo2 = p.getNombreEntidad();
        String titulo3 = p.getDomicilio();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FOLIO", p.getFolio());
        parametros.put("FECHA", p.getFechaSurtida());
        parametros.put("ESTATUS", p.getEstatus());
        parametros.put("TITULO1", (tituloSurtProvFarmacia != null) ? tituloSurtProvFarmacia : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("IDREABASTO", p.getIdReabasto());
        parametros.put("REQUERIMIENTO_FECHA", p.getFechaSolicitud());
        parametros.put("REQUERIMIENTO_FOLIO", p.getFolio());
        parametros.put("Almacen", p.getNombreEstructura());
        parametros.put("Proveedor", p.getNombreProveedor());
        
        return getPdfBytes("jasper/surtimiento.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteInsumoNoMiistrado(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloNoMinistrado = RESOURCES.getString("TITULO1_REP_INSUMONOMINISTRADO");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloNoMinistrado != null) ? tituloNoMinistrado : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd"));
        parametros.put("listaInsumo", getListaNoMinistradosAsString(p.getListaInsumo()));
        parametros.put("idEstructura", p.getIdEstructura());
        
        return getPdfBytes("jasper/repInsumoNoMinistrado.jasper", parametros);
    }

    @Override
    public boolean generaExcelInsumoNoMinistrado(ParamBusquedaReporte p, String pathTmp, String url, EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelNoMin = RESOURCES.getString("TITULO1_REP_INSUMONOMINISTRADO");
        String titulo2 = entidad != null ? entidad.getNombre() : "";
        String titulo3 = entidad != null ? entidad.getDomicilio() : "";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelNoMin != null) ? tituloExcelNoMin : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd"));
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        parametros.put("listaInsumo", getListaNoMinistradosAsString(p.getListaInsumo()));
        parametros.put("idEstructura", p.getIdEstructura());
        return getXlsxStream("repInsumoNoMinistrado.xlsx", "jasper/repInsumoNoMinistrado.jasper", parametros);
    }
    
    @Override
    public byte[] imprimeReporteControlDiario(ParamLibMedControlados p) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 =RESOURCES.getString("TITULO_CLAVES_CONTROL_DIARIO");
        String titulo2 = p.getNombreEntidad();
        String titulo3 = p.getDomicilio();
        String claveArticulo = null;
        String claveDocumento = null;
        if(p.getValueControlDiario() != null) {
            switch(p.getValueControlDiario()) {
                case "1":
                    titulo1 = RESOURCES.getString("TITULO_REFRIGERACION");
                    claveArticulo = RESOURCES.getString("CLAVE_ARTICULO_REFRIGERACION");
                    claveDocumento = RESOURCES.getString("CLAVE_DOCUMENTO_REFRIGERACION");
                    break;
                case "2":
                    titulo1 = RESOURCES.getString("TITULO_CLAVES_5000");
                    break;
                case "3":
                    titulo1 = RESOURCES.getString("TITULO_CLAVES_4000");
                    break;
                case "4":
                    titulo1 = RESOURCES.getString("TITULO_CLAVES_CONTROLADO");
                    break;    
                default:    
            }
        }
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        
        parametros.put("LOGO2", (rutaLogo1 != null) ? rutaLogo1 : null);

        parametros.put("CLAVE_DOCUMENTO", (claveDocumento != null) ? claveDocumento : "");
        parametros.put("CLAVE_ARTICULO", (claveArticulo != null) ? claveArticulo : "");
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listInsumo", (p.getListInsumos() != null) ? p.getListInsumos() : null);
        parametros.put("idEstructura", (p.getIdEstructura() != null) ? p.getIdEstructura() : null);
        parametros.put("valueControlDiario", (p.getValueControlDiario() != null) ? p.getValueControlDiario() : null);
        parametros.put("NOMBRE_USUARIO", (p.getUsuarioGeneraReporte() != null) ? p.getUsuarioGeneraReporte() : null);
        parametros.put("CLAVE_USUARIO", (p.getClaveUsuarioGenReporte() != null) ? p.getClaveUsuarioGenReporte() : null);
        parametros.put("folioConfirmacion", (p.getFolio() != null) ? p.getFolio() : null);
        
        return getPdfBytes("jasper/reporteControlDiario.jasper", parametros);
    }

    @Override
    public byte[] SurtPrescripcionConsolidado(ParamBusquedaReporte p, EntidadHospitalaria entidad, String datosUsuario,String matrPers) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloRepSurtPrescConsolidado = RESOURCES.getString("TITULO_SURT_PRESCR_CONSOLIDADO");        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("datosUsuario", (datosUsuario != null) ? datosUsuario : "");
        parametros.put("matrPers", (matrPers != null) ? matrPers : "");        
        parametros.put("TITULO1", (tituloRepSurtPrescConsolidado != null) ? tituloRepSurtPrescConsolidado : "");
        parametros.put("TITULO2", (entidad.getNombre() != null) ? entidad.getNombre() : "");
        parametros.put("DIRECCION1", (entidad.getDomicilio() != null) ? entidad.getDomicilio() : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);        
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));        
        parametros.put("idEstructura", (p.getIdEstructura() != null) ? p.getIdEstructura() : null);  
        
        return getPdfBytes("jasper/RepSurtPrescripcionConsolidado.jasper", parametros);
    }

    @Override
    public boolean generaExcelPrescConsolidado(ParamBusquedaReporte p, EntidadHospitalaria entidad,String datosUsuario,String matrPers) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloRepSurtPrescConsolidado = RESOURCES.getString("TITULO_SURT_PRESCR_CONSOLIDADO");                
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("datosUsuario", (datosUsuario != null) ? datosUsuario : "");
        parametros.put("matrPers", (matrPers != null) ? matrPers : "");        
        parametros.put("TITULO1", (tituloRepSurtPrescConsolidado != null) ? tituloRepSurtPrescConsolidado : "");
        parametros.put("TITULO2", (entidad.getNombre() != null) ? entidad.getNombre() : "");
        parametros.put("DIRECCION1", (entidad.getDomicilio() != null) ? entidad.getDomicilio() : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));        
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);        
        return getXlsxStream("repSurtPrescrpConsolidado.xlsx", "jasper/RepSurtPrescripcionConsolidado.jasper", parametros);
    }
    
   @Override
    public byte[] imprimeReporteExist_Consolidadas(Medicamento_Extended p, EntidadHospitalaria entidad,String nombreUsuario) throws Exception {        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO_EXISTENCIAS_CONSOLIDADAS");
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("NOMBRE_USUARIO", (nombreUsuario != null) ? nombreUsuario : "");
        parametros.put("listInsumo", getListaMedicamentosAsString(p.getListInsumos(), "CLAVEINSTITUCIONAL"));
        parametros.put("idEstructura", (p.getIdEstructura() != null) ? p.getIdEstructura() : null);
        
        return getPdfBytes("jasper/repExistConsolidadas.jasper", parametros);
    }
    
       @Override
    public boolean imprimeExcel_ReporteExist_Consolidadas(Medicamento_Extended p, String pathTmp, String url , EntidadHospitalaria entidad) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelExist_Consolidadas = RESOURCES.getString("TITULO_REP_EXISTENCIAS_CONSOLIDADAS");
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelExist_Consolidadas != null) ? tituloExcelExist_Consolidadas : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("listInsumo", getListaMedicamentosAsString(p.getListInsumos(), "CLAVEINSTITUCIONAL"));
        parametros.put("idEstructura", p.getIdEstructura());
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("reporteExistenciasConsolidadas.xlsx", "jasper/repExistConsolidadas.jasper", parametros);
        }

    @Override
    public byte[] reporteTranferencia(Reabasto reabasto, EntidadHospitalaria entidad , String nombreUsuario) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloTransferencias = RESOURCES.getString("TITULO_REP_TRANFERENCIA");
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IDREABASTO", reabasto.getIdReabasto());
        parametros.put("Usuario",(nombreUsuario != null) ? nombreUsuario : "");
        parametros.put("TITULO1", (tituloTransferencias != null) ? tituloTransferencias : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "");
        parametros.put("Almacen", reabasto.getAlmacen());
        parametros.put("Proveedor", reabasto.getProveedor());
        parametros.put("REQUERIMIENTO_FOLIO", reabasto.getFolio());
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        return getPdfBytes("jasper/repTransferenciaInsumos.jasper", parametros);        
    }
    
    @Override
    public boolean generaReporteReaccion(Reaccion reaccion,Integer opc, String pathTmp, String url) throws Exception{        
        String jasper="";
        Map<String, Object> parametros = new HashMap<>();
        switch(opc){
            case 1:         
                parametros.put("folio", reaccion.getFolio());
                parametros.put("numeroNotificacion", reaccion.getNumeroNotificacion());
                String nameComp = String.format(reaccion.getNombrePaciente()," ",reaccion.getApellidoPat()," ",reaccion.getApellidoMat());
                String[] name = nameComp.split(" ");
                String initials="";
                for(int i=0; i < name.length ;i++){
                    initials += name[i].substring(0,1);
                }
                parametros.put("iniciales", initials);
                parametros.put("fechaNacimiento", reaccion.getFechaNacimiento());
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fechaNac = LocalDate.parse(dateFormat.format(reaccion.getFechaNacimiento()), fmt);
                LocalDate ahora = LocalDate.now();
                Period periodo = Period.between(fechaNac, ahora);
                parametros.put("edadAPac", periodo.getYears());
                parametros.put("edadMPac", periodo.getMonths());
                parametros.put("sexoPac", reaccion.getSexo());
                parametros.put("estaturaPac", reaccion.getEstatura());
                parametros.put("pesoPac", reaccion.getPeso());
                parametros.put("fechaInicioSospecha", reaccion.getFechaInicioSospecha());
                parametros.put("descripcion", reaccion.getDescripcion());
                parametros.put("idConsecuencia", reaccion.getIdConsecuencia()); 
                
                jasper ="jasper/repReaccionesAdversas1.jasper";
                break;
            case 2: 
                parametros.put("medicamento", reaccion.getMedicamento());
                parametros.put("lote", reaccion.getLote());
                parametros.put("viaAdministracion", reaccion.getViaAdministracion());
                parametros.put("denomincacionDistintiva", reaccion.getDenomincacionDistintiva());
                parametros.put("laboratorio", reaccion.getLaboratorio());
                parametros.put("dosis", reaccion.getDosis());
                parametros.put("motivoPrescripcion", reaccion.getMotivoPrescripcion());
                parametros.put("caducidad", reaccion.getCaducidad());
                parametros.put("inicioAdministracion", reaccion.getInicioAdministracion());
                parametros.put("finAdministracion", reaccion.getFinAdministracion());
                parametros.put("medicamentoSuspendido", reaccion.getMedicamentoSuspendido());
                parametros.put("desaparecioReaccion", reaccion.getDesaparecioReaccion());
                parametros.put("disminuyoDosis", reaccion.getDisminuyoDosis());
                parametros.put("cuanto", reaccion.getCuanto());
                parametros.put("cambioFarmacoTerapia", reaccion.getCambioFarmacoTerapia());
                parametros.put("cual", reaccion.getCual());
                parametros.put("reaparecioReaccionReadministrar", reaccion.getReaparecioReaccionReadministrar());
                parametros.put("persistioReaccion", reaccion.getPersistioReaccion());
                parametros.put("idReaccion", reaccion.getIdReaccion());
                
                jasper ="jasper/repReaccionesAdversas2.jasper";
                break;
            case 3: 
                parametros.put("historiaClinica", reaccion.getHistoriaClinica());
                parametros.put("idTipoInformeLab", reaccion.getIdTipoInformeLab());
                parametros.put("idOrigenLab", reaccion.getIdOrigenLab());
                parametros.put("idTipoInformeProf", reaccion.getIdTipoInformeProf());
                parametros.put("idOrigenProf", reaccion.getIdOrigenProf());
                
                jasper ="jasper/repReaccionesAdversas3.jasper";
                break;
            case 4: 
                parametros.put("rfcInformante", reaccion.getRfcInformante());
                parametros.put("curpInformante", reaccion.getCurpInformante());
                parametros.put("nombre", reaccion.getNombre());
                parametros.put("apellidoPat", reaccion.getApellidoPat());
                parametros.put("apellidoMat", reaccion.getApellidoMat());
                parametros.put("telefono", reaccion.getTelefono());
                parametros.put("correoElectronico", reaccion.getCorreoElectronico());
                parametros.put("cp", reaccion.getCp());
                parametros.put("calle", reaccion.getCalle());
                parametros.put("numeroExt", reaccion.getNumeroExt());
                parametros.put("numeroInt", reaccion.getNumeroInt());
                parametros.put("colonia", reaccion.getColonia());
                parametros.put("localidad", reaccion.getLocalidad());
                parametros.put("municipio", reaccion.getMunicipio());
                parametros.put("estado", reaccion.getEstado());
                parametros.put("publicarInformacion", reaccion.getPublicarInformacion());
                
                jasper ="jasper/repReaccionesAdversas4.jasper";
                break;
        }
        return exportToPdfFile(pathTmp, jasper, parametros);  
    }
    
    @Override
    public byte[] reporteValidacionSoluciones(
            Surtimiento_Extend surtimiento,Solucion solucion, EntidadHospitalaria entidad, String nombreUsuario
            , String alergias, String diagnosticos, String envase, Prescripcion p) throws Exception{  
        
        Map<String, Object> parametros = new HashMap<>();        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloOrdenPreparacion = RESOURCES.getString("TITULO_REP_VALIDACION_SOLUCION");        
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        parametros.put("IDSURTIMIENTO", surtimiento.getIdSurtimiento());
        parametros.put("Usuario",(nombreUsuario != null) ? nombreUsuario : "");
        parametros.put("TITULO1", (tituloOrdenPreparacion != null) ? tituloOrdenPreparacion : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("FOLIO_MEZCLA", surtimiento.getFolio());
        parametros.put("PACIENTE", surtimiento.getNombrePaciente());
        parametros.put("NO_PACIENTE", surtimiento.getPacienteNumero());
        parametros.put("SERVICIO", surtimiento.getNombreEstructura());
        parametros.put("SEXO", surtimiento.getPacienteSexo());
        parametros.put("CAMA", surtimiento.getCama());
        parametros.put("DIAGNOSTICOS", diagnosticos);
        parametros.put("ALERGIAS", alergias);
        parametros.put("ENVASE", envase);
        parametros.put("IDESTRUCTURA",surtimiento.getIdEstructura());
        parametros.put("REQUERIMIENTO_FECHA",new Date());
        
        if (solucion != null) {
//            parametros.put("EDAD", String.valueOf(solucion.getEdadPaciente()));
            String fechaNacimiento = (surtimiento.getFechaNacimiento() != null) ? FechaUtil.formatoFecha(surtimiento.getFechaNacimiento(), "dd/MM/yyyy" ) : "";
            parametros.put("EDAD", fechaNacimiento);
            parametros.put("PESO", String.valueOf(solucion.getPesoPaciente()));
            parametros.put("PISO", solucion.getPiso());
            parametros.put("MUESTREO", (solucion.getMuestreo()!=null) ? (solucion.getMuestreo()==1) ? "Si" : "" : "");
            parametros.put("CONSERVACION", solucion.getObservaciones());
            parametros.put("VOLUMEN", (solucion.getVolumen()!=null) ? solucion.getVolumen().toString() : "");
            parametros.put("INSTRPREPARACION", solucion.getInstruccionesPreparacion());
            parametros.put("OBSERVACIONES", solucion.getObservaciones());
            if (solucion.getIdEstatusSolucion() != null){
                parametros.put("ESTATUSMEZ", EstatusSolucion_Enum.getStatusFromId(solucion.getIdEstatusSolucion()).name());
            }
            parametros.put("PROTLUZ", (surtimiento.isProteccionLuz()) ? "S" : "");
            parametros.put("NOAGITAR", (surtimiento.isNoAgitar()) ? "S" : "");
            parametros.put("TEMREFRI", (surtimiento.isTempRefrigeracion()) ? "S" : "");
            parametros.put("TEMAMB", (surtimiento.isTempAmbiente()) ? "S" : "");
            
            if (solucion.getCaducidadMezcla()!=null){
                String codigoQr = solucion.getClaveMezcla()
                        + "," + solucion.getLoteMezcla()
                        + "," + FechaUtil.formatoCadena(solucion.getCaducidadMezcla(), "ddMMyyyy");
                parametros.put("CADUCIDADMEZ", FechaUtil.formatoCadena(solucion.getCaducidadMezcla(), "dd/MM/yyyy HH:mm"));
                parametros.put("CODIGOQR", codigoQr);
            }
          
            parametros.put("CLAVMEZ", solucion.getClaveMezcla());
            parametros.put("LOTEMEZ", solucion.getLoteMezcla());
            
            
            Usuario u;
            if (p.getIdMedico()!= null) {
                u = obtenerUsuario(p.getIdMedico());
                String usuario = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno();
                parametros.put("USUARIO_MEDICO", usuario.trim());
                parametros.put("MEDICO", usuario);
                parametros.put("CEDULA", surtimiento.getCedProfesional());
//                parametros.put("USUARIO_MEDICO", surtimiento.getNombreMedico());
                if (p.getFechaPrescripcion() != null) {
                    parametros.put("FECHAPRESCRIBE", FechaUtil.formatoCadena(p.getFechaPrescripcion(), "dd/MM/yyyy HH:mm"));
                }
            }
            
            if (solucion.getIdUsuarioValida() != null) {
                u = obtenerUsuario(solucion.getIdUsuarioValida());
                String usuario = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno();
                parametros.put("USUARIO_VALIDA", usuario.trim());
                if (solucion.getFechaValida() != null) {
                    parametros.put("FECHAVALIDA", FechaUtil.formatoCadena(solucion.getFechaValida(), "dd/MM/yyyy HH:mm"));
                }
            }
            
            if (solucion.getIdUsuarioPrepara()!= null) {
                u = obtenerUsuario(solucion.getIdUsuarioPrepara());
                String usuario = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoPaterno();
                parametros.put("USUARIO_PREPARA", usuario.trim());
                if (solucion.getFechaPrepara() != null) {
                    parametros.put("FECHAPREPARA", FechaUtil.formatoCadena(solucion.getFechaPrepara(), "dd/MM/yyyy HH:mm"));
                }
            }
            
            if (solucion.getIdUsuarioInspeccion() != null) {
                u = obtenerUsuario(solucion.getIdUsuarioInspeccion());
                String usuario = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoPaterno();
                parametros.put("USUARIO_INSPECCIONA", usuario.trim());
                if (solucion.getFechaInspeccion() != null) {
                    parametros.put("FECHAINSPECCIONA", FechaUtil.formatoCadena(solucion.getFechaInspeccion(), "dd/MM/yyyy HH:mm"));
                }
            }
            
            parametros.put("SUP_CORP", String.valueOf(solucion.getAreaCorporal()));
            parametros.put("TALLA", String.valueOf(solucion.getTallaPaciente()));
            parametros.put("PDIABETES", (surtimiento.isDiabetes()) ? "S" : "");
            parametros.put("PRENALES", (surtimiento.isProblemasRenales()) ? "S" : "");
            parametros.put("PHIPERTENSION", (surtimiento.isHipertension()) ? "S" : "");
            parametros.put("TIPMEZCLA", surtimiento.getTipoSolucion());
            
            if (solucion.getIdViaAdministracion() != 0){
                ViaAdministracion viaAdmon = obtenerViaAdministracion(solucion.getIdViaAdministracion());
                if (viaAdmon != null) {
                    parametros.put("VIAADMON", viaAdmon.getNombreViaAdministracion());
                }
            }
            
            parametros.put("PERFUSION", (surtimiento.isPerfusionContinua()) ? "S" : "");
            parametros.put("TIEMPO_INFUS", String.valueOf(solucion.getMinutosInfusion()));
            parametros.put("VELOCIDAD", String.valueOf(solucion.getVelocidad()));
            parametros.put("RITMO", String.valueOf(solucion.getRitmo()));
            parametros.put("CAL_TOT", (solucion.getCaloriasTotales() != null) ? solucion.getCaloriasTotales().toString() : "0");
            parametros.put("CAL_PROT", (solucion.getKcalProteicas() != null) ?  solucion.getKcalProteicas().toString() : "0");
            parametros.put("CAL_NOPROT", (solucion.getKcalNoProteicas() != null) ?  solucion.getKcalNoProteicas().toString() : "0");
            parametros.put("OSMOLARIDAD", (solucion.getCaloriasTotales() != null) ?  solucion.getOmolairdadTotal().toString() : "0");
            
            if (solucion.getIdProtocolo()>0){
                Protocolo protocolo = obtenerProtocolo(solucion.getIdProtocolo());
                if (protocolo!= null){
                    parametros.put("PROTOCOLO", protocolo.getClaveProtocolo() + " - " + protocolo.getDescripcionProtocolo());
                }
            }
            
            if (solucion.getIdDiagnostico() != null && !solucion.getIdDiagnostico().trim().isEmpty()){
                Diagnostico diagnostico = obtenerDiagnostico(solucion.getIdDiagnostico());
                if (diagnostico != null){
                    parametros.put("DIAGNOSTICO", diagnostico.getClave() + " + " + diagnostico.getDescripcion());
                }
            }
        }
        if ( surtimiento.getTipoSolucion().equals("Nutricional Parenteral") ){
            return getPdfBytes("jasper/repValidacionNPTCM.jasper", parametros);
        }
        else {
            return getPdfBytes("jasper/repValidacionCM.jasper", parametros);
        }
    }
    
    private ViaAdministracion obtenerViaAdministracion(Integer idViaAdministracion){
        ViaAdministracion o = null;
        try{
            o = new ViaAdministracion();
            o.setIdViaAdministracion(idViaAdministracion);
            o.setActiva(Constantes.ACTIVO);
            o = viaAdministracionMapper.obtener(o);
        } catch (Exception e){
            LOGGER.error("Error al obtener la via de administracion {} ", e.getMessage());
        }
        return o;
    }
    
    private Diagnostico obtenerDiagnostico(String idDiagnostico){
        Diagnostico o = null;
        try{
            o = new Diagnostico();
            o.setIdDiagnostico(idDiagnostico);
            o.setActivo(true);
            o = diagnosticoMapper.obtener(o);
        } catch (Exception e){
            LOGGER.error("Error al obtener el Diagnóstico {} ", e.getMessage());
        }
        return o;
    }
    
    private Protocolo obtenerProtocolo(Integer idProtocolo){
        Protocolo o = null;
        try{
            o = new Protocolo();
            o.setIdProtocolo(idProtocolo);
            o.setIdEstatus(1);
        } catch (Exception e){
            LOGGER.error("Error al obtener el protocolo {} ", e.getMessage());
        }
        return o;
    }
    
    private Usuario obtenerUsuario(String idUsuario) {
        Usuario o = null;
        try {
            o = new Usuario();
            o.setIdUsuario(idUsuario);
            o.setActivo(true);
            o = usuarioMapper.obtener(o);
        } catch (Exception e){
            LOGGER.error("Error al obtener el usuario {} ", e.getMessage());
        }
        return o;
    }

    @Override
    public byte[] imprimirReporteHistoricoSoluciones(String idPrescripcion, String idSurtimiento, List<String> listaDiag,
                        Paciente_Extended paciente, SolucionExtended solucion, EntidadHospitalaria entidad, String folioPrescripcion, 
                        String folioSurtimiento, List<String> listaIdMedicamentos, List<String> listaClaveMedicamentos) throws Exception {
        
        Map<String, Object> parametros = new HashMap<>();        
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloReporte = RESOURCES.getString("TITULO_REP_HISTORICO_SOLUCIONES");        
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();        
        
        parametros.put("TITULO1", (tituloReporte != null) ? tituloReporte : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("USER_NAME", ((usuarioSession.getNombreUsuario() != null) ? usuarioSession.getNombreUsuario() : ""));
        
        parametros.put("folioPrescripcion", folioPrescripcion);
        parametros.put("folioSurtimiento", folioSurtimiento);
        parametros.put("idPrescripcion", idPrescripcion);
        parametros.put("idSurtimiento", idSurtimiento);
        parametros.put("idPaciente", paciente.getIdPaciente());
        parametros.put("idVisita", paciente.getIdVisita());
        parametros.put("nombrePaciente", paciente.getNombreCompleto());
        parametros.put("numeroPaciente", paciente.getPacienteNumero());
        parametros.put("fechaNacimiento",paciente.getFechaNacimiento());
        parametros.put("rfc", paciente.getRfc());
        parametros.put("curp", paciente.getCurp());
        parametros.put("servicio", paciente.getNombreEstructura());
        parametros.put("peso", String.valueOf(solucion.getPesoPaciente()));
        parametros.put("talla", String.valueOf(solucion.getTallaPaciente()));
        parametros.put("edad", paciente.getEdadPaciente());
        parametros.put("cama", paciente.getNombreCama());
        parametros.put("infusion", solucion.getInfusion());
        parametros.put("fechaProgramada", solucion.getFechaProgramada());
        parametros.put("velocidad",solucion.getVelocidad());
        parametros.put("estabilidad", solucion.getEstabilidad());
        parametros.put("ritmo", solucion.getRitmo());
        parametros.put("tipoEnvase", solucion.getTipoEnvase());
        parametros.put("tipoSolucion", solucion.getTipoSolucion());
        parametros.put("fechaMinistracion", solucion.getFechaMinistracion());
        parametros.put("ministro", solucion.getMinistro());
        parametros.put("listaDiagnosticos", getListaEstructurasAsString(listaDiag));
        parametros.put("listaIdMedicamentos", getListaEstructurasAsString(listaIdMedicamentos));
        parametros.put("listaClaveMedicamentos", getListaEstructurasAsString(listaClaveMedicamentos));
        
        parametros.put("fechaCaducidad", (solucion.getCaducidadMezcla() != null) ? FechaUtil.formatoFecha(solucion.getCaducidadMezcla(), "dd/MM/YYYY") : "" );
        parametros.put("lote", solucion.getLoteMezcla());
        parametros.put("nombreProtocolo", "NA".equals(solucion.getNombreProtocolo()) ? null : solucion.getNombreProtocolo());
        
        return getPdfBytes("jasper/re_historicoSoluciones.jasper", parametros);
    }
    
    public boolean imprimirExcelReporteHistoricoSoluciones(String idPrescripcion, String idSurtimiento, List<String> listaDiag,
                    Paciente_Extended paciente, SolucionExtended solucion, EntidadHospitalaria entidad, String folioPrescripcion,
                    String folioSurtimiento, List<String> listaIdMedicamentos, List<String> listaClaveMedicamentos) throws Exception {
        
        Map<String, Object> parametros = new HashMap<>();
        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloReporte = RESOURCES.getString("TITULO_REP_HISTORICO_SOLUCIONES");        
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        
        
        parametros.put("TITULO1", (tituloReporte != null) ? tituloReporte : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("USER_NAME", ((usuarioSession.getNombreUsuario() != null) ? usuarioSession.getNombreUsuario() : ""));
        
        parametros.put("folioPrescripcion", folioPrescripcion);
        parametros.put("folioSurtimiento", folioSurtimiento);
        parametros.put("idPrescripcion", idPrescripcion);
        parametros.put("idSurtimiento", idSurtimiento);
        parametros.put("idPaciente", paciente.getIdPaciente());
        parametros.put("idVisita", paciente.getIdVisita());
        parametros.put("nombrePaciente", paciente.getNombreCompleto());
        parametros.put("numeroPaciente", paciente.getPacienteNumero());
        parametros.put("fechaNacimiento",paciente.getFechaNacimiento());
        parametros.put("rfc", paciente.getRfc());
        parametros.put("curp", paciente.getCurp());
        parametros.put("servicio", paciente.getNombreEstructura());
        parametros.put("peso", paciente.getPesoPaciente());
        parametros.put("talla", paciente.getTallaPaciente());
        parametros.put("edad", paciente.getEdadPaciente());
        parametros.put("cama", paciente.getNombreCama());
        parametros.put("infusion", solucion.getInfusion());
        parametros.put("fechaProgramada", solucion.getFechaProgramada());
        parametros.put("velocidad",solucion.getVelocidad());
        parametros.put("estabilidad", solucion.getEstabilidad());
        parametros.put("ritmo", solucion.getRitmo());
        parametros.put("tipoEnvase", solucion.getTipoEnvase());
        parametros.put("tipoSolucion", solucion.getTipoSolucion());
        parametros.put("fechaMinistracion", solucion.getFechaMinistracion());
        parametros.put("ministro", solucion.getMinistro());
        parametros.put("listaDiagnosticos", getListaEstructurasAsString(listaDiag));
        parametros.put("listaIdMedicamentos", getListaEstructurasAsString(listaIdMedicamentos));
        parametros.put("listaClaveMedicamentos", getListaEstructurasAsString(listaClaveMedicamentos));
        
         parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);        
        return getXlsxStream("reporteHistoricoSoluciones.xlsx", "jasper/re_historicoSoluciones.jasper", parametros); 
        
    }
    
    @Override
    public byte[] reporteNutricionPareteral(Surtimiento_Extend surtimiento,Solucion solucion, EntidadHospitalaria entidad, String nombreUsuario, 
                    String alergias,String diagnosticos,String envase, NutricionParenteralExtended nutricionParenteral) throws Exception {  
        
        Map<String, Object> parametros = new HashMap<>();        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloOrdenPreparacion = "Orden De Nutrición Parenteral";//RESOURCES.getString("TITULO_REP_VALIDACION_SOLUCION");        
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
    
        parametros.put("IDSURTIMIENTO", surtimiento.getIdSurtimiento());
        parametros.put("Usuario",(nombreUsuario != null) ? nombreUsuario : "");
        parametros.put("TITULO1", (tituloOrdenPreparacion != null) ? tituloOrdenPreparacion : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("FECHAREGISTRO", nutricionParenteral.getFechaProgramada());
        parametros.put("FOLIO_MEZCLA", surtimiento.getFolio());
        parametros.put("MEDICO", surtimiento.getNombreMedico());
        parametros.put("CEDULA", surtimiento.getCedProfesional());
        parametros.put("PACIENTE", surtimiento.getNombrePaciente());
        parametros.put("EDAD", nutricionParenteral.getEdad() != null ? nutricionParenteral.getEdad().toString() : "");
        parametros.put("AFILIACION", surtimiento.getPacienteNumero());
        parametros.put("SERVICIO", surtimiento.getNombreEstructura());
        parametros.put("SEXO", surtimiento.getPacienteSexo());
        parametros.put("PESO", nutricionParenteral.getPeso() != null ? nutricionParenteral.getPeso().toString() : "");
        parametros.put("CAMA", surtimiento.getCama());
//        parametros.put("PISO", solucion.getPiso());
        parametros.put("DIAGNOSTICO", diagnosticos);
        parametros.put("ALERGIAS", alergias);
        parametros.put("ENVASE", envase);
        parametros.put("MUESTREO", solucion.getMuestreo()==1 ? "Si":"No");        
        parametros.put("CONSERVACION", solucion.getObservaciones());
        parametros.put("VOLUMEN", solucion.getVolumen());
        parametros.put("OBSERVACIONES", solucion.getObservaciones());
        parametros.put("IDESTRUCTURA",surtimiento.getIdEstructura());
        parametros.put("REQUERIMIENTO_FECHA",new Date());
        parametros.put("PESOTOTAL", nutricionParenteral.getPesoTotal().toString());
        parametros.put("TOTALCALORIAS", nutricionParenteral.getCaloriasTotal().toString());
        parametros.put("TOTALOSMOLARIDAD", nutricionParenteral.getTotalOsmolaridad().toString());        
        parametros.put("ESTABILIDAD", nutricionParenteral.getEstabilidad().toString());
        parametros.put("RESESTABILIDAD", nutricionParenteral.getResultEstabilidad());
        parametros.put("PRECIPITACION", nutricionParenteral.getPrecipitacion().toString());
        parametros.put("RESPRECIPITACION", nutricionParenteral.getResultPrecipitacion());
        parametros.put("RESOSMOLARIDAD", nutricionParenteral.getResulOsmolaridad());
        
        parametros.put("TALLA", nutricionParenteral.getTallaPaciente().toString() );
        parametros.put("PDIABETES", "");
        parametros.put("PRENALES", "");
        parametros.put("PHIPERTENSION", "");
        parametros.put("TIPMEZCLA", "NPT" );
        parametros.put("VIAADMON", nutricionParenteral.getViaAdministracion());
        parametros.put("PERFUSION", (nutricionParenteral.isPerfusionContinua()) ? "S": "N");
        parametros.put("VELOCIDAD", solucion.getVelocidad() );
        parametros.put("RITMO", solucion.getRitmo() );
        parametros.put("PROTOCOLO", "" );
        parametros.put("PROTLUZ", (nutricionParenteral.isProteccionLuz()) ? "S": "" );
        parametros.put("NOAGITAR", "");
        parametros.put("TEMREFRI", (nutricionParenteral.isTempRefrigeracion()) ? "S" : "N");
        parametros.put("TEMAMB",  (nutricionParenteral.isTempAmbiente()) ? "S" : "N");
        parametros.put("FECHAPRESCRIBE", "");
        parametros.put("FECHAVALIDA", "");
        parametros.put("FECHAPREPARA", "");
        parametros.put("FECHAINSPECCIONA", "");
        parametros.put("ESTATUSMEZ", "");
        parametros.put("CODIGOQR", solucion.getClaveMezcla()+","+solucion.getLoteMezcla()+","+FechaUtil.formatoCadena(solucion.getCaducidadMezcla(), "ddMMyyyy"));
        parametros.put("CLAVMEZ", solucion.getClaveMezcla());
        parametros.put("LOTEMEZ", solucion.getLoteMezcla());
        parametros.put("CADUCIDADMEZ", FechaUtil.formatoCadena(solucion.getCaducidadMezcla(), "ddMMyyyy"));
        parametros.put("SOBRELLENADO", nutricionParenteral.getSobrellenado());
        parametros.put("INSTRPREPARACION", nutricionParenteral.getInstruccionPreparacion());
        parametros.put("SUP_CORP", nutricionParenteral.getAreaCorporal().toString());
        parametros.put("TIEMPO_INFUS", "");
        parametros.put("USUARIO_VALIDA", "");
        parametros.put("USUARIO_PREPARA", "");
        parametros.put("USUARIO_MEDICO", "");
        parametros.put("USUARIO_INSPECCIONA","");
        
        return getPdfBytes("jasper/PrescripcionNutricionParenteral.jasper", parametros);
    }
    
    @Override
    public byte[] reporteFichaPrevencion(FichaPrevencionExtended fpe, EntidadHospitalaria entidad) throws Exception {
        
        Map<String, Object> parametros = new HashMap<>();        
        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        
        String tituloOrdenPreparacion = RESOURCES.getString("TITULO_REP_FICHA_PREVENCION");
        
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        
        parametros.put("IDFICHA", fpe.getIdPrevencion());
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("TITULO1", (tituloOrdenPreparacion != null) ? tituloOrdenPreparacion : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
                
        parametros.put("FECHA", fpe.getInsertFecha());
        
        parametros.put("FOLIO" , fpe.getFolio());
        parametros.put("AREA" , fpe.getNombreEstructura());
        parametros.put("TURNO" , fpe.getNombreTurno());
        parametros.put("FECHA_EJECUTA" , FechaUtil.formatoCadena(fpe.getFechaRelizaLimpieza(), "dd/MM/yyyy HH:mm"));
        parametros.put("USUARIO_EJECUTA" , fpe.getNombreUsuarioRealizaLimpieza());
        parametros.put("FEHCA_REGISTRA" , FechaUtil.formatoCadena(fpe.getInsertFecha(), "dd/MM/yyyy HH:mm"));
        parametros.put("USUARIO_REGISTRA" , fpe.getNombreUsuarioRegistra());
        parametros.put("FECHA_APRUEBA" , FechaUtil.formatoCadena(fpe.getFechaAprueba(), "dd/MM/yyyy HH:mm"));
        parametros.put("USUARIO_APRUEBA" , fpe.getNombreUsuarioAprueba());
        parametros.put("SANITIZANTE" , fpe.getSanitizante());
        parametros.put("AREA_ACOND" , fpe.isAreaAcondicionado() ? "X":"");
        parametros.put("AREA_ALMA" , fpe.isAreaAlmacenamiento() ? "X":"");
        parametros.put("AREA_DESIN" , fpe.isAreaDesinfeccion() ? "X":"");
        parametros.put("AREA_LAVA" , fpe.isAreaLavado() ? "X":"-");
        parametros.put("AREA_INSP" , fpe.isAreaInspeccion() ? "X":"");
        parametros.put("AREA_PREP" , fpe.isAreaPreparado() ? "X":"");
        parametros.put("EXT_EQUIP" , fpe.isExteriorEquipo() ? "X":"");
        parametros.put("INT_EQUIP" , fpe.isInteriorEquipo() ? "X":"");
        parametros.put("HERRAMIENTAS" , fpe.isHerramientas() ? "X":"");
        parametros.put("MANIJAS" , fpe.isManijas() ? "X":"");
        parametros.put("MOBILIARIO" , fpe.isMobiliario() ? "X":"");
        parametros.put("PAREDES" , fpe.isParedes() ? "X":"");
        parametros.put("PASILLO_ING" , fpe.isPasilloIngreso() ? "X":"");
        parametros.put("PISOS" , fpe.isPisos() ? "X":"");
        parametros.put("PUERTAS" , fpe.isPuertas() ? "X":"");
        parametros.put("VESTIDOR" , fpe.isVestidor() ? "X":"");
        parametros.put("VENTANAS" , fpe.isVentanas() ? "X":"");
        parametros.put("TRANSFER" , fpe.isTransfer() ? "X":"");
        parametros.put("COMENTARIOS_REG" , fpe.getComentariosRealizaLimpieza());
        parametros.put("COMENTARIOS_APR" , fpe.getComentariosAprueba());
        parametros.put("UBICACION" , fpe.getArea());
        parametros.put("ESTATUS" , fpe.getEstatusPrevencion());

        return getPdfBytes("jasper/FichaPrevencionContaminacion.jasper", parametros);
    }
    
    @Override
    public byte[] reporteRetiroMezcla(DevolucionMezclaExtended mezcla, EntidadHospitalaria entidad,String usuarioCreate) throws Exception{
    
        Map<String, Object> parametros = new HashMap<>();        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloDevolucion = RESOURCES.getString("TITULO_REP_RETIRO_MEZCLA"); 
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();       
        
        parametros.put("TITULO1", (tituloDevolucion != null) ? tituloDevolucion : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("usuarioCreate", usuarioCreate);
        
        parametros.put("idDevolucionMezcla", mezcla.getIdDevolucionMezcla());
        parametros.put("folio", mezcla.getFolio());
         parametros.put("creacion", mezcla.getCreacion());
        parametros.put("registro", mezcla.getRegistro());
        parametros.put("estatus", mezcla.getEstatus());
        parametros.put("orden", mezcla.getOrden());
        parametros.put("folioPrescripcion", mezcla.getFolioPrescripcion());
        parametros.put("area", mezcla.getArea());
        parametros.put("cama", mezcla.getCama());
        parametros.put("paciente", mezcla.getPaciente());
        parametros.put("medico",mezcla.getMedico());
        parametros.put("surtimiento", mezcla.getSurtimiento());
        parametros.put("claveMezcla", mezcla.getClaveMezcla());
        parametros.put("origen", mezcla.getOrigen());
        parametros.put("destino", mezcla.getDestino());
        parametros.put("nombre", mezcla.getNombre());
        parametros.put("idPresentacion", mezcla.getIdPresentacion());
        parametros.put("idVia", mezcla.getIdVia());
        parametros.put("volumenFinal", mezcla.getVolumenFinal());
        parametros.put("lote", mezcla.getLote());
        parametros.put("caducidad",mezcla.getFechaCaducidad());
        parametros.put("estabilidad", mezcla.getNoHorasestabilidad());
        parametros.put("usuario", mezcla.getUsuario());
        parametros.put("motivo", mezcla.getMotivoRetiro());
        parametros.put("destinofinal", mezcla.getDestinoFinal());
        parametros.put("fechaRetiro", mezcla.getFechaRetiro());
        parametros.put("clasificacion", mezcla.getClasificacion());
        parametros.put("comentarios", mezcla.getComentarios());
        parametros.put("riesgoPaciente", mezcla.getRiesgoPaciente());
        parametros.put("cuales", mezcla.getCuales());
        
     return getPdfBytes("jasper/repRetiroMezcla.jasper", parametros);
    }
    
    @Override
    public byte[] reporteDevolucionMezcla(DevolucionMezclaExtended mezcla, EntidadHospitalaria entidad,String usuarioCreate) throws Exception{
    
        Map<String, Object> parametros = new HashMap<>();        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloDevolucion = RESOURCES.getString("TITULO_REP_DEVOLUCION_MEZCLA");  
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();       
        
        parametros.put("TITULO1", (tituloDevolucion != null) ? tituloDevolucion : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("usuarioCreate", usuarioCreate);
        
        parametros.put("idDevolucionMezcla", mezcla.getIdDevolucionMezcla());
        parametros.put("folio", mezcla.getFolio());
         parametros.put("creacion", mezcla.getCreacion());
        parametros.put("registro", mezcla.getRegistro());
        parametros.put("estatus", mezcla.getEstatus());
        parametros.put("orden", mezcla.getOrden());
        parametros.put("folioPrescripcion", mezcla.getFolioPrescripcion());
        parametros.put("area", mezcla.getArea());
        parametros.put("cama", mezcla.getCama());
        parametros.put("paciente", mezcla.getPaciente());
        parametros.put("medico",mezcla.getMedico());
        parametros.put("surtimiento", mezcla.getSurtimiento());
        parametros.put("claveMezcla", mezcla.getClaveMezcla());
        parametros.put("origen", mezcla.getOrigen());
        parametros.put("destino", mezcla.getDestino());
        parametros.put("nombre", mezcla.getNombre());
        parametros.put("idPresentacion", mezcla.getIdPresentacion());
        parametros.put("idVia", mezcla.getIdVia());
        parametros.put("volumenFinal", mezcla.getVolumenFinal());
        parametros.put("lote", mezcla.getLote());
        parametros.put("caducidad",mezcla.getFechaCaducidad());
        parametros.put("estabilidad", mezcla.getNoHorasestabilidad());
        parametros.put("usuario", mezcla.getUsuario());
        parametros.put("motivo", mezcla.getMotivoRetiro());
        
        parametros.put("retencion", mezcla.getRetencion());
        parametros.put("fechaRetiro", mezcla.getFechaRetiro());
        parametros.put("reutilizar", mezcla.getReutilizar());
        parametros.put("fechaLimite", mezcla.getFechaLimite());
        parametros.put("comentarios", mezcla.getComentarios());
        parametros.put("merma", mezcla.getMerma());
        parametros.put("destruir", mezcla.getDestruir());
        parametros.put("sugerencia", mezcla.getSugerencia());
        
     return getPdfBytes("jasper/repDevolucionMezcla.jasper", parametros);
    }

    @Override
    public byte[] imprimeReporteAccionesUsuario(ParamBusquedaReporte paramReporte, EntidadHospitalaria entidad) throws Exception {
        
        Map<String, Object> parametros = new HashMap<>();        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloBitacora = RESOURCES.getString("TITULO_BITACORA_ACCIONES_USUARIO");  
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();       
        
        parametros.put("TITULO1", (tituloBitacora != null) ? tituloBitacora : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("fechaInicio", FechaUtil.formatoFecha(paramReporte.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(paramReporte.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaUsuarios", getListaNombreUsuariosAsString(paramReporte.getListUsuarios()));
        
        return getPdfBytes("jasper/bitacoraAccionesUsuario.jasper", parametros);
    }
    
    @Override
    public boolean imprimeExcelBitacoraAcciones(ParamBusquedaReporte paramReporte, EntidadHospitalaria entidad) {
        Map<String, Object> parametros = new HashMap<>();
        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloOrdenPreparacion = RESOURCES.getString("TITULO_BITACORA_ACCIONES_USUARIO");        
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        
        
        parametros.put("TITULO1", (tituloOrdenPreparacion != null) ? tituloOrdenPreparacion : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("DIRECCION1", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("fechaInicio", FechaUtil.formatoFecha(paramReporte.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(paramReporte.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("listaUsuarios", getListaNombreUsuariosAsString(paramReporte.getListUsuarios()));
        
         parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);        
        return getXlsxStream("bitacoraAccionesUsuario.xlsx", "jasper/bitacoraAccionesUsuario.jasper", parametros); 
        
    }
    
    
    @Override
    public byte[] imprimeListaDistribucion(EntidadHospitalaria entidad, NotaDispenColect_Extended sd) throws Exception {
        
        Map<String, Object> parametros = new HashMap<>();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
                
        String titulo1 = entidad.getNombre();
        String titulo2 = sd.getNombreAreaDistribuye();
        String titulo3 = RESOURCES.getString("NDC.TITULO3");
        String titulo4 = RESOURCES.getString("NDC.TITULO4");
        String nombreDocumento = RESOURCES.getString("NDC.NOM_DOC");
        String codigoDocumento = RESOURCES.getString("NDC.COD_DOC");
        String licenciaSanitaria = RESOURCES.getString("NDC.LIC_SANIT");
        String domicilio = entidad.getDomicilio();       
                
        parametros.put("NOMBRE_DOCUMENTO", (nombreDocumento != null) ? nombreDocumento : "NOTA DE DISPENSACION DE MEZCLAS ESTERILES");
        
        parametros.put("TITULO1", (titulo1 != null) ? titulo1.toUpperCase() : " ");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2.toUpperCase() : " ");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3.toUpperCase() : " ");
        parametros.put("DIRECCION1", (domicilio != null) ? domicilio : " ");
        
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
                
        parametros.put("FOLIO", (sd.getFolio() != null) ? sd.getFolio() : " ");
        parametros.put("FECHA", (sd.getFechaEntrega()!= null) ? sd.getFechaEntrega(): " ");
        parametros.put("ESTATUS", (sd.getEstatusNotaDispencolect()!= null) ? sd.getEstatusNotaDispencolect() : " ");
        parametros.put("AREA", (sd.getNombreEstructura()!= null) ? sd.getNombreEstructura() : " ");
        parametros.put("TURNO", (sd.getNombreTurno() != null) ? sd.getNombreTurno() : " ");
        parametros.put("COMENTARIOS_ENTREGA", (sd.getComentarios() != null) ? sd.getComentarios() : " ");
        
        parametros.put("USUARIO_ENTREGA", (sd.getNombreUsuarioEntrega() != null) ? sd.getNombreUsuarioEntrega() : " ");
        parametros.put("FECHA_ENTREGA", (sd.getFechaEntrega()!= null) ? FechaUtil.formatoFecha(sd.getFechaEntrega(), "yyyy/MM/dd HH:mm") : " ");

        parametros.put("USUARIO_DISPENSA", (sd.getNombreUsuarioDispensa()!= null) ? sd.getNombreUsuarioDispensa() : "");
        parametros.put("FECHA_DISPENSA", (sd.getFechaDispensa()!= null) ? FechaUtil.formatoFecha(sd.getFechaDispensa(), "yyyy/MM/dd HH:mm") : "");
        
        parametros.put("USUARIO_DISTRIBUYE", (sd.getNombreUsuarioDistribuye()!= null) ? sd.getNombreUsuarioDistribuye(): "");
        parametros.put("FECHA_DISTRIBUYE", (sd.getFechaDistribuye()!= null) ? FechaUtil.formatoFecha(sd.getFechaDistribuye(), "yyyy/MM/dd HH:mm") : "");
        
        parametros.put("USUARIO_RECIBE", (sd.getNombreUsuarioRecibe()!= null) ? sd.getNombreUsuarioRecibe() : "");
        parametros.put("FECHA_RECIBE", (sd.getFechaRecibe()!= null) ? FechaUtil.formatoFecha(sd.getFechaRecibe(), "yyyy/MM/dd HH:mm") : "");
        
        parametros.put("NUM_TOTAL_MEZCLAS", (sd.getNumeroMezclas() != null) ? sd.getNumeroMezclas().toString() : "");
                
        parametros.put("NOMBRE_DOCUMENTO", (nombreDocumento != null) ? nombreDocumento : "");
        parametros.put("CODIGO_DOCUMENTO", (codigoDocumento != null) ? codigoDocumento : "");
        parametros.put("LICENCIA_SANITARIA", (licenciaSanitaria != null) ? licenciaSanitaria : "");
        parametros.put("IDNOTADISPENCOLEC", (sd.getIdNotaDispenColect()!= null) ? sd.getIdNotaDispenColect() : "");
                        
        return getPdfBytes("jasper/DispensacionColectiva.jasper", parametros);
        
    }

    @Override
    public byte[] generConsumosPdf(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        
        String titulo1 = entidad.getNombre();
        String titulo2 = "";//sd.getNombreAreaDistribuye();
        String titulo3 = RESOURCES.getString("AREAS_LISTADISTRIBUCION_MEZCLAS1");
        String titulo4 = RESOURCES.getString("AREAS_LISTADISTRIBUCION_MEZCLAS2");
        String domicilio = entidad.getDomicilio();       
        String nombreDocumento = RESOURCES.getString("TITULO_LISTADISTRIBUCION_MEZCLAS");
        String codigoDocumento = RESOURCES.getString("CODIGO_LISTADISTRIBUCION_MEZCLAS");
        String licenciaSanitaria = RESOURCES.getString("LICENCIA_SANITARIA");
        
        Map<String, Object> parametros = new HashMap<>();
        
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "HOSPITAL JUÁREZ DE MÉXICO");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "CETRO DE MEZCLAS");
//        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "DIVISIÓN DE PLANEACIÓN ESTRATÉGICA");
//        parametros.put("TITULO3", (titulo4 != null) ? titulo3 : "DIVISIÓN DE CALIDAD DE LA ATENCIÓN");
        parametros.put("DIRECCION1", (domicilio != null) ? domicilio : "Av Instituto Politécnico Nacional 5160, Magdalena de las Salinas, Gustavo A. Madero, 07760 Ciudad de México, CDMX");
        
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("USER_NAME", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));

        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("tipoInsumo", p.getTipoInsumo());
        parametros.put("listaInsumos", p.getListaInsumos());
        parametros.put("listaMedicos", p.getListMedicos());
        parametros.put("listaUsuarios", p.getListaUsuarios());
        parametros.put("listaPaciente", p.getListaPaciente());
        parametros.put("listaEstructuras", p.getListaEstructuras());
        parametros.put("listaAlmacenes", p.getListaAlmacenes());
        return getPdfBytes("jasper/reporteConsumos.jasper", parametros);
    }
    
    @Override
    public boolean generaConsumosExcel(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        
        String titulo1 = entidad.getNombre();
        String titulo2 = "";//sd.getNombreAreaDistribuye();
        String titulo3 = RESOURCES.getString("AREAS_LISTADISTRIBUCION_MEZCLAS1");
        String titulo4 = RESOURCES.getString("AREAS_LISTADISTRIBUCION_MEZCLAS2");
        String domicilio = entidad.getDomicilio();       
        String nombreDocumento = RESOURCES.getString("TITULO_LISTADISTRIBUCION_MEZCLAS");
        String codigoDocumento = RESOURCES.getString("CODIGO_LISTADISTRIBUCION_MEZCLAS");
        String licenciaSanitaria = RESOURCES.getString("LICENCIA_SANITARIA");
        
        Map<String, Object> parametros = new HashMap<>();
        
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "HOSPITAL JUÁREZ DE MÉXICO");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "CETRO DE MEZCLAS");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "DIVISIÓN DE PLANEACIÓN ESTRATÉGICA");
        parametros.put("TITULO3", (titulo4 != null) ? titulo3 : "DIVISIÓN DE CALIDAD DE LA ATENCIÓN");
        parametros.put("DIRECCION1", (domicilio != null) ? domicilio : "Av Instituto Politécnico Nacional 5160, Magdalena de las Salinas, Gustavo A. Madero, 07760 Ciudad de México, CDMX");
        
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("USER_NAME", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));

        parametros.put("fechaInicio", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("fechaFin", FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("tipoInsumo", p.getTipoInsumo());
        parametros.put("listaInsumos", p.getListaInsumos());
        parametros.put("listaMedicos", p.getListMedicos());
        parametros.put("listaUsuarios", p.getListaUsuarios());
        parametros.put("listaPaciente", p.getListaPaciente());
        parametros.put("listaEstructuras", p.getListaEstructuras());
        parametros.put("listaAlmacenes", p.getListaAlmacenes());
    
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("reporteConsumos.xlsx", "jasper/reporteConsumos.jasper", parametros);
    }

    @Override
    public byte[] imprimePrescripcionMezcla(
            Surtimiento_Extend surtimiento,Solucion solucion, EntidadHospitalaria entidad, String nombreUsuario
            , String alergias, String diagnosticos, String envase, Prescripcion p) throws Exception{  
        
        Map<String, Object> parametros = new HashMap<>();        
        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        
        String nombreDocumento = RESOURCES.getString("TITULO_DOC_PRESCRIPCION_MEZCLA");
        String nombreEntidad = entidad.getNombre();
        String domicilioEntidad = entidad.getDomicilio();
                
        parametros.put("TITULO1", (nombreDocumento != null) ? nombreDocumento : "");
        parametros.put("TITULO2", (nombreEntidad != null) ? nombreEntidad : "");
        parametros.put("DIRECCION1", (domicilioEntidad != null) ? domicilioEntidad : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("IDPRESCRIPCION", p.getIdPrescripcion());
        parametros.put("ESTATUS_PRESCRIPCION", surtimiento.getEstatusPrescripcion());
        
        parametros.put("FOLIO_PRESCRIPCION", surtimiento.getFolioPrescripcion());
        parametros.put("FECHA_PRESCRIPCION", surtimiento.getFechaProgramada());

        parametros.put("PACIENTE", surtimiento.getNombrePaciente());
        parametros.put("NO_PACIENTE", surtimiento.getPacienteNumero());
        parametros.put("SERVICIO", surtimiento.getNombreEstructura());
        parametros.put("SEXO", surtimiento.getPacienteSexo());
        parametros.put("CAMA", surtimiento.getCama());
        parametros.put("DIAGNOSTICOS", diagnosticos);
        parametros.put("ALERGIAS", alergias);
        parametros.put("ENVASE", envase);
        
        if (solucion != null) {
            String fechaNacimiento = (surtimiento.getFechaNacimiento() != null) ? FechaUtil.formatoFecha(surtimiento.getFechaNacimiento(), "dd/MM/yyyy" ) : "";
            parametros.put("EDAD", fechaNacimiento);
            parametros.put("PESO", String.valueOf(solucion.getPesoPaciente()));
            parametros.put("PISO", solucion.getPiso());
            parametros.put("MUESTREO", (solucion.getMuestreo()!=null) ? (solucion.getMuestreo()==1) ? "S" : "N" : "N");
            parametros.put("VOLUMEN", (solucion.getVolumen()!=null) ? solucion.getVolumen().toString() : "");
            
            Usuario u;
            if (p.getIdMedico()!= null) {
                u = obtenerUsuario(p.getIdMedico());
                String usuario = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoPaterno();
                parametros.put("USUARIO_MEDICO", usuario.trim());
                parametros.put("MEDICO", usuario);
                parametros.put("CEDULA", surtimiento.getCedProfesional());
                if (p.getFechaPrescripcion() != null) {
// TODO: no sale esta fecha en la impresión
                    parametros.put("FECHAPRESCRIBE  ", FechaUtil.formatoCadena(p.getFechaPrescripcion(), "dd/MM/yyyy HH:mm"));
                }
            }
                        
            parametros.put("SUP_CORP", String.valueOf(solucion.getAreaCorporal()));
            parametros.put("TALLA", String.valueOf(solucion.getTallaPaciente()));
            parametros.put("PDIABETES", (surtimiento.isDiabetes()) ? "S" : "N");
            parametros.put("PRENALES", (surtimiento.isProblemasRenales()) ? "S" : "N");
            parametros.put("PHIPERTENSION", (surtimiento.isHipertension()) ? "S" : "N");
            parametros.put("TIPMEZCLA", surtimiento.getTipoSolucion());
            
            if (solucion.getIdViaAdministracion() != null){
                ViaAdministracion viaAdmon = obtenerViaAdministracion(solucion.getIdViaAdministracion());
                if (viaAdmon != null) {
                    parametros.put("VIAADMON", viaAdmon.getNombreViaAdministracion());
                }
            }
            
            parametros.put("PERFUSION", (surtimiento.isPerfusionContinua()) ? "S" : "N");
            parametros.put("VELOCIDAD", (solucion.getVelocidad() != null) ? String.valueOf(solucion.getVelocidad()) : "");
            parametros.put("TIEMPO_INFUS", (solucion.getMinutosInfusion() != null) ? String.valueOf(solucion.getMinutosInfusion()) : "");
            
            if (solucion.getIdProtocolo()==0){
                parametros.put("PROTOCOLO", "");
            } else {
                Protocolo protocolo = obtenerProtocolo(solucion.getIdProtocolo());
                if (protocolo!= null){
                    parametros.put("PROTOCOLO", protocolo.getClaveProtocolo() + " - " + protocolo.getDescripcionProtocolo());
                }
            }
            
            if (solucion.getIdDiagnostico() == null || solucion.getIdDiagnostico().trim().isEmpty()){
                parametros.put("DIAGNOSTICO", "");
            } else {
                Diagnostico diagnostico = obtenerDiagnostico(solucion.getIdDiagnostico());
                if (diagnostico != null){
                    parametros.put("DIAGNOSTICO", diagnostico.getClave() + " + " + diagnostico.getDescripcion());
                }
            }
        }
        return getPdfBytes("jasper/prescripcionMezcla.jasper", parametros);
    }
    
    @Override
    public byte[] imprimePrescripcionMezclaNPT(
            EntidadHospitalaria entidad, NutricionParenteralExtended nutricionParenteral
            , Surtimiento_Extend surtimiento, Solucion solucion, Prescripcion p
            , String alergias, String diagnosticos, Usuario usuarioSesion) throws Exception{
        
        Map<String, Object> parametros = new HashMap<>();        
        
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        
        String nombreDocumento = RESOURCES.getString("TITULO_DOC_PRESCRIPCION_MEZCLA");
        String nombreEntidad = entidad.getNombre();
        String domicilioEntidad = entidad.getDomicilio();
                
        parametros.put("TITULO1", (nombreDocumento != null) ? nombreDocumento : "");
        parametros.put("TITULO2", (nombreEntidad != null) ? nombreEntidad : "");
        parametros.put("DIRECCION1", (domicilioEntidad != null) ? domicilioEntidad : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        if (p != null) {
            if (p.getFechaPrescripcion() != null) {
                parametros.put("FECHAPRESCRIBE  ", FechaUtil.formatoCadena(p.getInsertFecha(), "dd/MM/yyyy HH:mm"));
                parametros.put("FECHA_PRESCRIPCION", p.getFechaPrescripcion());
            }
        }
        
        if (surtimiento!= null){
            parametros.put("IDPRESCRIPCION", surtimiento.getIdPrescripcion());
            parametros.put("ESTATUS_PRESCRIPCION", surtimiento.getEstatusPrescripcion());
            parametros.put("FOLIO_PRESCRIPCION", surtimiento.getFolioPrescripcion());
            parametros.put("PACIENTE", surtimiento.getNombrePaciente());
            parametros.put("NO_PACIENTE", surtimiento.getPacienteNumero());
            parametros.put("SERVICIO", surtimiento.getNombreEstructura());
            parametros.put("SEXO", surtimiento.getPacienteSexo());
            parametros.put("CAMA", surtimiento.getCama());
            parametros.put("ENVASE", surtimiento.getNombreEnvaseContenedor());
            parametros.put("VIAADMON", surtimiento.getNombreViaAdministracion());
            parametros.put("PERFUSION", (surtimiento.isPerfusionContinua()) ? "S" : "");
            parametros.put("TIPMEZCLA", surtimiento.getTipoSolucion());
        }
        
        if (nutricionParenteral != null) {
            String fechaNacimiento = (nutricionParenteral.getFechaNacimiento() != null) ? FechaUtil.formatoFecha(nutricionParenteral.getFechaNacimiento(), "dd/MM/yyyy" ) : "";
            parametros.put("EDAD", fechaNacimiento);
            parametros.put("TALLA", nutricionParenteral.getTallaPaciente().toString());
            parametros.put("PESO", nutricionParenteral.getPeso() != null ? nutricionParenteral.getPeso().toString() : "");
            parametros.put("SUP_CORP", nutricionParenteral.getAreaCorporal().toString());
            parametros.put("VOLUMEN", (nutricionParenteral.getVolumenTotal()!=null) ? nutricionParenteral.getVolumenTotal().toString() : "");
            parametros.put("PERFUSION", (nutricionParenteral.isPerfusionContinua()) ? "S" : "N");
            parametros.put("PDIABETES", (nutricionParenteral.isDiabetes()) ? "S" : "");
            parametros.put("PRENALES", (nutricionParenteral.isProblemasRenales()) ? "S" : "");
            parametros.put("PHIPERTENSION", (nutricionParenteral.isHipertension()) ? "S" : "");
            parametros.put("NOMBRE_DOCUMENTO", "PRESCRIPCION NPT");
            parametros.put("TIEMPO_INFUS", nutricionParenteral.getHorasInfusion().toString());
            parametros.put("UNI_TIEMP", "hrs");
            parametros.put("VIAADMON", nutricionParenteral.getViaAdministracion());
            parametros.put("DIAGNOSTICOS", diagnosticos);
            
//            parametros.put("PESOTOTAL", nutricionParenteral.getPesoTotal().toString());
            parametros.put("CAL_TOT", nutricionParenteral.getCaloriasTotal().toString());
            parametros.put("CAL_PROT", nutricionParenteral.getCaloriasTotal().toString());
            parametros.put("CAL_NOPROT", nutricionParenteral.getCaloriasTotal().toString());
            parametros.put("OSMOLARIDAD", nutricionParenteral.getTotalOsmolaridad().toString());
//            parametros.put("TOTALOSMOLARIDAD", nutricionParenteral.getTotalOsmolaridad().toString());
//            parametros.put("ESTABILIDAD", nutricionParenteral.getEstabilidad().toString());
//            parametros.put("RESESTABILIDAD", nutricionParenteral.getResultEstabilidad());
//            parametros.put("PRECIPITACION", nutricionParenteral.getPrecipitacion().toString());
//            parametros.put("RESPRECIPITACION", nutricionParenteral.getResultPrecipitacion());
//            parametros.put("RESOSMOLARIDAD", nutricionParenteral.getResulOsmolaridad());
//            parametros.put("PROTLUZ", (nutricionParenteral.isProteccionLuz()) ? "S" : "");
//            parametros.put("TEMREFRI", (nutricionParenteral.isTempRefrigeracion()) ? "S" : "N");
//            parametros.put("TEMAMB", (nutricionParenteral.isTempAmbiente()) ? "S" : "N");
//            parametros.put("SOBRELLENADO", nutricionParenteral.getSobrellenado());
//            parametros.put("INSTRPREPARACION", nutricionParenteral.getInstruccionPreparacion());
//            parametros.put("VIAADMON", nutricionParenteral.getViaAdministracion());           

            Usuario u;
            if (nutricionParenteral.getIdMedico()!= null) {
                u = obtenerUsuario(nutricionParenteral.getIdMedico());
                if ( u != null){
                    String usuario = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoPaterno();
                    parametros.put("MEDICO", usuario.trim());
                    parametros.put("CEDULA", u.getCedProfesional());
                }
            }
        }
        
        if (solucion != null) {
            parametros.put("VELOCIDAD", String.valueOf(solucion.getVelocidad()));
//            parametros.put("PISO", solucion.getPiso());
//            parametros.put("MUESTREO", (solucion.getMuestreo()!=null) ? (solucion.getMuestreo()==1) ? "Si" : "" : "");
//            parametros.put("SUP_CORP", String.valueOf(solucion.getAreaCorporal()));
//            parametros.put("TALLA", String.valueOf(solucion.getTallaPaciente()));
//            parametros.put("PESO", String.valueOf(solucion.getPesoPaciente()));            

        }
        return getPdfBytes("jasper/PrescripcionNutricionParenteral.jasper", parametros);
    }
    
    @Override
    public byte[] generaMezclasPdf(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        
        String titulo1 = entidad.getNombre();
        String titulo2 = p.getNombreEstructura();
        String domicilio = entidad.getDomicilio();
        
        Map<String, Object> parametros = new HashMap<>();
        
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "HOSPITAL JUÁREZ DE MÉXICO");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "CENTRO DE MEZCLAS");
        parametros.put("DIRECCION1", (domicilio != null) ? domicilio : "Av Instituto Politécnico Nacional 5160, Magdalena de las Salinas, Gustavo A. Madero, 07760 Ciudad de México, CDMX");
        
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("NOMBRE_USUARIO", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        parametros.put("USER_NAME", ((usuarioSession.getNombreUsuario() != null) ? usuarioSession.getNombreUsuario() : ""));

        parametros.put("FECHA_INICIAL", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("FECHA_FINAL",  FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("FECHAINICIO", FechaUtil.formatoFecha(p.getFechaInicio(), "dd-MM-yyyy HH:mm:ss"));
        parametros.put("FECHAFIN",  FechaUtil.formatoFecha(p.getFechaFin(), "dd-MM-yyyy HH:mm:ss"));
        parametros.put("SERVICIO", p.getNombreEstructura() != null ? p.getNombreEstructura() : "Todos");
        parametros.put("IDESTATUSSOLUCION", p.getIdEstatusSolucion());
        parametros.put("ESTATUSSOLUCION", p.getEstatusSolucion());
        parametros.put("ID_ESTRUCTURA", p.getIdEstructura());
        parametros.put("ID_MEDICO", p.getIdMedico());
        parametros.put("ID_PACIENTE", p.getIdPaciente());
//        parametros.put("ID_INSUMO", p.getId());
//        parametros.put("ID_USUARIO", p.getListaUsuarios());
        if(p.getEstatusSolucion() != null) {
            return getPdfBytes("jasper/reporteMezclas.jasper", parametros);
        } else {
            return getPdfBytes("jasper/reporteMezclasOrig.jasper", parametros);
        }
        
    }
    
    @Override
    public boolean generaMezclasExcel(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        
        String titulo1 = entidad.getNombre();
        String titulo2 = p.getNombreEstructura();
        String domicilio = entidad.getDomicilio();
        
        Map<String, Object> parametros = new HashMap<>();
        
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "HOSPITAL JUÁREZ DE MÉXICO");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "CENTRO DE MEZCLAS");
        parametros.put("DIRECCION1", (domicilio != null) ? domicilio : "Av Instituto Politécnico Nacional 5160, Magdalena de las Salinas, Gustavo A. Madero, 07760 Ciudad de México, CDMX");
        
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        
        parametros.put("NOMBRE_USUARIO", ((usuarioSession.getNombre() != null) ? usuarioSession.getNombre() : "") + " " + ((usuarioSession.getApellidoPaterno() != null) ? usuarioSession.getApellidoPaterno() : "") + " " + ((usuarioSession.getApellidoMaterno() != null) ? usuarioSession.getApellidoMaterno() : ""));
        parametros.put("USER_NAME", ((usuarioSession.getNombreUsuario() != null) ? usuarioSession.getNombreUsuario() : ""));

        parametros.put("FECHA_INICIAL", FechaUtil.formatoFecha(p.getFechaInicio(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("FECHA_FINAL",  FechaUtil.formatoFecha(p.getFechaFin(), "yyyy-MM-dd HH:mm:ss"));
        parametros.put("FECHAINICIO", FechaUtil.formatoFecha(p.getFechaInicio(), "dd-MM-yyyy HH:mm:ss"));
        parametros.put("FECHAFIN",  FechaUtil.formatoFecha(p.getFechaFin(), "dd-MM-yyyy HH:mm:ss"));
        parametros.put("SERVICIO", p.getNombreEstructura() != null ? p.getNombreEstructura() : "Todos");
        parametros.put("IDESTATUSSOLUCION", p.getIdEstatusSolucion());
        parametros.put("ESTATUSSOLUCION", p.getEstatusSolucion());
        parametros.put("ID_ESTRUCTURA", p.getIdEstructura());
        parametros.put("ID_MEDICO", p.getIdMedico());
        parametros.put("ID_PACIENTE", p.getIdPaciente());
//        parametros.put("ID_INSUMO", p.getId());
//        parametros.put("ID_USUARIO", p.getListaUsuarios());    
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        if(p.getEstatusSolucion() != null) {
            return getXlsxStream("reporteMezclas.xlsx", "jasper/reporteMezclas.jasper", parametros);
        } else {
            return getXlsxStream("reporteMezclas.xlsx", "jasper/reporteMezclasOrig.jasper", parametros);
        }
        
    }

    @Override
    public byte[] imprimeReporteEntregaExistConsolidadas(Medicamento_Extended p, EntidadHospitalaria entidad, String nombreUsuario, String nombreEstructura , String idEstructura , String nombreTurno, Integer idTurno) throws Exception{
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String titulo1 = RESOURCES.getString("TITULO_ENTREGA_EXISTENCIAS_CONSOLIDADAS");
        String titulo2 = (entidad.getNombre() == null) ? "" : entidad.getNombre();
        String titulo3 = (entidad.getNombre() == null) ? "" : entidad.getDomicilio();
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (titulo1 != null) ? titulo1 : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("NOMBRE_USUARIO", (nombreUsuario != null) ? nombreUsuario : "");
        parametros.put("NOMBRE_TURNO", (nombreTurno != null) ? nombreTurno : "");
        parametros.put("NOMBRE_ESTRUCTURA", (nombreEstructura != null) ? nombreEstructura : "");
        parametros.put("ID_TURNO", (idTurno != null) ? idTurno.toString() : "");
        parametros.put("ID_ESTRUCTURA", (idEstructura != null) ? idEstructura : "");
        
        parametros.put("listInsumo", getListaMedicamentosAsString(p.getListInsumos(), "CLAVEINSTITUCIONAL"));
        
        return getPdfBytes("jasper/repEntregaExistConsolidadas.jasper", parametros);
    }
    
    
    @Override
    public boolean imprimeExcel_ReporteEntregaExist_Consolidadas(Medicamento_Extended p, String pathTmp, String url , EntidadHospitalaria entidad, String nombreUsuario, String nombreEstructura , String idEstructura , String nombreTurno, Integer idTurno) throws Exception {
        String rutaLogo1 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo1.png").getPath();
        String rutaLogo2 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo2.png").getPath();
        String rutaLogo3 = ReportesServiceImpl.class.getClassLoader().getResource("images/logo3.png").getPath();
        String tituloExcelExist_Consolidadas = RESOURCES.getString("TITULO_ENTREGA_EXISTENCIAS_CONSOLIDADAS");
        String titulo2 = entidad.getNombre();
        String titulo3 = entidad.getDomicilio();
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO1", (tituloExcelExist_Consolidadas != null) ? tituloExcelExist_Consolidadas : "");
        parametros.put("TITULO2", (titulo2 != null) ? titulo2 : "");
        parametros.put("TITULO3", (titulo3 != null) ? titulo3 : "");
        parametros.put("LOGO1", (rutaLogo1 != null) ? rutaLogo1 : null);
        parametros.put("LOGO2", (rutaLogo2 != null) ? rutaLogo2 : null);
        parametros.put("LOGO3", (rutaLogo3 != null) ? rutaLogo3 : null);
        parametros.put("NOMBRE_USUARIO", (nombreUsuario != null) ? nombreUsuario : "");
        parametros.put("NOMBRE_TURNO", (nombreTurno != null) ? nombreTurno : "");
        parametros.put("NOMBRE_ESTRUCTURA", (nombreEstructura != null) ? nombreEstructura : "");
        parametros.put("ID_TURNO", (idTurno != null) ? idTurno.toString() : "");
        parametros.put("ID_ESTRUCTURA", (idEstructura != null) ? idEstructura : "");
        
        parametros.put("listInsumo", getListaMedicamentosAsString(p.getListInsumos(), "CLAVEINSTITUCIONAL"));
        
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        return getXlsxStream("reporteExistenciasConsolidadas.xlsx", "jasper/repEntregaExistConsolidadas.jasper", parametros);
        }
    
}

