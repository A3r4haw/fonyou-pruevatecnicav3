package mx.mc.service;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.TurnoMedico;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.model.UsuarioRol;
import mx.mc.model.VistaUsuario;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface UsuarioService extends GenericCrudService<Usuario, String> {

    Usuario obtenerNombreUsuarioCorreoElectronico(String username) throws Exception;

    Usuario getUserByUserName(String nombreUsuario) throws Exception;

    Usuario getUserByEmail(String correoElectronico) throws Exception;

    Usuario obtenerUsuarioPorId(String idUsuario) throws Exception;

    @Deprecated
    List<VistaUsuario> obtenerVista(String cadena) throws Exception;

    List<VistaUsuario> obtenerUsuariosOrdenadoPorCadena(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage,
             String sortField, SortOrder sortOrder) throws Exception;

    public Long obtenerTotalUsuariosOrdenadoPorCadena(ParamBusquedaReporte paramBusquedaReporte) throws Exception;

    Usuario obtenerIdEstructuraPadre(String idUsuario) throws Exception;

    Usuario verificaSiExisteUser(Usuario user) throws Exception;

    List<Usuario> obtenerUsuarios(String cadenaBusqueda) throws Exception;

    public List<Usuario> obtenerMedicosPorCriteriosBusqueda(
            String cadenaBusqueda, Integer idTipoUsuario, Integer numRegistros, Integer puedePrescribir) throws Exception;

    List<Usuario_Extended> obtenerDoctoresPorTurnoYConsultorio(
            Integer idTurno, String idEstructura) throws Exception;

    Boolean insertarUser(Usuario user) throws Exception;

    Boolean insertarLayout(Usuario layout, UsuarioRol rolLayout) throws Exception;

    public Boolean insertarMedicoYTurno(Usuario user, List<TurnoMedico> listaTurnos) throws Exception;

    List<Usuario> obtenerListaMedicosActivos() throws Exception;

    Usuario_Extended obtenerCuentaUsuarioPorId(String idUsuario) throws Exception;

    public boolean actualizarCuentaUsuario(Usuario usuario) throws Exception;

    List<Usuario> obtenerListaUsuarios(String idEstructura) throws Exception;

    Usuario obtenerUsuarioByIdUsuario(String idUsuario) throws Exception;

    public List<Usuario> obtenerMedicosPorCriteriosBusquedaYEstructura(String cadenaBusqueda, Integer idTipoUsuario, String idEstructura,
            Integer numRegistros, Boolean puedePrescribir) throws Exception;
    
    Usuario obtenerUsuarioByMatriculaPersonal(String matriculaPersonal) throws Exception;
    
    List<Usuario> getAllUsersByIdRol(String idRol) throws Exception;
    public List<Usuario> obtenerAutoCompUsario(String nombre) throws Exception;
    
    List<Usuario> obtenerUsuarioQuimicos(String cadenaBusqueda) throws Exception;
}
