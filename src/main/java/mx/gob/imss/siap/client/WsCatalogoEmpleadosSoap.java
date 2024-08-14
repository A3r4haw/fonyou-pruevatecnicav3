package mx.gob.imss.siap.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.3.5
 * 2020-03-11T16:41:49.885-06:00
 * Generated source version: 3.3.5
 *
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "wsCatalogoEmpleadosSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface WsCatalogoEmpleadosSoap {

    @WebMethod(operationName = "ConsultaCatalogoTrabajadores", action = "http://tempuri.org/ConsultaCatalogoTrabajadores")
    @RequestWrapper(localName = "ConsultaCatalogoTrabajadores", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ConsultaCatalogoTrabajadores")
    @ResponseWrapper(localName = "ConsultaCatalogoTrabajadoresResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ConsultaCatalogoTrabajadoresResponse")
    @WebResult(name = "ConsultaCatalogoTrabajadoresResult", targetNamespace = "http://tempuri.org/")
    public ConsultaCatalogoTrabajadoresResponse.ConsultaCatalogoTrabajadoresResult consultaCatalogoTrabajadores(

        @WebParam(name = "Delegación", targetNamespace = "http://tempuri.org/")
        java.lang.String delegacion,
        @WebParam(name = "Matrícula", targetNamespace = "http://tempuri.org/")
        java.lang.String matricula,
        @WebParam(name = "RFC", targetNamespace = "http://tempuri.org/")
        java.lang.String rfc,
        @WebParam(name = "CURP", targetNamespace = "http://tempuri.org/")
        java.lang.String curp,
        @WebParam(name = "NSS", targetNamespace = "http://tempuri.org/")
        java.lang.String nss
    );
}
