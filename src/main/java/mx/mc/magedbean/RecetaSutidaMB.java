/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.init.Constantes;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Usuario;
import mx.mc.service.PrescripcionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Admin
 */
@Controller
@Scope(value = "view")
public class RecetaSutidaMB implements Serializable{
    
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecetaSutidaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    
    private String cadenaBusqueda;
    private Integer sizeRecetaList;
    private Integer sizeDetalleRecetaList;
    private Prescripcion_Extended recetaSelect;
    private SurtimientoInsumo_Extend detalleInsumoSelect;
    private Usuario usuarioSelect;
    private List<Prescripcion_Extended> recetaList;
    private List<SurtimientoInsumo_Extend> detalleRecetaList;
    
        
    @Autowired
    private PrescripcionService prescripcionService;
    
    @Autowired
    private SurtimientoInsumoService surtimientoInsumoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    
    @PostConstruct
    public void init(){    
        LOGGER.trace("mx.mc.magedbean.RecetaSutidaMB.init()");
        initialize();
    }
    
    
    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");        
        
        cadenaBusqueda = null;
        sizeRecetaList= 0;
        recetaList = new ArrayList<>();
        recetaSelect = new Prescripcion_Extended();
        
        consultaRecetas();
    }
    
    
    public void consultaRecetas(){    
        LOGGER.debug("mx.mc.magedbean.RecetaSutidaMB.consultaReceta()");
        try {
            if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            
            recetaList = prescripcionService.obtenerRecetasSurtidas(cadenaBusqueda);
            if(!recetaList.isEmpty())
                sizeRecetaList = recetaList.size();
            
            
            cadenaBusqueda = null;
        } catch (Exception ex) {            
            LOGGER.error("Error al obtener Recetas: {}", ex.getMessage());
        }
    }

    public void verReceta(){
        try {
            if(recetaSelect!=null){
                detalleRecetaList = surtimientoInsumoService.obtenerDetalleReceta(recetaSelect.getIdPrescripcion());
                sizeDetalleRecetaList = detalleRecetaList.size();
                
                
                usuarioSelect = usuarioService.obtenerUsuarioByIdUsuario(recetaSelect.getInsertIdUsuario());
            }
        } catch (Exception e) {
        }
    }
    
    
     //<editor-fold  defaultstate="collapsed" desc="Getter and Setters...">
    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }
    

    public Integer getSizeRecetaList() {
        return sizeRecetaList;
    }

    public void setSizeRecetaList(Integer sizeRecetaList) {
        this.sizeRecetaList = sizeRecetaList;
    }

    public Prescripcion_Extended getRecetaSelect() {
        return recetaSelect;
    }

    public void setRecetaSelect(Prescripcion_Extended recetaSelect) {
        this.recetaSelect = recetaSelect;
    }

    public List<Prescripcion_Extended> getRecetaList() {
        return recetaList;
    }

    public void setRecetaList(List<Prescripcion_Extended> recetaList) {
        this.recetaList = recetaList;
    }
    
    public List<SurtimientoInsumo_Extend> getDetalleRecetaList() {
        return detalleRecetaList;
    }

    public void setDetalleRecetaList(List<SurtimientoInsumo_Extend> detalleRecetaList) {
        this.detalleRecetaList = detalleRecetaList;
    }

    public Integer getSizeDetalleRecetaList() {
        return sizeDetalleRecetaList;
    }

    public void setSizeDetalleRecetaList(Integer sizeDetalleRecetaList) {
        this.sizeDetalleRecetaList = sizeDetalleRecetaList;
    }
    

    public SurtimientoInsumo_Extend getDetalleInsumoSelect() {
        return detalleInsumoSelect;
    }

    public void setDetalleInsumoSelect(SurtimientoInsumo_Extend detalleInsumoSelect) {
        this.detalleInsumoSelect = detalleInsumoSelect;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }
    
    //</editor-fold>
}
