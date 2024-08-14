package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class NutricionParenteral implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idNutricionParenteral;
    private String folio;
    private String folioPreparacion;

    private String idPaciente;
    private Integer idEstatusSolucion;
    private Date fechaPrepara;
    private String idRegistro;
    private Date fechaProgramada;
    private String idMedico;
    private String idVisita;
    private BigDecimal volumenTotal;
    private BigDecimal pesoTotal;
    private BigDecimal caloriasTotal;
    private BigDecimal calcPesoVolumen;
    private BigDecimal porcenEnergiaTotal;
    private BigDecimal monovalentes;
    private BigDecimal divalentes;
    private BigDecimal calcMonovalente;
    private BigDecimal calcDivalente;
    private BigDecimal precipitacion;
    private String resultPrecipitacion;
    private BigDecimal estabilidad;
    private String resultEstabilidad;
    private BigDecimal totalOsmolaridad;
    private String resulOsmolaridad;
    private String infusion;
    private BigDecimal velocInfusion;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private String idEstructura;

    private String instruccionPreparacion;
    private boolean noAgitar;
    private Integer unidadConcentracion;
    private String observaciones;

    private String idSurtimiento;
    private Integer edad;
    private BigDecimal peso;
    private String diagnosticos;
    private Date fechaNacimiento;
    private BigDecimal tallaPaciente;
    private BigDecimal areaCorporal;
    private String tipoConsulta;
    private boolean diabetes;
    private boolean hipertension;
    private boolean problemasRenales;
    private Integer idContenedor;
    private Date fechaParaEntregar;
    private Integer idHorarioParaEntregar;
    private boolean perfusionContinua;
    private BigDecimal velocidad;
    private BigDecimal ritmo;
    private String tipoPrescripcion;
    private boolean padecimientoCronico;
    private String idCama;
    private String idTipoSolucion;
    private Integer idViaAdministracion;
    private String viaAdministracion;

    private Integer sobrellenado;
    private Integer horasInfusion;

    private BigDecimal kcalProteicas;
    private BigDecimal kcalNoProteicas;

    public NutricionParenteral() {
    }

    public NutricionParenteral(String idNutricionParenteral) {
        this.idNutricionParenteral = idNutricionParenteral;
    }

    public NutricionParenteral(String idNutricionParenteral, String folio, String folioPreparacion, String idPaciente, Integer idEstatusSolucion, Date fechaPrepara, String idRegistro, Date fechaProgramada, String idMedico, String idVisita, BigDecimal volumenTotal, BigDecimal pesoTotal, BigDecimal caloriasTotal, BigDecimal calcPesoVolumen, BigDecimal porcenEnergiaTotal, BigDecimal monovalentes, BigDecimal divalentes, BigDecimal calcMonovalente, BigDecimal calcDivalente, BigDecimal precipitacion, String resultPrecipitacion, BigDecimal estabilidad, String resultEstabilidad, BigDecimal totalOsmolaridad, String resulOsmolaridad, String infusion, BigDecimal velocInfusion, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario, String idEstructura, String instruccionPreparacion, boolean noAgitar, Integer unidadConcentracion, String observaciones, String idSurtimiento, Integer edad, BigDecimal peso, String diagnosticos, Date fechaNacimiento, BigDecimal tallaPaciente, BigDecimal areaCorporal, String tipoConsulta, boolean diabetes, boolean hipertension, boolean problemasRenales, Integer idContenedor, Date fechaParaEntregar, Integer idHorarioParaEntregar, boolean perfusionContinua, BigDecimal velocidad, BigDecimal ritmo, String tipoPrescripcion, boolean padecimientoCronico, String idCama, String idTipoSolucion, Integer idViaAdministracion, String viaAdministracion, Integer sobrellenado, Integer horasInfusion, BigDecimal kcalProteicas, BigDecimal kcalNoProteicas) {
        this.idNutricionParenteral = idNutricionParenteral;
        this.folio = folio;
        this.folioPreparacion = folioPreparacion;
        this.idPaciente = idPaciente;
        this.idEstatusSolucion = idEstatusSolucion;
        this.fechaPrepara = fechaPrepara;
        this.idRegistro = idRegistro;
        this.fechaProgramada = fechaProgramada;
        this.idMedico = idMedico;
        this.idVisita = idVisita;
        this.volumenTotal = volumenTotal;
        this.pesoTotal = pesoTotal;
        this.caloriasTotal = caloriasTotal;
        this.calcPesoVolumen = calcPesoVolumen;
        this.porcenEnergiaTotal = porcenEnergiaTotal;
        this.monovalentes = monovalentes;
        this.divalentes = divalentes;
        this.calcMonovalente = calcMonovalente;
        this.calcDivalente = calcDivalente;
        this.precipitacion = precipitacion;
        this.resultPrecipitacion = resultPrecipitacion;
        this.estabilidad = estabilidad;
        this.resultEstabilidad = resultEstabilidad;
        this.totalOsmolaridad = totalOsmolaridad;
        this.resulOsmolaridad = resulOsmolaridad;
        this.infusion = infusion;
        this.velocInfusion = velocInfusion;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.idEstructura = idEstructura;
        this.instruccionPreparacion = instruccionPreparacion;
        this.noAgitar = noAgitar;
        this.unidadConcentracion = unidadConcentracion;
        this.observaciones = observaciones;
        this.idSurtimiento = idSurtimiento;
        this.edad = edad;
        this.peso = peso;
        this.diagnosticos = diagnosticos;
        this.fechaNacimiento = fechaNacimiento;
        this.tallaPaciente = tallaPaciente;
        this.areaCorporal = areaCorporal;
        this.tipoConsulta = tipoConsulta;
        this.diabetes = diabetes;
        this.hipertension = hipertension;
        this.problemasRenales = problemasRenales;
        this.idContenedor = idContenedor;
        this.fechaParaEntregar = fechaParaEntregar;
        this.idHorarioParaEntregar = idHorarioParaEntregar;
        this.perfusionContinua = perfusionContinua;
        this.velocidad = velocidad;
        this.ritmo = ritmo;
        this.tipoPrescripcion = tipoPrescripcion;
        this.padecimientoCronico = padecimientoCronico;
        this.idCama = idCama;
        this.idTipoSolucion = idTipoSolucion;
        this.idViaAdministracion = idViaAdministracion;
        this.viaAdministracion = viaAdministracion;
        this.sobrellenado = sobrellenado;
        this.horasInfusion = horasInfusion;
        this.kcalProteicas = kcalProteicas;
        this.kcalNoProteicas = kcalNoProteicas;
    }

    @Override
    public String toString() {
        return "NutricionParenteral{" + "idNutricionParenteral=" + idNutricionParenteral + ", folio=" + folio + ", folioPreparacion=" + folioPreparacion + ", idPaciente=" + idPaciente + ", idEstatusSolucion=" + idEstatusSolucion + ", fechaPrepara=" + fechaPrepara + ", idRegistro=" + idRegistro + ", fechaProgramada=" + fechaProgramada + ", idMedico=" + idMedico + ", idVisita=" + idVisita + ", volumenTotal=" + volumenTotal + ", pesoTotal=" + pesoTotal + ", caloriasTotal=" + caloriasTotal + ", calcPesoVolumen=" + calcPesoVolumen + ", porcenEnergiaTotal=" + porcenEnergiaTotal + ", monovalentes=" + monovalentes + ", divalentes=" + divalentes + ", calcMonovalente=" + calcMonovalente + ", calcDivalente=" + calcDivalente + ", precipitacion=" + precipitacion + ", resultPrecipitacion=" + resultPrecipitacion + ", estabilidad=" + estabilidad + ", resultEstabilidad=" + resultEstabilidad + ", totalOsmolaridad=" + totalOsmolaridad + ", resulOsmolaridad=" + resulOsmolaridad + ", infusion=" + infusion + ", velocInfusion=" + velocInfusion + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idEstructura=" + idEstructura + ", instruccionPreparacion=" + instruccionPreparacion + ", noAgitar=" + noAgitar + ", unidadConcentracion=" + unidadConcentracion + ", observaciones=" + observaciones + ", idSurtimiento=" + idSurtimiento + ", edad=" + edad + ", peso=" + peso + ", diagnosticos=" + diagnosticos + ", fechaNacimiento=" + fechaNacimiento + ", tallaPaciente=" + tallaPaciente + ", areaCorporal=" + areaCorporal + ", tipoConsulta=" + tipoConsulta + ", diabetes=" + diabetes + ", hipertension=" + hipertension + ", problemasRenales=" + problemasRenales + ", idContenedor=" + idContenedor + ", fechaParaEntregar=" + fechaParaEntregar + ", idHorarioParaEntregar=" + idHorarioParaEntregar + ", perfusionContinua=" + perfusionContinua + ", velocidad=" + velocidad + ", ritmo=" + ritmo + ", tipoPrescripcion=" + tipoPrescripcion + ", padecimientoCronico=" + padecimientoCronico + ", idCama=" + idCama + ", idTipoSolucion=" + idTipoSolucion + ", idViaAdministracion=" + idViaAdministracion + ", viaAdministracion=" + viaAdministracion + ", sobrellenado=" + sobrellenado + ", horasInfusion=" + horasInfusion + ", kcalProteicas=" + kcalProteicas + ", kcalNoProteicas=" + kcalNoProteicas + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.idNutricionParenteral);
        hash = 83 * hash + Objects.hashCode(this.folio);
        hash = 83 * hash + Objects.hashCode(this.folioPreparacion);
        hash = 83 * hash + Objects.hashCode(this.idPaciente);
        hash = 83 * hash + Objects.hashCode(this.idEstatusSolucion);
        hash = 83 * hash + Objects.hashCode(this.fechaPrepara);
        hash = 83 * hash + Objects.hashCode(this.idRegistro);
        hash = 83 * hash + Objects.hashCode(this.fechaProgramada);
        hash = 83 * hash + Objects.hashCode(this.idMedico);
        hash = 83 * hash + Objects.hashCode(this.idVisita);
        hash = 83 * hash + Objects.hashCode(this.volumenTotal);
        hash = 83 * hash + Objects.hashCode(this.pesoTotal);
        hash = 83 * hash + Objects.hashCode(this.caloriasTotal);
        hash = 83 * hash + Objects.hashCode(this.calcPesoVolumen);
        hash = 83 * hash + Objects.hashCode(this.porcenEnergiaTotal);
        hash = 83 * hash + Objects.hashCode(this.monovalentes);
        hash = 83 * hash + Objects.hashCode(this.divalentes);
        hash = 83 * hash + Objects.hashCode(this.calcMonovalente);
        hash = 83 * hash + Objects.hashCode(this.calcDivalente);
        hash = 83 * hash + Objects.hashCode(this.precipitacion);
        hash = 83 * hash + Objects.hashCode(this.resultPrecipitacion);
        hash = 83 * hash + Objects.hashCode(this.estabilidad);
        hash = 83 * hash + Objects.hashCode(this.resultEstabilidad);
        hash = 83 * hash + Objects.hashCode(this.totalOsmolaridad);
        hash = 83 * hash + Objects.hashCode(this.resulOsmolaridad);
        hash = 83 * hash + Objects.hashCode(this.infusion);
        hash = 83 * hash + Objects.hashCode(this.velocInfusion);
        hash = 83 * hash + Objects.hashCode(this.insertFecha);
        hash = 83 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 83 * hash + Objects.hashCode(this.updateFecha);
        hash = 83 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 83 * hash + Objects.hashCode(this.idEstructura);
        hash = 83 * hash + Objects.hashCode(this.instruccionPreparacion);
        hash = 83 * hash + (this.noAgitar ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.unidadConcentracion);
        hash = 83 * hash + Objects.hashCode(this.observaciones);
        hash = 83 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 83 * hash + Objects.hashCode(this.edad);
        hash = 83 * hash + Objects.hashCode(this.peso);
        hash = 83 * hash + Objects.hashCode(this.diagnosticos);
        hash = 83 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 83 * hash + Objects.hashCode(this.tallaPaciente);
        hash = 83 * hash + Objects.hashCode(this.areaCorporal);
        hash = 83 * hash + Objects.hashCode(this.tipoConsulta);
        hash = 83 * hash + (this.diabetes ? 1 : 0);
        hash = 83 * hash + (this.hipertension ? 1 : 0);
        hash = 83 * hash + (this.problemasRenales ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.idContenedor);
        hash = 83 * hash + Objects.hashCode(this.fechaParaEntregar);
        hash = 83 * hash + Objects.hashCode(this.idHorarioParaEntregar);
        hash = 83 * hash + (this.perfusionContinua ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.velocidad);
        hash = 83 * hash + Objects.hashCode(this.ritmo);
        hash = 83 * hash + Objects.hashCode(this.tipoPrescripcion);
        hash = 83 * hash + (this.padecimientoCronico ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.idCama);
        hash = 83 * hash + Objects.hashCode(this.idTipoSolucion);
        hash = 83 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 83 * hash + Objects.hashCode(this.viaAdministracion);
        hash = 83 * hash + Objects.hashCode(this.sobrellenado);
        hash = 83 * hash + Objects.hashCode(this.horasInfusion);
        hash = 83 * hash + Objects.hashCode(this.kcalProteicas);
        hash = 83 * hash + Objects.hashCode(this.kcalNoProteicas);
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
        final NutricionParenteral other = (NutricionParenteral) obj;
        if (this.noAgitar != other.noAgitar) {
            return false;
        }
        if (this.diabetes != other.diabetes) {
            return false;
        }
        if (this.hipertension != other.hipertension) {
            return false;
        }
        if (this.problemasRenales != other.problemasRenales) {
            return false;
        }
        if (this.perfusionContinua != other.perfusionContinua) {
            return false;
        }
        if (this.padecimientoCronico != other.padecimientoCronico) {
            return false;
        }
        if (!Objects.equals(this.idNutricionParenteral, other.idNutricionParenteral)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.folioPreparacion, other.folioPreparacion)) {
            return false;
        }
        if (!Objects.equals(this.idPaciente, other.idPaciente)) {
            return false;
        }
        if (!Objects.equals(this.idRegistro, other.idRegistro)) {
            return false;
        }
        if (!Objects.equals(this.idMedico, other.idMedico)) {
            return false;
        }
        if (!Objects.equals(this.idVisita, other.idVisita)) {
            return false;
        }
        if (!Objects.equals(this.resultPrecipitacion, other.resultPrecipitacion)) {
            return false;
        }
        if (!Objects.equals(this.resultEstabilidad, other.resultEstabilidad)) {
            return false;
        }
        if (!Objects.equals(this.resulOsmolaridad, other.resulOsmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.infusion, other.infusion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.instruccionPreparacion, other.instruccionPreparacion)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.diagnosticos, other.diagnosticos)) {
            return false;
        }
        if (!Objects.equals(this.tipoConsulta, other.tipoConsulta)) {
            return false;
        }
        if (!Objects.equals(this.tipoPrescripcion, other.tipoPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idCama, other.idCama)) {
            return false;
        }
        if (!Objects.equals(this.idTipoSolucion, other.idTipoSolucion)) {
            return false;
        }
        if (!Objects.equals(this.viaAdministracion, other.viaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSolucion, other.idEstatusSolucion)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrepara, other.fechaPrepara)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramada, other.fechaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.volumenTotal, other.volumenTotal)) {
            return false;
        }
        if (!Objects.equals(this.pesoTotal, other.pesoTotal)) {
            return false;
        }
        if (!Objects.equals(this.caloriasTotal, other.caloriasTotal)) {
            return false;
        }
        if (!Objects.equals(this.calcPesoVolumen, other.calcPesoVolumen)) {
            return false;
        }
        if (!Objects.equals(this.porcenEnergiaTotal, other.porcenEnergiaTotal)) {
            return false;
        }
        if (!Objects.equals(this.monovalentes, other.monovalentes)) {
            return false;
        }
        if (!Objects.equals(this.divalentes, other.divalentes)) {
            return false;
        }
        if (!Objects.equals(this.calcMonovalente, other.calcMonovalente)) {
            return false;
        }
        if (!Objects.equals(this.calcDivalente, other.calcDivalente)) {
            return false;
        }
        if (!Objects.equals(this.precipitacion, other.precipitacion)) {
            return false;
        }
        if (!Objects.equals(this.estabilidad, other.estabilidad)) {
            return false;
        }
        if (!Objects.equals(this.totalOsmolaridad, other.totalOsmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.velocInfusion, other.velocInfusion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.unidadConcentracion, other.unidadConcentracion)) {
            return false;
        }
        if (!Objects.equals(this.edad, other.edad)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.tallaPaciente, other.tallaPaciente)) {
            return false;
        }
        if (!Objects.equals(this.areaCorporal, other.areaCorporal)) {
            return false;
        }
        if (!Objects.equals(this.idContenedor, other.idContenedor)) {
            return false;
        }
        if (!Objects.equals(this.fechaParaEntregar, other.fechaParaEntregar)) {
            return false;
        }
        if (!Objects.equals(this.idHorarioParaEntregar, other.idHorarioParaEntregar)) {
            return false;
        }
        if (!Objects.equals(this.velocidad, other.velocidad)) {
            return false;
        }
        if (!Objects.equals(this.ritmo, other.ritmo)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.sobrellenado, other.sobrellenado)) {
            return false;
        }
        if (!Objects.equals(this.horasInfusion, other.horasInfusion)) {
            return false;
        }
        if (!Objects.equals(this.kcalProteicas, other.kcalProteicas)) {
            return false;
        }
        return Objects.equals(this.kcalNoProteicas, other.kcalNoProteicas);
    }

    public String getIdNutricionParenteral() {
        return idNutricionParenteral;
    }

    public void setIdNutricionParenteral(String idNutricionParenteral) {
        this.idNutricionParenteral = idNutricionParenteral;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolioPreparacion() {
        return folioPreparacion;
    }

    public void setFolioPreparacion(String folioPreparacion) {
        this.folioPreparacion = folioPreparacion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdEstatusSolucion() {
        return idEstatusSolucion;
    }

    public void setIdEstatusSolucion(Integer idEstatusSolucion) {
        this.idEstatusSolucion = idEstatusSolucion;
    }

    public Date getFechaPrepara() {
        return fechaPrepara;
    }

    public void setFechaPrepara(Date fechaPrepara) {
        this.fechaPrepara = fechaPrepara;
    }

    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public BigDecimal getVolumenTotal() {
        return volumenTotal;
    }

    public void setVolumenTotal(BigDecimal volumenTotal) {
        this.volumenTotal = volumenTotal;
    }

    public BigDecimal getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(BigDecimal pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public BigDecimal getCaloriasTotal() {
        return caloriasTotal;
    }

    public void setCaloriasTotal(BigDecimal caloriasTotal) {
        this.caloriasTotal = caloriasTotal;
    }

    public BigDecimal getCalcPesoVolumen() {
        return calcPesoVolumen;
    }

    public void setCalcPesoVolumen(BigDecimal calcPesoVolumen) {
        this.calcPesoVolumen = calcPesoVolumen;
    }

    public BigDecimal getPorcenEnergiaTotal() {
        return porcenEnergiaTotal;
    }

    public void setPorcenEnergiaTotal(BigDecimal porcenEnergiaTotal) {
        this.porcenEnergiaTotal = porcenEnergiaTotal;
    }

    public BigDecimal getMonovalentes() {
        return monovalentes;
    }

    public void setMonovalentes(BigDecimal monovalentes) {
        this.monovalentes = monovalentes;
    }

    public BigDecimal getDivalentes() {
        return divalentes;
    }

    public void setDivalentes(BigDecimal divalentes) {
        this.divalentes = divalentes;
    }

    public BigDecimal getCalcMonovalente() {
        return calcMonovalente;
    }

    public void setCalcMonovalente(BigDecimal calcMonovalente) {
        this.calcMonovalente = calcMonovalente;
    }

    public BigDecimal getCalcDivalente() {
        return calcDivalente;
    }

    public void setCalcDivalente(BigDecimal calcDivalente) {
        this.calcDivalente = calcDivalente;
    }

    public BigDecimal getPrecipitacion() {
        return precipitacion;
    }

    public void setPrecipitacion(BigDecimal precipitacion) {
        this.precipitacion = precipitacion;
    }

    public String getResultPrecipitacion() {
        return resultPrecipitacion;
    }

    public void setResultPrecipitacion(String resultPrecipitacion) {
        this.resultPrecipitacion = resultPrecipitacion;
    }

    public BigDecimal getEstabilidad() {
        return estabilidad;
    }

    public void setEstabilidad(BigDecimal estabilidad) {
        this.estabilidad = estabilidad;
    }

    public String getResultEstabilidad() {
        return resultEstabilidad;
    }

    public void setResultEstabilidad(String resultEstabilidad) {
        this.resultEstabilidad = resultEstabilidad;
    }

    public BigDecimal getTotalOsmolaridad() {
        return totalOsmolaridad;
    }

    public void setTotalOsmolaridad(BigDecimal totalOsmolaridad) {
        this.totalOsmolaridad = totalOsmolaridad;
    }

    public String getResulOsmolaridad() {
        return resulOsmolaridad;
    }

    public void setResulOsmolaridad(String resulOsmolaridad) {
        this.resulOsmolaridad = resulOsmolaridad;
    }

    public String getInfusion() {
        return infusion;
    }

    public void setInfusion(String infusion) {
        this.infusion = infusion;
    }

    public BigDecimal getVelocInfusion() {
        return velocInfusion;
    }

    public void setVelocInfusion(BigDecimal velocInfusion) {
        this.velocInfusion = velocInfusion;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getInstruccionPreparacion() {
        return instruccionPreparacion;
    }

    public void setInstruccionPreparacion(String instruccionPreparacion) {
        this.instruccionPreparacion = instruccionPreparacion;
    }

    public boolean isNoAgitar() {
        return noAgitar;
    }

    public void setNoAgitar(boolean noAgitar) {
        this.noAgitar = noAgitar;
    }

    public Integer getUnidadConcentracion() {
        return unidadConcentracion;
    }

    public void setUnidadConcentracion(Integer unidadConcentracion) {
        this.unidadConcentracion = unidadConcentracion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(String diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public BigDecimal getTallaPaciente() {
        return tallaPaciente;
    }

    public void setTallaPaciente(BigDecimal tallaPaciente) {
        this.tallaPaciente = tallaPaciente;
    }

    public BigDecimal getAreaCorporal() {
        return areaCorporal;
    }

    public void setAreaCorporal(BigDecimal areaCorporal) {
        this.areaCorporal = areaCorporal;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean isHipertension() {
        return hipertension;
    }

    public void setHipertension(boolean hipertension) {
        this.hipertension = hipertension;
    }

    public boolean isProblemasRenales() {
        return problemasRenales;
    }

    public void setProblemasRenales(boolean problemasRenales) {
        this.problemasRenales = problemasRenales;
    }

    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }

    public Date getFechaParaEntregar() {
        return fechaParaEntregar;
    }

    public void setFechaParaEntregar(Date fechaParaEntregar) {
        this.fechaParaEntregar = fechaParaEntregar;
    }

    public Integer getIdHorarioParaEntregar() {
        return idHorarioParaEntregar;
    }

    public void setIdHorarioParaEntregar(Integer idHorarioParaEntregar) {
        this.idHorarioParaEntregar = idHorarioParaEntregar;
    }

    public boolean isPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(boolean perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }

    public BigDecimal getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(BigDecimal velocidad) {
        this.velocidad = velocidad;
    }

    public BigDecimal getRitmo() {
        return ritmo;
    }

    public void setRitmo(BigDecimal ritmo) {
        this.ritmo = ritmo;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public boolean isPadecimientoCronico() {
        return padecimientoCronico;
    }

    public void setPadecimientoCronico(boolean padecimientoCronico) {
        this.padecimientoCronico = padecimientoCronico;
    }

    public String getIdCama() {
        return idCama;
    }

    public void setIdCama(String idCama) {
        this.idCama = idCama;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public Integer getSobrellenado() {
        return sobrellenado;
    }

    public void setSobrellenado(Integer sobrellenado) {
        this.sobrellenado = sobrellenado;
    }

    public Integer getHorasInfusion() {
        return horasInfusion;
    }

    public void setHorasInfusion(Integer horasInfusion) {
        this.horasInfusion = horasInfusion;
    }

    public BigDecimal getKcalProteicas() {
        return kcalProteicas;
    }

    public void setKcalProteicas(BigDecimal kcalProteicas) {
        this.kcalProteicas = kcalProteicas;
    }

    public BigDecimal getKcalNoProteicas() {
        return kcalNoProteicas;
    }

    public void setKcalNoProteicas(BigDecimal kcalNoProteicas) {
        this.kcalNoProteicas = kcalNoProteicas;
    }

}
