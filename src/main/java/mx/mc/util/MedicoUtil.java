/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.util;

import java.util.Calendar;
import java.util.Date;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.Usuario;

/**
 *
 * @author mcalderon
 */
public class MedicoUtil {
    
    // Solo se estan validando los datos Necesarios para generar el usuario Médico, los demas valores ya los deben de traer en su objeto usuario.
    public static Usuario crearUsuario(Usuario usuario){
        if(usuario.getIdUsuario() == null || usuario.getIdUsuario().isEmpty()){
            usuario.setIdUsuario(Comunes.getUUID());
        }
        if(usuario.isActivo() == Constantes.INACTIVO){
            usuario.setActivo(Constantes.ACTIVO);
        }
        if(usuario.getCorreoElectronico() == null || usuario.getCorreoElectronico().isEmpty()){
            if (usuario.getClaveAcceso() == null || usuario.getClaveAcceso().isEmpty()) {
                usuario = generarClaveCorreo(usuario);                
            }
        }
        if(usuario.getFechaVigencia() == null){
            usuario.setFechaVigencia(generateDate(new java.util.Date(), Constantes.ANIOS_VIGENCIA_USUARIO));
        }
        if(usuario.getInsertFecha() == null){
            usuario.setInsertFecha(new Date());
        }
        if(usuario.isUsuarioBloqueado() == Constantes.ACTIVO){
            usuario.setActivo(Constantes.INACTIVO);            
        }
        if (usuario.getCedProfesional() == null) {
            usuario.setCedProfesional(Constantes.TXT_VACIO);
        }
        return usuario;
    }
    
    //Se genera una fecha de vigencia, la cual es la fecha actual + 5 años
    private static Date generateDate(Date fecha, int año) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.YEAR, año);
        return calendar.getTime();
    }

        //Se genero la clave de acceso y el correo 
    private static Usuario generarClaveCorreo(Usuario user) {
        String nombreCorreo = user.getNombreUsuario();
        nombreCorreo = nombreCorreo.replaceAll(" ", "_");

        int valor = 0;
        String valCorreo = Constantes.DOMINIO_CORREO;
        valor = (int) (100000 * Math.random());
        String cadena = String.valueOf(valor);
        user.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(nombreCorreo.concat(cadena)));
        user.setCorreoElectronico(nombreCorreo.concat(valCorreo));
        return user;
    }   
}