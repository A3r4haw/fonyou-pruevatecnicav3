/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.common.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import mx.mc.init.Constantes;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento_Extended;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.util.Comunes;
import mx.mc.ws.common.vo.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author gcruz
 */
@WebService(serviceName = "musCatalogos")
public class MusCatalogos extends SpringBeanAutowiringSupport {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MusCatalogos.class);

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EstructuraService estructuraService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Web service operation obtener lista de medicamentos por clave y tipo
     *
     * @param claveMedicamento
     * @param tipo
     * @return
     * @throws java.lang.Exception
     */
    @WebMethod(operationName = "insumosList")
    @WebResult(name = "insumoItem")
    public Response insumosList(@WebParam(name = "claveMedicamento") String claveMedicamento, @WebParam(name = "tipo") String tipo) throws Exception {
        List<Medicamento_Extended> listaMediamentos = null;
        Response response = new Response();
        try {
            response.setStatus(true);
            if (tipo != null && tipo.isEmpty()) {
                tipo = null;
            }

            if (claveMedicamento != null
                    && claveMedicamento.isEmpty()) {
                claveMedicamento = null;
            }

            response = validaCamposInsumoList(tipo, claveMedicamento);
            if (response.isStatus()) {
                listaMediamentos = medicamentoService.obtenerListaMedicamentos(claveMedicamento, tipo);
                if (listaMediamentos != null && !listaMediamentos.isEmpty()) {
                    response.setLista(listaMediamentos);
                    response.setMensaje("Exito");
                    response.setCodigo("509");
                } else {
                    response.setCodigo("Error 501");
                    response.setMensaje("No se encontró información con estos parámetros de búsqueda  " + claveMedicamento + " y " + tipo);
                }
            }

        } catch (Exception ex) {
            response.setCodigo("Error 501");
            response.setMensaje("No se encontró información con estos parámetros de búsqueda  " + claveMedicamento + " y " + tipo);
            LOGGER.error("Error al obtener Medicamentos para WebService: {}", ex.getMessage());
        }
        return response;
    }

    /**
     * Web service operation Obtiene lista de inventarios de Medicamento
     */
    @WebMethod(operationName = "obtenerListInvenatrio")
    @WebResult(name = "insumoItem")
    public Response obtenerListInvenatrio(@WebParam(name = "claveMedicamento") String claveMedicamento, @WebParam(name = "claveAlmacen") String claveAlmacen) {

        List<Medicamento_Extended> listaMediamentos = null;
        Response response = new Response();
        try {
            if (claveMedicamento != null
                    && claveMedicamento.isEmpty()) {
                claveMedicamento = null;
            }

            if (claveAlmacen != null
                    && claveAlmacen.isEmpty()) {
                claveAlmacen = null;
            }

            response = validaCamposInventarioList(claveMedicamento, claveAlmacen);
            if (response.isStatus()) {
                Estructura estructura = estructuraService.getEstructuraForClave(claveAlmacen);
                if (estructura.getIdTipoAlmacen().equals(Constantes.SUBALMACEN)) {
                    listaMediamentos = medicamentoService.obtenerListInventarioSubAlmacen(claveMedicamento, claveAlmacen);
                } else {
                    listaMediamentos = medicamentoService.obtenerListInventario(claveMedicamento, claveAlmacen);
                }
                if (listaMediamentos != null && !listaMediamentos.isEmpty()) {
                    response.setLista(listaMediamentos);
                    response.setMensaje("Exito");
                    response.setCodigo("509");
                } else {
                    response.setCodigo("Error 501");
                    response.setMensaje("No se encontró información con estos parámetros de búsqueda  clave: " + claveMedicamento + " y con Almacén: " + claveAlmacen);
                }
            }

        } catch (Exception ex) {
            response.setCodigo("Error 501");
            response.setMensaje("No se encontró información con estos parámetros de búsqueda  " + claveMedicamento + " y " + claveAlmacen);
            LOGGER.error("Error al obtener Medicamentos para WebService: {}", ex.getMessage());
        }
        return response;
    }

    private static Response validaCamposInsumoList(String tipo, String claveMedicamento) {
        Response response = new Response();
        response.setStatus(true);
        if (tipo != null
                && !Comunes.isValidInteger(tipo)
                && !tipo.isEmpty()) {
            response.setCodigo("Error 503");
            response.setMensaje("El valor de tipo debe ser NÚMERICO ");
            response.setStatus(false);
            return response;
        }
        if (claveMedicamento != null
                && Comunes.isValidInteger(claveMedicamento)) {
            response.setCodigo("Error 502");
            response.setMensaje("El valor de claveMedicamento debe ser CADENA ");
            response.setStatus(false);
        }
        return response;
    }

    private static Response validaCamposInventarioList(String claveMedicamento, String claveAlmacen) {
        Response response = new Response();
        response.setStatus(true);
        if (claveMedicamento == null || claveAlmacen == null) {
            response.setCodigo("Error 505");
            response.setMensaje("Es necesario Enviar parametros de busqueda");
            response.setStatus(false);
            return response;
        }
        if (Comunes.isValidInteger(claveMedicamento)) {
            response.setCodigo("Error 502");
            response.setMensaje("El valor de claveMedicamento debe ser CADENA ");
            response.setStatus(false);
            return response;
        }
        return response;
    }
}
