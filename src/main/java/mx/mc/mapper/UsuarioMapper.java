package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.model.VistaUsuario;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface UsuarioMapper extends GenericCrudMapper<Usuario, String> {

    Usuario obtenerNombreUsuarioCorreoElectronico(@Param("username") String username);

    Usuario getUserByUserName(@Param("nombreUsuario") String nombreUsuario);

    Usuario getUserByEmail(@Param("correoElectronico") String correoElectronico);

    Usuario obtenerUsuarioPorId(@Param("idUsuario") String idUsuario);

    @Deprecated
    List<VistaUsuario> obtenerVista(@Param("cadena") String cadena);

    List<VistaUsuario> obtenerUsuariosOrdenadoPorCadena(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage,
             @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    Long obtenerTotalUsuariosOrdenadoPorCadena(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);

    List<VistaUsuario> obtenerUsuariosByFechaCreacion(@Param("cadena") String cadena);

    Usuario obtenerIdEstructuraPadre(@Param("idUsuario") String idUsuario) throws Exception;

    Usuario obtenerUsuario(Usuario user) throws Exception;

    List<Usuario> obtenerUsuarios(@Param("cadenaBusqueda") String cadenaBusqueda) throws Exception;

    List<Usuario_Extended> obtenerDoctoresPorTurnoYConsultorio(
            @Param("idTurno") Integer idTurno,
            @Param("idEstructura") String idEstructura) throws Exception;

    List<Usuario> obtenerMedicosPorCriteriosBusqueda(
            @Param("cadenaBusqueda") String cadenaBusqueda,
            @Param("idTipoUsuario") Integer idTipoUsuario,
            @Param("numRegistros") Integer numRegistros,
            @Param("puedePrescribir") Integer puedePrescribir) throws Exception;

    List<Usuario> obtenerListaMedicosActivos() throws Exception;

    Usuario_Extended obtenerCuentaUsuarioPorId(@Param("idUsuario") String idUsuario);

    boolean actualizarCuentaUsuario(Usuario usuario) throws Exception;

    List<Usuario> obtenerListaUsuarios(@Param("idEstructura") String idEstructura) throws Exception;

    Usuario obtenerUsuarioByIdUsuario(@Param("idUsuario") String idUsuario) throws Exception;

    List<Usuario> obtenerMedicosPorCriteriosBusquedaYEstructura(@Param("cadenaBusqueda") String cadenaBusqueda,
            @Param("idTipoUsuario") Integer idTipoUsuario, @Param("idEstructura") String idEstructura,
            @Param("numRegistros") Integer numRegistros, @Param("puedePrescribir") Boolean puedePrescribir) throws Exception;
    
    Usuario obtenerUsuarioByMatriculaPersonal(@Param("matriculaPersonal") String matriculaPersonal) throws Exception;
    
    List<Usuario> getAllUsersByIdRol(@Param("idRol") String idRol)throws Exception;
    
    List<Usuario> obtenerAutoCompUsario(@Param("cadena") String cadena);
    
    List<Usuario> obtenerUsuarioQuimicos(@Param("cadenaBusqueda") String cadenaBusqueda) throws Exception;
}
