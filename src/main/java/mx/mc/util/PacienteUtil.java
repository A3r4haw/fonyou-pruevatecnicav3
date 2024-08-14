/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Paciente;


/**
 *
 * @author mcalderon
 */
public class PacienteUtil {
    
    public static Paciente crearPaciente(Paciente patient) {        
        if(patient.getIdPaciente() == null || patient.getIdPaciente().trim().isEmpty()){
            patient.setIdPaciente(Comunes.getUUID());
        }
        if(patient.getNombreCompleto() == null){
            patient.setNombreCompleto(Constantes.TXT_VACIO);
        }       
        if(patient.getApellidoPaterno() == null){
            patient.setApellidoPaterno(Constantes.TXT_VACIO);
        }
        if(patient.getApellidoMaterno() == null){
            patient.setApellidoMaterno(Constantes.TXT_VACIO);
        }
        if(patient.getIdEstructura() != null || !patient.getIdEstructura().trim().isEmpty()){
            patient.setIdEstructura(patient.getIdEstructura());  // VERIFICAR
        }        
        if(patient.getClaveDerechohabiencia() != null){
            patient.setClaveDerechohabiencia(patient.getClaveDerechohabiencia());                
        }        
        //patient.setPacienteNumero(patient.getClaveDerechohabiencia() + generarPacNumero() );   // Este dato se agrega hasta Implement  con patientManual autoIncrem
        if(patient.getFechaNacimiento() == null){
            patient.setFechaNacimiento(new Date());
        }
        if(patient.getCurp() ==  null){
            patient.setCurp(Constantes.TXT_VACIO);
        }
        if(patient.getIdEscolaridad() == null){
            patient.setIdEscolaridad(CatalogoGeneral_Enum.ESCOLARIDAD_NO_DEFINIDA.getValue());
        }                
        if(patient.getIdEstadoCivil() == null){
            patient.setIdEstadoCivil(CatalogoGeneral_Enum.ESTADO_CIVIL_NO_DEFINIDO.getValue());
        }
        if(patient.getIdEstatusPaciente() == null){
            patient.setIdEstatusPaciente(EstatusPaciente_Enum.REGISTRADO.getValue());
        }
        if(patient.getIdGrupoEtnico() == null){
            patient.setIdGrupoEtnico(CatalogoGeneral_Enum.GRUPO_ETNICO_NO_DEFINIDO.getValue());
        }        
        if(patient.getIdGrupoSanguineo() == null){
            patient.setIdGrupoSanguineo(CatalogoGeneral_Enum.GRUPO_SANGUINEO_NO_DEFINIDO.getValue());
        }
        if(patient.getIdNivelSocioEconomico() == null){
            patient.setIdNivelSocioEconomico(CatalogoGeneral_Enum.NIVEL_SOCIOECONOMICO_NO_DEFINIDO.getValue());
        }
        if(patient.getIdOcupacion() == null){
            patient.setIdOcupacion(CatalogoGeneral_Enum.OCUPACION_NO_DEFINIDA.getValue());        
        }
        if(patient.getIdReligion() == null){
            patient.setIdReligion(CatalogoGeneral_Enum.RELIGION_NO_DEFINIDA.getValue());
        }
        if(patient.getIdTipoPaciente() == null){
            patient.setIdTipoPaciente(CatalogoGeneral_Enum.TIPO_PACIENTE_NO_DEFINIDO.getValue());
        }
        if(patient.getIdTipoVivienda() == null){
            patient.setIdTipoVivienda(CatalogoGeneral_Enum.TIPO_VIVIENDA_NO_DEFINIDA.getValue());
        }
        if(patient.getIdUnidadMedica() == null){
            patient.setIdUnidadMedica(CatalogoGeneral_Enum.UNIDAD_MEDICA_OTRA.getValue());
        }
        if(patient.getInsertFecha() == null){
            patient.setInsertFecha(new Date());
        }        
        patient.setInsertIdUsuario(patient.getInsertIdUsuario());               
        if(patient.getSexo() != 'F' && patient.getSexo() != 'M'){
            patient.setSexo('U');
        }
        if(patient.getRfc() == null){
            patient.setRfc(Constantes.TXT_VACIO);
        }
        if(patient.getIdEstructuraPeriferico() != null){
            patient.setIdEstructuraPeriferico(patient.getIdEstructuraPeriferico());
        }
        if(patient.getEstatusGabinete() != null){
            patient.setEstatusGabinete(patient.getEstatusGabinete());
        }
        return patient;
    }
    
    public static Integer calcularEdad(Date nacimiento) {
        Period periodo;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate fechaNac = LocalDate.parse(dateFormat.format(nacimiento), fmt);
            LocalDate ahora = LocalDate.now();
            periodo = Period.between(fechaNac, ahora);
        } catch (Exception ex) {
            return null;
        }
        return periodo.getYears();
    }
}
