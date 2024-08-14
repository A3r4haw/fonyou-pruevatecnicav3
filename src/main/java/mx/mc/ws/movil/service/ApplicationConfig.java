package mx.mc.ws.movil.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Punto de entrada de los servicios REST
 *
 * @author Alberto Palacios
 * @version 1.0
 * @since 2018-12-10
 */
@javax.ws.rs.ApplicationPath("ws")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     *
     * Registra las clases que proveen a los métodos REST
     * 
     * @param resources Conjunto de recursos que definen métodos REST
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(mx.gob.issste.ws.ColectivoSiam.class);
        resources.add(mx.mc.ws.farmacovigilancia.service.ReaccionesAdversas.class);
        resources.add(mx.mc.ws.movil.service.DevolucionMedicamentoMBMovil.class);
        resources.add(mx.mc.ws.movil.service.DispensacionMBMovil.class);
        resources.add(mx.mc.ws.movil.service.DispensacionMBMovilOffline.class);
        resources.add(mx.mc.ws.movil.service.ImpresoraMBMovil.class);
        resources.add(mx.mc.ws.movil.service.MinistrarMedicamentoMBMovil.class);
        resources.add(mx.mc.ws.movil.service.MinistrarMedicamentoMBMovilOffline.class);
        resources.add(mx.mc.ws.movil.service.RecepcionDevolucionMBMovil.class);
        resources.add(mx.mc.ws.movil.service.RecepcionMedicamentoMBMovil.class);
        resources.add(mx.mc.ws.movil.service.RecepcionMedicamentoMBMovilOffline.class);
        resources.add(mx.mc.ws.movil.service.Security.class);
        resources.add(mx.mc.ws.movil.service.UpdateAplication.class);
    }
    
}
