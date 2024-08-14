/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import mx.mc.model.Surtimiento_Extend;

/**
 *
 * @author apalacios
 */
public class ImpExpediciones implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String AGRUPACION_SERVICIO = "NOR";
    private long id;
    private String expedicion;
    private String documento;
    private String agrupacionServicio;
    private String tipoMovimientoCliente;
    private int prioridad;
    private int urgencia;
    private Integer numAlbaranes;
    private String observacionAlbaran;
    private String observacionAgencia;
    private String observacionAlmacen;
    private String cliente;
    private String nomCli;
    private String dirEnvio;
    private String cpEnvio;
    private String poblacionEnvio;
    private String provinciaEnvio;
    private String paisEnvio;
    private String portes;
    private int imprAlbaran;
    private int imprEtiqueta;
    private String fechaPrevistaServicio;
    private String remitente;
    private String agencia;
    private Double importeAgencia;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private String nombreSscc;
    private String nombre2Sscc;
    private String direccionSscc;
    private String cpSscc;
    private String poblacionSscc;
    private String provinciaSscc;
    private String paisSscc;
    private String departamento;
    private String puntoVenta;
    private String lugarDestino;
    private String empresaSscc;
    private int datosSscc;
    private String grupoEmbalaje;
    private String telefonoEnvio;
    private Integer orden;
    private String dirEnvio2;
    private String suReferencia;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private String datoExtra4;
    private String datoExtra5;
    private String centro;
    private String telefonoSscc;
    private String tipoServicioTransporte;
    private String tipoFlujo;
    private String tipoAlmacen;
    private int imprFactura;
    private int imprPacking;
    private Date fechaCreacion;
    private String clasificaciones;
    private String valoresClasificaciones;
    private String personaContacto;
    private String emailEnvio;
    private int aviso;
    private String idiomaPacking;
    private String idiomaEtiqueta;
    private String idiomaAlbaran;
    private String centroCoste;
    private String matricula;
    private String tipoMesa;
    private String estadoBloqueo;
    private String muelleSalida;
    private String observacionEmbalaje;
    private String numPedEci;
    private String agenciasPosibles;
    private String tanda;
    private Integer prioridadTanda;
    private int agrupaPacking;
    private String condicionAgrupa;
    private int controlParking;
    private String tipoDecisionAgencia;
    private String condicionPosibleAgrupa;
    private String tipoParking;
    private String parkingPropuesto;
    private String expedicionAgencia;
    private String agente;
    private int contraReembolso;
    private String zonaVisualizacion;
    private Integer bultoIni;
    private int preAgrupa;
    private int servirCompleto;
    private String condicionPreAgrupa;
    private Integer posicionMesa;
    private String barcodeAlbaran;
    private int previaTraspasoManual;
    private int bultosTemporales;
    private String mensajeFinalizacion;
    private String documentoOrigen;
    private String concertacionCliente;
    private String fechaEntregaCliente;
    private String tipoEmpaquetado;
    private String incoTerm;
    private String propietario;
    private String eanDirEntrega;
    private String idComprador;
    private String idVendedor;
    private String idProductRecipient;
    private String nombrePais;
    private String codigoRegion;

    public ImpExpediciones(Surtimiento_Extend surtimientoExtend, String tipoExpedicion, String tipoAlmacen) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.expedicion = surtimientoExtend.getFolio();
        this.documento = surtimientoExtend.getFolioPrescripcion();
        this.agrupacionServicio = AGRUPACION_SERVICIO;
        this.tipoMovimientoCliente = tipoExpedicion;
        this.prioridad = 0;
        this.urgencia = surtimientoExtend.getTipoPrescripcion().equalsIgnoreCase("U") ? 1 : 0;
        this.numAlbaranes = 0;
        this.observacionAlbaran = surtimientoExtend.getNombreEstructura();
        this.observacionAgencia = surtimientoExtend.getComentarios(); //checar dif. entre getComentario() y getComentarios()
        this.observacionAlmacen = null;
        this.cliente = null;
        this.nomCli = null;
        this.dirEnvio = null;
        this.cpEnvio = null;
        this.poblacionEnvio = null;
        this.provinciaEnvio = null;
        this.paisEnvio = null;
        this.portes = null;
        this.imprAlbaran = 0;
        this.imprEtiqueta = 0;
        this.fechaPrevistaServicio = sdf.format(surtimientoExtend.getFechaProgramada());
        this.remitente = null;
        this.agencia = null;
        this.importeAgencia = 0.0;
        this.tratado = "N";
        this.error = null;
        this.fechaTratado = null;
        this.version = 1;
        this.nombreSscc = null;
        this.nombre2Sscc = null;
        this.direccionSscc = null;
        this.cpSscc = null;
        this.poblacionSscc = null;
        this.provinciaSscc = null;
        this.paisSscc = null;
        this.departamento = null;
        this.puntoVenta = null;
        this.lugarDestino = null;
        this.empresaSscc = null;
        this.datosSscc = 0;
        this.grupoEmbalaje = null;
        this.telefonoEnvio = null;
        this.orden = 0;
        this.dirEnvio2 = null;
        this.suReferencia = null;
        this.datoExtra1 = null;
        this.datoExtra2 = null;
        this.datoExtra3 = null;
        this.datoExtra4 = null;
        this.datoExtra5 = null;
        this.centro = null;
        this.telefonoSscc = null;
        this.tipoServicioTransporte = null;
        this.tipoFlujo = null;
        this.tipoAlmacen = tipoAlmacen;
        this.imprFactura = 0;
        this.imprPacking = 0;
        this.fechaCreacion = new Date();
        this.clasificaciones = null;
        this.valoresClasificaciones = null;
        this.personaContacto = null;
        this.emailEnvio = null;
        this.aviso = 0;
        this.idiomaPacking = null;
        this.idiomaEtiqueta = null;
        this.idiomaAlbaran = null;
        this.centroCoste = null;
        this.matricula = null;
        this.tipoMesa = null;
        this.estadoBloqueo = null;
        this.muelleSalida = null;
        this.observacionEmbalaje = null;
        this.numPedEci = null;
        this.agenciasPosibles = null;
        this.tanda = null;
        this.prioridadTanda = 0;
        this.agrupaPacking = 0;
        this.condicionAgrupa = null;
        this.controlParking = 0;
        this.tipoDecisionAgencia = null;
        this.condicionPosibleAgrupa = null;
        this.tipoParking = null;
        this.parkingPropuesto = null;
        this.expedicionAgencia = null;
        this.agente = null;
        this.contraReembolso = 0;
        this.zonaVisualizacion = null;
        this.bultoIni = 0;
        this.preAgrupa = 0;
        this.servirCompleto = 0;
        this.condicionPreAgrupa = null;
        this.posicionMesa = 0;
        this.barcodeAlbaran = null;
        this.previaTraspasoManual = 0;
        this.bultosTemporales = 0;
        this.mensajeFinalizacion = null;
        this.documentoOrigen = null;
        this.concertacionCliente = null;
        this.fechaEntregaCliente = null;
        this.tipoEmpaquetado = null;
        this.incoTerm = null;
        this.propietario = null;
        this.eanDirEntrega = null;
        this.idComprador = null;
        this.idVendedor = null;
        this.idProductRecipient = null;
        this.nombrePais = null;
        this.codigoRegion = null;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(String expedicion) {
        this.expedicion = expedicion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getAgrupacionServicio() {
        return agrupacionServicio;
    }

    public void setAgrupacionServicio(String agrupacionServicio) {
        this.agrupacionServicio = agrupacionServicio;
    }

    public String getTipoMovimientoCliente() {
        return tipoMovimientoCliente;
    }

    public void setTipoMovimientoCliente(String tipoMovimientoCliente) {
        this.tipoMovimientoCliente = tipoMovimientoCliente;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public Integer getNumAlbaranes() {
        return numAlbaranes;
    }

    public void setNumAlbaranes(Integer numAlbaranes) {
        this.numAlbaranes = numAlbaranes;
    }

    public String getObservacionAlbaran() {
        return observacionAlbaran;
    }

    public void setObservacionAlbaran(String observacionAlbaran) {
        this.observacionAlbaran = observacionAlbaran;
    }

    public String getObservacionAgencia() {
        return observacionAgencia;
    }

    public void setObservacionAgencia(String observacionAgencia) {
        this.observacionAgencia = observacionAgencia;
    }

    public String getObservacionAlmacen() {
        return observacionAlmacen;
    }

    public void setObservacionAlmacen(String observacionAlmacen) {
        this.observacionAlmacen = observacionAlmacen;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getDirEnvio() {
        return dirEnvio;
    }

    public void setDirEnvio(String dirEnvio) {
        this.dirEnvio = dirEnvio;
    }

    public String getCpEnvio() {
        return cpEnvio;
    }

    public void setCpEnvio(String cpEnvio) {
        this.cpEnvio = cpEnvio;
    }

    public String getPoblacionEnvio() {
        return poblacionEnvio;
    }

    public void setPoblacionEnvio(String poblacionEnvio) {
        this.poblacionEnvio = poblacionEnvio;
    }

    public String getProvinciaEnvio() {
        return provinciaEnvio;
    }

    public void setProvinciaEnvio(String provinciaEnvio) {
        this.provinciaEnvio = provinciaEnvio;
    }

    public String getPaisEnvio() {
        return paisEnvio;
    }

    public void setPaisEnvio(String paisEnvio) {
        this.paisEnvio = paisEnvio;
    }

    public String getPortes() {
        return portes;
    }

    public void setPortes(String portes) {
        this.portes = portes;
    }

    public int getImprAlbaran() {
        return imprAlbaran;
    }

    public void setImprAlbaran(int imprAlbaran) {
        this.imprAlbaran = imprAlbaran;
    }

    public int getImprEtiqueta() {
        return imprEtiqueta;
    }

    public void setImprEtiqueta(int imprEtiqueta) {
        this.imprEtiqueta = imprEtiqueta;
    }

    public String getFechaPrevistaServicio() {
        return fechaPrevistaServicio;
    }

    public void setFechaPrevistaServicio(String fechaPrevistaServicio) {
        this.fechaPrevistaServicio = fechaPrevistaServicio;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Double getImporteAgencia() {
        return importeAgencia;
    }

    public void setImporteAgencia(Double importeAgencia) {
        this.importeAgencia = importeAgencia;
    }

    public String getTratado() {
        return tratado;
    }

    public void setTratado(String tratado) {
        this.tratado = tratado;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getFechaTratado() {
        return fechaTratado;
    }

    public void setFechaTratado(Date fechaTratado) {
        this.fechaTratado = fechaTratado;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getNombreSscc() {
        return nombreSscc;
    }

    public void setNombreSscc(String nombreSscc) {
        this.nombreSscc = nombreSscc;
    }

    public String getNombre2Sscc() {
        return nombre2Sscc;
    }

    public void setNombre2Sscc(String nombre2Sscc) {
        this.nombre2Sscc = nombre2Sscc;
    }

    public String getDireccionSscc() {
        return direccionSscc;
    }

    public void setDireccionSscc(String direccionSscc) {
        this.direccionSscc = direccionSscc;
    }

    public String getCpSscc() {
        return cpSscc;
    }

    public void setCpSscc(String cpSscc) {
        this.cpSscc = cpSscc;
    }

    public String getPoblacionSscc() {
        return poblacionSscc;
    }

    public void setPoblacionSscc(String poblacionSscc) {
        this.poblacionSscc = poblacionSscc;
    }

    public String getProvinciaSscc() {
        return provinciaSscc;
    }

    public void setProvinciaSscc(String provinciaSscc) {
        this.provinciaSscc = provinciaSscc;
    }

    public String getPaisSscc() {
        return paisSscc;
    }

    public void setPaisSscc(String paisSscc) {
        this.paisSscc = paisSscc;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(String puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public String getLugarDestino() {
        return lugarDestino;
    }

    public void setLugarDestino(String lugarDestino) {
        this.lugarDestino = lugarDestino;
    }

    public String getEmpresaSscc() {
        return empresaSscc;
    }

    public void setEmpresaSscc(String empresaSscc) {
        this.empresaSscc = empresaSscc;
    }

    public int getDatosSscc() {
        return datosSscc;
    }

    public void setDatosSscc(int datosSscc) {
        this.datosSscc = datosSscc;
    }

    public String getGrupoEmbalaje() {
        return grupoEmbalaje;
    }

    public void setGrupoEmbalaje(String grupoEmbalaje) {
        this.grupoEmbalaje = grupoEmbalaje;
    }

    public String getTelefonoEnvio() {
        return telefonoEnvio;
    }

    public void setTelefonoEnvio(String telefonoEnvio) {
        this.telefonoEnvio = telefonoEnvio;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getDirEnvio2() {
        return dirEnvio2;
    }

    public void setDirEnvio2(String dirEnvio2) {
        this.dirEnvio2 = dirEnvio2;
    }

    public String getSuReferencia() {
        return suReferencia;
    }

    public void setSuReferencia(String suReferencia) {
        this.suReferencia = suReferencia;
    }

    public String getDatoExtra1() {
        return datoExtra1;
    }

    public void setDatoExtra1(String datoExtra1) {
        this.datoExtra1 = datoExtra1;
    }

    public String getDatoExtra2() {
        return datoExtra2;
    }

    public void setDatoExtra2(String datoExtra2) {
        this.datoExtra2 = datoExtra2;
    }

    public String getDatoExtra3() {
        return datoExtra3;
    }

    public void setDatoExtra3(String datoExtra3) {
        this.datoExtra3 = datoExtra3;
    }

    public String getDatoExtra4() {
        return datoExtra4;
    }

    public void setDatoExtra4(String datoExtra4) {
        this.datoExtra4 = datoExtra4;
    }

    public String getDatoExtra5() {
        return datoExtra5;
    }

    public void setDatoExtra5(String datoExtra5) {
        this.datoExtra5 = datoExtra5;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getTelefonoSscc() {
        return telefonoSscc;
    }

    public void setTelefonoSscc(String telefonoSscc) {
        this.telefonoSscc = telefonoSscc;
    }

    public String getTipoServicioTransporte() {
        return tipoServicioTransporte;
    }

    public void setTipoServicioTransporte(String tipoServicioTransporte) {
        this.tipoServicioTransporte = tipoServicioTransporte;
    }

    public String getTipoFlujo() {
        return tipoFlujo;
    }

    public void setTipoFlujo(String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public int getImprFactura() {
        return imprFactura;
    }

    public void setImprFactura(int imprFactura) {
        this.imprFactura = imprFactura;
    }

    public int getImprPacking() {
        return imprPacking;
    }

    public void setImprPacking(int imprPacking) {
        this.imprPacking = imprPacking;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getClasificaciones() {
        return clasificaciones;
    }

    public void setClasificaciones(String clasificaciones) {
        this.clasificaciones = clasificaciones;
    }

    public String getValoresClasificaciones() {
        return valoresClasificaciones;
    }

    public void setValoresClasificaciones(String valoresClasificaciones) {
        this.valoresClasificaciones = valoresClasificaciones;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getEmailEnvio() {
        return emailEnvio;
    }

    public void setEmailEnvio(String emailEnvio) {
        this.emailEnvio = emailEnvio;
    }

    public int getAviso() {
        return aviso;
    }

    public void setAviso(int aviso) {
        this.aviso = aviso;
    }

    public String getIdiomaPacking() {
        return idiomaPacking;
    }

    public void setIdiomaPacking(String idiomaPacking) {
        this.idiomaPacking = idiomaPacking;
    }

    public String getIdiomaEtiqueta() {
        return idiomaEtiqueta;
    }

    public void setIdiomaEtiqueta(String idiomaEtiqueta) {
        this.idiomaEtiqueta = idiomaEtiqueta;
    }

    public String getIdiomaAlbaran() {
        return idiomaAlbaran;
    }

    public void setIdiomaAlbaran(String idiomaAlbaran) {
        this.idiomaAlbaran = idiomaAlbaran;
    }

    public String getCentroCoste() {
        return centroCoste;
    }

    public void setCentroCoste(String centroCoste) {
        this.centroCoste = centroCoste;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipoMesa() {
        return tipoMesa;
    }

    public void setTipoMesa(String tipoMesa) {
        this.tipoMesa = tipoMesa;
    }

    public String getEstadoBloqueo() {
        return estadoBloqueo;
    }

    public void setEstadoBloqueo(String estadoBloqueo) {
        this.estadoBloqueo = estadoBloqueo;
    }

    public String getMuelleSalida() {
        return muelleSalida;
    }

    public void setMuelleSalida(String muelleSalida) {
        this.muelleSalida = muelleSalida;
    }

    public String getObservacionEmbalaje() {
        return observacionEmbalaje;
    }

    public void setObservacionEmbalaje(String observacionEmbalaje) {
        this.observacionEmbalaje = observacionEmbalaje;
    }

    public String getNumPedEci() {
        return numPedEci;
    }

    public void setNumPedEci(String numPedEci) {
        this.numPedEci = numPedEci;
    }

    public String getAgenciasPosibles() {
        return agenciasPosibles;
    }

    public void setAgenciasPosibles(String agenciasPosibles) {
        this.agenciasPosibles = agenciasPosibles;
    }

    public String getTanda() {
        return tanda;
    }

    public void setTanda(String tanda) {
        this.tanda = tanda;
    }

    public Integer getPrioridadTanda() {
        return prioridadTanda;
    }

    public void setPrioridadTanda(Integer prioridadTanda) {
        this.prioridadTanda = prioridadTanda;
    }

    public int getAgrupaPacking() {
        return agrupaPacking;
    }

    public void setAgrupaPacking(int agrupaPacking) {
        this.agrupaPacking = agrupaPacking;
    }

    public String getCondicionAgrupa() {
        return condicionAgrupa;
    }

    public void setCondicionAgrupa(String condicionAgrupa) {
        this.condicionAgrupa = condicionAgrupa;
    }

    public int getControlParking() {
        return controlParking;
    }

    public void setControlParking(int controlParking) {
        this.controlParking = controlParking;
    }

    public String getTipoDecisionAgencia() {
        return tipoDecisionAgencia;
    }

    public void setTipoDecisionAgencia(String tipoDecisionAgencia) {
        this.tipoDecisionAgencia = tipoDecisionAgencia;
    }

    public String getCondicionPosibleAgrupa() {
        return condicionPosibleAgrupa;
    }

    public void setCondicionPosibleAgrupa(String condicionPosibleAgrupa) {
        this.condicionPosibleAgrupa = condicionPosibleAgrupa;
    }

    public String getTipoParking() {
        return tipoParking;
    }

    public void setTipoParking(String tipoParking) {
        this.tipoParking = tipoParking;
    }

    public String getParkingPropuesto() {
        return parkingPropuesto;
    }

    public void setParkingPropuesto(String parkingPropuesto) {
        this.parkingPropuesto = parkingPropuesto;
    }

    public String getExpedicionAgencia() {
        return expedicionAgencia;
    }

    public void setExpedicionAgencia(String expedicionAgencia) {
        this.expedicionAgencia = expedicionAgencia;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public int getContraReembolso() {
        return contraReembolso;
    }

    public void setContraReembolso(int contraReembolso) {
        this.contraReembolso = contraReembolso;
    }

    public String getZonaVisualizacion() {
        return zonaVisualizacion;
    }

    public void setZonaVisualizacion(String zonaVisualizacion) {
        this.zonaVisualizacion = zonaVisualizacion;
    }

    public Integer getBultoIni() {
        return bultoIni;
    }

    public void setBultoIni(Integer bultoIni) {
        this.bultoIni = bultoIni;
    }

    public int getPreAgrupa() {
        return preAgrupa;
    }

    public void setPreAgrupa(int preAgrupa) {
        this.preAgrupa = preAgrupa;
    }

    public int getServirCompleto() {
        return servirCompleto;
    }

    public void setServirCompleto(int servirCompleto) {
        this.servirCompleto = servirCompleto;
    }

    public String getCondicionPreAgrupa() {
        return condicionPreAgrupa;
    }

    public void setCondicionPreAgrupa(String condicionPreAgrupa) {
        this.condicionPreAgrupa = condicionPreAgrupa;
    }

    public Integer getPosicionMesa() {
        return posicionMesa;
    }

    public void setPosicionMesa(Integer posicionMesa) {
        this.posicionMesa = posicionMesa;
    }

    public String getBarcodeAlbaran() {
        return barcodeAlbaran;
    }

    public void setBarcodeAlbaran(String barcodeAlbaran) {
        this.barcodeAlbaran = barcodeAlbaran;
    }

    public int getPreviaTraspasoManual() {
        return previaTraspasoManual;
    }

    public void setPreviaTraspasoManual(int previaTraspasoManual) {
        this.previaTraspasoManual = previaTraspasoManual;
    }

    public int getBultosTemporales() {
        return bultosTemporales;
    }

    public void setBultosTemporales(int bultosTemporales) {
        this.bultosTemporales = bultosTemporales;
    }

    public String getMensajeFinalizacion() {
        return mensajeFinalizacion;
    }

    public void setMensajeFinalizacion(String mensajeFinalizacion) {
        this.mensajeFinalizacion = mensajeFinalizacion;
    }

    public String getDocumentoOrigen() {
        return documentoOrigen;
    }

    public void setDocumentoOrigen(String documentoOrigen) {
        this.documentoOrigen = documentoOrigen;
    }

    public String getConcertacionCliente() {
        return concertacionCliente;
    }

    public void setConcertacionCliente(String concertacionCliente) {
        this.concertacionCliente = concertacionCliente;
    }

    public String getFechaEntregaCliente() {
        return fechaEntregaCliente;
    }

    public void setFechaEntregaCliente(String fechaEntregaCliente) {
        this.fechaEntregaCliente = fechaEntregaCliente;
    }

    public String getTipoEmpaquetado() {
        return tipoEmpaquetado;
    }

    public void setTipoEmpaquetado(String tipoEmpaquetado) {
        this.tipoEmpaquetado = tipoEmpaquetado;
    }

    public String getIncoTerm() {
        return incoTerm;
    }

    public void setIncoTerm(String incoTerm) {
        this.incoTerm = incoTerm;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getEanDirEntrega() {
        return eanDirEntrega;
    }

    public void setEanDirEntrega(String eanDirEntrega) {
        this.eanDirEntrega = eanDirEntrega;
    }

    public String getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(String idComprador) {
        this.idComprador = idComprador;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getIdProductRecipient() {
        return idProductRecipient;
    }

    public void setIdProductRecipient(String idProductRecipient) {
        this.idProductRecipient = idProductRecipient;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getCodigoRegion() {
        return codigoRegion;
    }

    public void setCodigoRegion(String codigoRegion) {
        this.codigoRegion = codigoRegion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + Objects.hashCode(this.expedicion);
        hash = 37 * hash + Objects.hashCode(this.documento);
        hash = 37 * hash + Objects.hashCode(this.agrupacionServicio);
        hash = 37 * hash + Objects.hashCode(this.tipoMovimientoCliente);
        hash = 37 * hash + this.prioridad;
        hash = 37 * hash + this.urgencia;
        hash = 37 * hash + Objects.hashCode(this.numAlbaranes);
        hash = 37 * hash + Objects.hashCode(this.observacionAlbaran);
        hash = 37 * hash + Objects.hashCode(this.observacionAgencia);
        hash = 37 * hash + Objects.hashCode(this.observacionAlmacen);
        hash = 37 * hash + Objects.hashCode(this.cliente);
        hash = 37 * hash + Objects.hashCode(this.nomCli);
        hash = 37 * hash + Objects.hashCode(this.dirEnvio);
        hash = 37 * hash + Objects.hashCode(this.cpEnvio);
        hash = 37 * hash + Objects.hashCode(this.poblacionEnvio);
        hash = 37 * hash + Objects.hashCode(this.provinciaEnvio);
        hash = 37 * hash + Objects.hashCode(this.paisEnvio);
        hash = 37 * hash + Objects.hashCode(this.portes);
        hash = 37 * hash + this.imprAlbaran;
        hash = 37 * hash + this.imprEtiqueta;
        hash = 37 * hash + Objects.hashCode(this.fechaPrevistaServicio);
        hash = 37 * hash + Objects.hashCode(this.remitente);
        hash = 37 * hash + Objects.hashCode(this.agencia);
        hash = 37 * hash + Objects.hashCode(this.importeAgencia);
        hash = 37 * hash + Objects.hashCode(this.tratado);
        hash = 37 * hash + Objects.hashCode(this.error);
        hash = 37 * hash + Objects.hashCode(this.fechaTratado);
        hash = 37 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 37 * hash + Objects.hashCode(this.nombreSscc);
        hash = 37 * hash + Objects.hashCode(this.nombre2Sscc);
        hash = 37 * hash + Objects.hashCode(this.direccionSscc);
        hash = 37 * hash + Objects.hashCode(this.cpSscc);
        hash = 37 * hash + Objects.hashCode(this.poblacionSscc);
        hash = 37 * hash + Objects.hashCode(this.provinciaSscc);
        hash = 37 * hash + Objects.hashCode(this.paisSscc);
        hash = 37 * hash + Objects.hashCode(this.departamento);
        hash = 37 * hash + Objects.hashCode(this.puntoVenta);
        hash = 37 * hash + Objects.hashCode(this.lugarDestino);
        hash = 37 * hash + Objects.hashCode(this.empresaSscc);
        hash = 37 * hash + this.datosSscc;
        hash = 37 * hash + Objects.hashCode(this.grupoEmbalaje);
        hash = 37 * hash + Objects.hashCode(this.telefonoEnvio);
        hash = 37 * hash + Objects.hashCode(this.orden);
        hash = 37 * hash + Objects.hashCode(this.dirEnvio2);
        hash = 37 * hash + Objects.hashCode(this.suReferencia);
        hash = 37 * hash + Objects.hashCode(this.datoExtra1);
        hash = 37 * hash + Objects.hashCode(this.datoExtra2);
        hash = 37 * hash + Objects.hashCode(this.datoExtra3);
        hash = 37 * hash + Objects.hashCode(this.datoExtra4);
        hash = 37 * hash + Objects.hashCode(this.datoExtra5);
        hash = 37 * hash + Objects.hashCode(this.centro);
        hash = 37 * hash + Objects.hashCode(this.telefonoSscc);
        hash = 37 * hash + Objects.hashCode(this.tipoServicioTransporte);
        hash = 37 * hash + Objects.hashCode(this.tipoFlujo);
        hash = 37 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 37 * hash + this.imprFactura;
        hash = 37 * hash + this.imprPacking;
        hash = 37 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 37 * hash + Objects.hashCode(this.clasificaciones);
        hash = 37 * hash + Objects.hashCode(this.valoresClasificaciones);
        hash = 37 * hash + Objects.hashCode(this.personaContacto);
        hash = 37 * hash + Objects.hashCode(this.emailEnvio);
        hash = 37 * hash + this.aviso;
        hash = 37 * hash + Objects.hashCode(this.idiomaPacking);
        hash = 37 * hash + Objects.hashCode(this.idiomaEtiqueta);
        hash = 37 * hash + Objects.hashCode(this.idiomaAlbaran);
        hash = 37 * hash + Objects.hashCode(this.centroCoste);
        hash = 37 * hash + Objects.hashCode(this.matricula);
        hash = 37 * hash + Objects.hashCode(this.tipoMesa);
        hash = 37 * hash + Objects.hashCode(this.estadoBloqueo);
        hash = 37 * hash + Objects.hashCode(this.muelleSalida);
        hash = 37 * hash + Objects.hashCode(this.observacionEmbalaje);
        hash = 37 * hash + Objects.hashCode(this.numPedEci);
        hash = 37 * hash + Objects.hashCode(this.agenciasPosibles);
        hash = 37 * hash + Objects.hashCode(this.tanda);
        hash = 37 * hash + Objects.hashCode(this.prioridadTanda);
        hash = 37 * hash + this.agrupaPacking;
        hash = 37 * hash + Objects.hashCode(this.condicionAgrupa);
        hash = 37 * hash + this.controlParking;
        hash = 37 * hash + Objects.hashCode(this.tipoDecisionAgencia);
        hash = 37 * hash + Objects.hashCode(this.condicionPosibleAgrupa);
        hash = 37 * hash + Objects.hashCode(this.tipoParking);
        hash = 37 * hash + Objects.hashCode(this.parkingPropuesto);
        hash = 37 * hash + Objects.hashCode(this.expedicionAgencia);
        hash = 37 * hash + Objects.hashCode(this.agente);
        hash = 37 * hash + this.contraReembolso;
        hash = 37 * hash + Objects.hashCode(this.zonaVisualizacion);
        hash = 37 * hash + Objects.hashCode(this.bultoIni);
        hash = 37 * hash + this.preAgrupa;
        hash = 37 * hash + this.servirCompleto;
        hash = 37 * hash + Objects.hashCode(this.condicionPreAgrupa);
        hash = 37 * hash + Objects.hashCode(this.posicionMesa);
        hash = 37 * hash + Objects.hashCode(this.barcodeAlbaran);
        hash = 37 * hash + this.previaTraspasoManual;
        hash = 37 * hash + this.bultosTemporales;
        hash = 37 * hash + Objects.hashCode(this.mensajeFinalizacion);
        hash = 37 * hash + Objects.hashCode(this.documentoOrigen);
        hash = 37 * hash + Objects.hashCode(this.concertacionCliente);
        hash = 37 * hash + Objects.hashCode(this.fechaEntregaCliente);
        hash = 37 * hash + Objects.hashCode(this.tipoEmpaquetado);
        hash = 37 * hash + Objects.hashCode(this.incoTerm);
        hash = 37 * hash + Objects.hashCode(this.propietario);
        hash = 37 * hash + Objects.hashCode(this.eanDirEntrega);
        hash = 37 * hash + Objects.hashCode(this.idComprador);
        hash = 37 * hash + Objects.hashCode(this.idVendedor);
        hash = 37 * hash + Objects.hashCode(this.idProductRecipient);
        hash = 37 * hash + Objects.hashCode(this.nombrePais);
        hash = 37 * hash + Objects.hashCode(this.codigoRegion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImpExpediciones other = (ImpExpediciones) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.prioridad != other.prioridad) {
            return false;
        }
        if (this.urgencia != other.urgencia) {
            return false;
        }
        if (this.imprAlbaran != other.imprAlbaran) {
            return false;
        }
        if (this.imprEtiqueta != other.imprEtiqueta) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.datosSscc != other.datosSscc) {
            return false;
        }
        if (this.imprFactura != other.imprFactura) {
            return false;
        }
        if (this.imprPacking != other.imprPacking) {
            return false;
        }
        if (this.aviso != other.aviso) {
            return false;
        }
        if (this.agrupaPacking != other.agrupaPacking) {
            return false;
        }
        if (this.controlParking != other.controlParking) {
            return false;
        }
        if (this.contraReembolso != other.contraReembolso) {
            return false;
        }
        if (this.preAgrupa != other.preAgrupa) {
            return false;
        }
        if (this.servirCompleto != other.servirCompleto) {
            return false;
        }
        if (this.previaTraspasoManual != other.previaTraspasoManual) {
            return false;
        }
        if (this.bultosTemporales != other.bultosTemporales) {
            return false;
        }
        if (!Objects.equals(this.expedicion, other.expedicion)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.agrupacionServicio, other.agrupacionServicio)) {
            return false;
        }
        if (!Objects.equals(this.tipoMovimientoCliente, other.tipoMovimientoCliente)) {
            return false;
        }
        if (!Objects.equals(this.observacionAlbaran, other.observacionAlbaran)) {
            return false;
        }
        if (!Objects.equals(this.observacionAgencia, other.observacionAgencia)) {
            return false;
        }
        if (!Objects.equals(this.observacionAlmacen, other.observacionAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.nomCli, other.nomCli)) {
            return false;
        }
        if (!Objects.equals(this.dirEnvio, other.dirEnvio)) {
            return false;
        }
        if (!Objects.equals(this.cpEnvio, other.cpEnvio)) {
            return false;
        }
        if (!Objects.equals(this.poblacionEnvio, other.poblacionEnvio)) {
            return false;
        }
        if (!Objects.equals(this.provinciaEnvio, other.provinciaEnvio)) {
            return false;
        }
        if (!Objects.equals(this.paisEnvio, other.paisEnvio)) {
            return false;
        }
        if (!Objects.equals(this.portes, other.portes)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrevistaServicio, other.fechaPrevistaServicio)) {
            return false;
        }
        if (!Objects.equals(this.remitente, other.remitente)) {
            return false;
        }
        if (!Objects.equals(this.agencia, other.agencia)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.nombreSscc, other.nombreSscc)) {
            return false;
        }
        if (!Objects.equals(this.nombre2Sscc, other.nombre2Sscc)) {
            return false;
        }
        if (!Objects.equals(this.direccionSscc, other.direccionSscc)) {
            return false;
        }
        if (!Objects.equals(this.cpSscc, other.cpSscc)) {
            return false;
        }
        if (!Objects.equals(this.poblacionSscc, other.poblacionSscc)) {
            return false;
        }
        if (!Objects.equals(this.provinciaSscc, other.provinciaSscc)) {
            return false;
        }
        if (!Objects.equals(this.paisSscc, other.paisSscc)) {
            return false;
        }
        if (!Objects.equals(this.departamento, other.departamento)) {
            return false;
        }
        if (!Objects.equals(this.puntoVenta, other.puntoVenta)) {
            return false;
        }
        if (!Objects.equals(this.lugarDestino, other.lugarDestino)) {
            return false;
        }
        if (!Objects.equals(this.empresaSscc, other.empresaSscc)) {
            return false;
        }
        if (!Objects.equals(this.grupoEmbalaje, other.grupoEmbalaje)) {
            return false;
        }
        if (!Objects.equals(this.telefonoEnvio, other.telefonoEnvio)) {
            return false;
        }
        if (!Objects.equals(this.dirEnvio2, other.dirEnvio2)) {
            return false;
        }
        if (!Objects.equals(this.suReferencia, other.suReferencia)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra1, other.datoExtra1)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra2, other.datoExtra2)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra3, other.datoExtra3)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra4, other.datoExtra4)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra5, other.datoExtra5)) {
            return false;
        }
        if (!Objects.equals(this.centro, other.centro)) {
            return false;
        }
        if (!Objects.equals(this.telefonoSscc, other.telefonoSscc)) {
            return false;
        }
        if (!Objects.equals(this.tipoServicioTransporte, other.tipoServicioTransporte)) {
            return false;
        }
        if (!Objects.equals(this.tipoFlujo, other.tipoFlujo)) {
            return false;
        }
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valoresClasificaciones, other.valoresClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.personaContacto, other.personaContacto)) {
            return false;
        }
        if (!Objects.equals(this.emailEnvio, other.emailEnvio)) {
            return false;
        }
        if (!Objects.equals(this.idiomaPacking, other.idiomaPacking)) {
            return false;
        }
        if (!Objects.equals(this.idiomaEtiqueta, other.idiomaEtiqueta)) {
            return false;
        }
        if (!Objects.equals(this.idiomaAlbaran, other.idiomaAlbaran)) {
            return false;
        }
        if (!Objects.equals(this.centroCoste, other.centroCoste)) {
            return false;
        }
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Objects.equals(this.tipoMesa, other.tipoMesa)) {
            return false;
        }
        if (!Objects.equals(this.estadoBloqueo, other.estadoBloqueo)) {
            return false;
        }
        if (!Objects.equals(this.muelleSalida, other.muelleSalida)) {
            return false;
        }
        if (!Objects.equals(this.observacionEmbalaje, other.observacionEmbalaje)) {
            return false;
        }
        if (!Objects.equals(this.numPedEci, other.numPedEci)) {
            return false;
        }
        if (!Objects.equals(this.agenciasPosibles, other.agenciasPosibles)) {
            return false;
        }
        if (!Objects.equals(this.tanda, other.tanda)) {
            return false;
        }
        if (!Objects.equals(this.condicionAgrupa, other.condicionAgrupa)) {
            return false;
        }
        if (!Objects.equals(this.tipoDecisionAgencia, other.tipoDecisionAgencia)) {
            return false;
        }
        if (!Objects.equals(this.condicionPosibleAgrupa, other.condicionPosibleAgrupa)) {
            return false;
        }
        if (!Objects.equals(this.tipoParking, other.tipoParking)) {
            return false;
        }
        if (!Objects.equals(this.parkingPropuesto, other.parkingPropuesto)) {
            return false;
        }
        if (!Objects.equals(this.expedicionAgencia, other.expedicionAgencia)) {
            return false;
        }
        if (!Objects.equals(this.agente, other.agente)) {
            return false;
        }
        if (!Objects.equals(this.zonaVisualizacion, other.zonaVisualizacion)) {
            return false;
        }
        if (!Objects.equals(this.condicionPreAgrupa, other.condicionPreAgrupa)) {
            return false;
        }
        if (!Objects.equals(this.barcodeAlbaran, other.barcodeAlbaran)) {
            return false;
        }
        if (!Objects.equals(this.mensajeFinalizacion, other.mensajeFinalizacion)) {
            return false;
        }
        if (!Objects.equals(this.documentoOrigen, other.documentoOrigen)) {
            return false;
        }
        if (!Objects.equals(this.concertacionCliente, other.concertacionCliente)) {
            return false;
        }
        if (!Objects.equals(this.fechaEntregaCliente, other.fechaEntregaCliente)) {
            return false;
        }
        if (!Objects.equals(this.tipoEmpaquetado, other.tipoEmpaquetado)) {
            return false;
        }
        if (!Objects.equals(this.incoTerm, other.incoTerm)) {
            return false;
        }
        if (!Objects.equals(this.propietario, other.propietario)) {
            return false;
        }
        if (!Objects.equals(this.eanDirEntrega, other.eanDirEntrega)) {
            return false;
        }
        if (!Objects.equals(this.idComprador, other.idComprador)) {
            return false;
        }
        if (!Objects.equals(this.idVendedor, other.idVendedor)) {
            return false;
        }
        if (!Objects.equals(this.idProductRecipient, other.idProductRecipient)) {
            return false;
        }
        if (!Objects.equals(this.nombrePais, other.nombrePais)) {
            return false;
        }
        if (!Objects.equals(this.codigoRegion, other.codigoRegion)) {
            return false;
        }
        if (!Objects.equals(this.numAlbaranes, other.numAlbaranes)) {
            return false;
        }
        if (!Objects.equals(this.importeAgencia, other.importeAgencia)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacion, other.fechaCreacion)) {
            return false;
        }
        if (!Objects.equals(this.prioridadTanda, other.prioridadTanda)) {
            return false;
        }
        if (!Objects.equals(this.bultoIni, other.bultoIni)) {
            return false;
        }
        return Objects.equals(this.posicionMesa, other.posicionMesa);
    }
}
