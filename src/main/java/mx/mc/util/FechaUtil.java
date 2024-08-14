package mx.mc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
//import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.apache.commons.validator.routines.DateValidator;
//import java.util.Locale;

/**
 *
 * @author hramirez
 */
public class FechaUtil {

    private static final Pattern REGEXDATE = Pattern.compile("^([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})(\\s)([0-1][0-9]|2[0-3])(:)([0-5][0-9])(:)([0-5][0-9])$");
    private static final String DATE_REGEX =
                    "^(?:(?:(?:0?[13578]|1[02])(\\/|-|\\.)31)\\1|" +
                    "(?:(?:0?[1,3-9]|1[0-2])(\\/|-|\\.)(?:29|30)\\2))" +
                    "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:0?2(\\/|-|\\.)29\\3" +
                    "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|" +
                    "[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|" +
                    "^(?:(?:0?[1-9])|(?:1[0-2]))(\\/|-|\\.)(?:0?[1-9]|1\\d|" +
                    "2[0-8])\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
 
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);
    
    
    public static boolean isFechaValida(Date fechaInicial, Date fechaFinal, Date fecha) {
        if (fecha.after(fechaInicial)) {
            if (fechaFinal == null || fecha.before(fechaFinal)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isFechaValida(String date){
        boolean valid=false;
        Matcher mat = REGEXDATE.matcher(date);//formato: dd/mm/yyyy hh:mm:ss
        if(mat.find())
            valid=true;
        
        return valid;
     }

//    public static Date formatoFecha(String yyyyMMdd, Date fechaCaducidadManual) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
        
    public static boolean isFechaMayorQue(Date f1, Date f2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String f1cadena = sdf.format(f1);
        String f2cadena = sdf.format(f2);

        Date fecha1 = sdf.parse(f1cadena, new ParsePosition(0));
        Date fecha2 = sdf.parse(f2cadena, new ParsePosition(0));

        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(fecha1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(fecha2);
        return cal1.after(cal2);
    }

    public static boolean isFechaMayorIgualQue(Date f1, Date f2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String f1cadena = sdf.format(f1);
        String f2cadena = sdf.format(f2);

        Date fecha1 = sdf.parse(f1cadena, new ParsePosition(0));
        Date fecha2 = sdf.parse(f2cadena, new ParsePosition(0));

        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(fecha1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(fecha2);
        return (cal1.after(cal2) || cal1.equals(cal2));
    }
    
    public static Date formatoFecha(String formato, String cadena) throws Exception {
        try {
            return new SimpleDateFormat(formato).parse(cadena);
        } catch (ParseException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static String formatoCadena(Date fecha, String patron) {
        if (fecha == null) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat();
        formato.applyPattern(patron);
        return formato.format(fecha);
    }

    public static String formatoFecha(Date fecha, String patron) {
        if (fecha == null) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat();
        formato.applyPattern(patron);
        return formato.format(fecha);
    }

    public static Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public static Date sumarRestarHorasFecha(Date fecha, int horas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.HOUR, horas);
        return calendar.getTime();
    }

    public static Date sumarRestarMinutosFecha(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }
    
    public static long diferenciaFechasEnMinutos(Date fecha, Date fecha2){
        long diff = fecha2.getTime() - fecha.getTime();
        long minutos = TimeUnit.MILLISECONDS.toMinutes(diff);
        return minutos;
    }

    public static String fechaFormato(Date fecha) {
        DateFormat df = DateFormat.getDateInstance();
        return df.format(fecha);
    }

    public static boolean fechasIguales(Date f1, Date f2) {
        return fechaFormato(f1).equals(fechaFormato(f2));
    }
    
    public static Integer calculaHorasRestantes() throws Exception {
        Integer res = null;
        try {
            Calendar calA = Calendar.getInstance();
            calA.setTime(new Date());
            res = 24 - calA.get(Calendar.HOUR_OF_DAY);
            
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        
        return res;
    }
    
    public static int horasFaltantesParaCorte(String horaCorteSurtimiento, Date fechaInicioProgramada){
        long res = 0;
        try {
            // Define hora De corte por Parametro
            LocalTime horaCorte = LocalTime.parse(horaCorteSurtimiento);

            // Define Hora Inicio de Tratamiento
            Instant instant = Instant.ofEpochMilli(fechaInicioProgramada.getTime());
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
            LocalTime horaInicioTratamietno = ldt.toLocalTime();
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(new java.util.Date());
            cal.set(Calendar.HOUR_OF_DAY, horaCorte.getHour());
            cal.set(Calendar.MINUTE, horaCorte.getMinute());
            cal.set(Calendar.SECOND, horaCorte.getSecond());

            // Si la fecha de corte es anterior a Fecha Inicio se pasa al siguiente día
            if (horaCorte.isBefore(horaInicioTratamietno)) {
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.HOUR_OF_DAY, horaInicioTratamietno.getHour());
            cal2.set(Calendar.MINUTE, horaInicioTratamietno.getMinute());
            cal2.set(Calendar.SECOND, horaInicioTratamietno.getSecond());

            res = (cal.getTimeInMillis() - cal2.getTimeInMillis()) / (1000 * 60 * 60);
            
        } catch (Exception ex) {
            Logger.getLogger(FechaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) res;
    }
    
    public static Date getFechaActual (){
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        return now.getTime();
    }     
    
    public static Date obtenerFechaInicio(){
        Calendar cal = Calendar.getInstance();        
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);        
        return cal.getTime();
    }    
    
    public static Date obtenerFechaFin(){
        Calendar cal = Calendar.getInstance();        
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 59);
        return cal.getTime();
    }
    
    public static Date obtenerFechaFinal(Date fecha) {
        Calendar cal = Calendar.getInstance();       
        cal.setTime(fecha);
        cal.set(com.ibm.icu.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(com.ibm.icu.util.Calendar.MINUTE, 59);
        return cal.getTime();        
    }  
    
    
    public static boolean isFormatoFechaValida(Date fecha) {
        String cadena = formatoFecha(fecha, "dd/MM/yyyy" );
        Matcher matcher = REGEXDATE.matcher(cadena);
        return matcher.matches();
    }
    
    
//    public static boolean isFechaValida(){
//        
//        if (dateValidator(date, "mm/dd/yyyy")) {
//                System.out.println(date + " is valid");
//            }
//            else {
//                System.out.println(date + " isn't valid");
//            }
//    }
    
    public static Integer obtenerNoDiasEntreFechas(Date fecha1, Date fecha2) {
        long diffDays = 0;
        try {
            String f1 = formatoCadena(fecha1, "yyyy-MM-dd");
            String f2 = formatoCadena(fecha2, "yyyy-MM-dd");
            LocalDate d1 = LocalDate.parse(f1, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2 = LocalDate.parse(f2, DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
            diffDays = diff.toDays();
//            LocalDate d3 = LocalDate.parse("2018-05-06", DateTimeFormatter.ISO_LOCAL_DATE);
//            LocalDate d4 = LocalDate.parse("2020-01-23", DateTimeFormatter.ISO_LOCAL_DATE);
//            Period period = Period.between(d1, d2);
//            int years = Math.abs(period.getYears());
//            int months = Math.abs(period.getMonths());
//            int days = Math.abs(period.getDays());
        } catch (Exception e) {
            System.err.println("Error al calcular días " + e.getMessage());
        }
        return (int) diffDays;
    }
    

    public static LocalDate obtenerUltimoDiaMes(Date fecha) {
        try {
            TemporalAdjuster temporalAdjuster = TemporalAdjusters.lastDayOfMonth();
            LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate lastDayOfMonth = localDate.with(temporalAdjuster);
            return lastDayOfMonth;
        } catch (Exception e){
            System.err.println("Error :: " + e.getMessage());
            
        }
        return null;
    }
    
}
