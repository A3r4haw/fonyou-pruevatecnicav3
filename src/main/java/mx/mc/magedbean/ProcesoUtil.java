//package mx.mc.magedbean;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//import mx.mc.enums.TipoUsuario_Enum;
//import mx.mc.init.Constantes;
//import mx.mc.model.Usuario;
//import mx.mc.service.UsuarioService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// *
// * @author Cervanets
// */
//public class ProcesoUtil implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    private static final Logger LOGGER = LoggerFactory.getLogger(ProcesoUtil.class);
//    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
//    
//    @Autowired
//    private transient UsuarioService usuarioService;
//    
//    public List<Usuario> obtenerMedicosPorAutocomplete(String cadena) {
//        LOGGER.trace("mx.mc.magedbean.ProcesoUtil.obtenerMedicosPorAutocomplete()");
//        List<Usuario> listMedicos = new ArrayList<>();
//        try {
//            listMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);
//        } catch (Exception ex) {
//            LOGGER.error("Error en obtenerMedicosPorAutocomplete :{}", ex.getMessage());
//        }
//        return listMedicos;
//    }
//    
//    
//}
