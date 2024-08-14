package mx.mc.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TurnoMedicoMapper;
import mx.mc.mapper.UsuarioMapper;
import mx.mc.mapper.UsuariosRolesMapper;
import mx.mc.model.Folios;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.TurnoMedico;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.model.UsuarioRol;
import mx.mc.model.VistaUsuario;
import mx.mc.util.Comunes;
import org.primefaces.model.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class UsuarioServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements UsuarioService {
    
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuariosRolesMapper usuarioRolMapper;

    @Autowired
    private TurnoMedicoMapper turnoMedicoMapper;

    @Autowired
    private FoliosMapper foliosMapper;

    @Autowired
    public UsuarioServiceImpl(GenericCrudMapper<Usuario, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public Usuario obtenerNombreUsuarioCorreoElectronico(String username) throws Exception {
        try {
            return usuarioMapper.obtenerNombreUsuarioCorreoElectronico(username);
        } catch (Exception ex) {
            throw new Exception("Error obtener usuario. " + ex.getMessage());
        }
    }

    @Override
    public Usuario getUserByUserName(String nombreUsuario) throws Exception {
        try {
            return usuarioMapper.getUserByUserName(nombreUsuario);
        } catch (Exception ex) {
            throw new Exception("Error obtener usuario. " + ex.getMessage());
        }
    }

    @Override
    public Usuario getUserByEmail(String correoElectronico) throws Exception {
        try {
            return usuarioMapper.getUserByEmail(correoElectronico);
        } catch (Exception ex) {
            throw new Exception("Error obtener el Correo. " + ex.getMessage());
        }
    }

    @Deprecated
    @Override
    public List<VistaUsuario> obtenerVista(String cadena) throws Exception {
        try {
            return usuarioMapper.obtenerUsuariosByFechaCreacion(cadena);
        } catch (Exception ex) {
            throw new Exception("Error obtener Vista" + ex.getMessage());
        }
    }

    @Override
    public List<VistaUsuario> obtenerUsuariosOrdenadoPorCadena(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage,
             String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : "desc" ;
            return usuarioMapper.obtenerUsuariosOrdenadoPorCadena(paramBusquedaReporte, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Registros. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalUsuariosOrdenadoPorCadena(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return usuarioMapper.obtenerTotalUsuariosOrdenadoPorCadena(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total de registros de Usuarios." + e.getMessage());
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(String idUsuario) throws Exception {
        try {
            return usuarioMapper.obtenerUsuarioPorId(idUsuario);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Usuario " + ex.getMessage());
        }
    }

    @Override
    public Usuario obtenerIdEstructuraPadre(String idUsuario) throws Exception {
        try {
            return usuarioMapper.obtenerIdEstructuraPadre(idUsuario);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Usuario " + ex.getMessage());
        }
    }

    @Override
    public List<Usuario> obtenerUsuarios(String cadenaBusqueda) throws Exception {
        try {
            return usuarioMapper.obtenerUsuarios(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener usuarios " + ex.getMessage());
        }
    }

    @Override
    public List<Usuario> obtenerMedicosPorCriteriosBusqueda(
            String cadenaBusqueda, Integer idTipoUsuario, Integer numRegistros, Integer puedePrescribir) throws Exception {
        try {
            return usuarioMapper.obtenerMedicosPorCriteriosBusqueda(
                    cadenaBusqueda, idTipoUsuario, numRegistros, puedePrescribir);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtenerMedicosPorCriteriosBusqueda " + ex.getMessage());
        }
    }

    @Override
    public List<Usuario_Extended> obtenerDoctoresPorTurnoYConsultorio(Integer idTurno, String idEstructura) throws Exception {
        try {
            return usuarioMapper.obtenerDoctoresPorTurnoYConsultorio(idTurno, idEstructura);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtenerDoctoresPorTurnoYConsultorio " + ex.getMessage());
        }
    }

    @Override
    public Usuario verificaSiExisteUser(Usuario user) throws Exception {
        try {
            return usuarioMapper.obtenerUsuario(user);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al verificaSiExisteUser " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Boolean insertarLayout(Usuario layout, UsuarioRol rolLayout) throws Exception {
        boolean resp = false;
        try {
            resp = usuarioMapper.insertar(layout);
            if (resp) {
                resp = usuarioRolMapper.insertar(rolLayout);
                if (!resp) {
                    throw new Exception("No se pudo insertar el Rol del Usuario ");
                }
            } else {
                throw new Exception("No se pudo insertar el Usuario ");
            }

        } catch (Exception ex) {
            throw new Exception("Ocurrio una Exception al insertar el usuario   " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public Boolean insertarUser(Usuario user) throws Exception {
        boolean resp = false;
        try {
            resp = usuarioMapper.insertar(user);
        } catch (DuplicateKeyException e) {
            String aux = e.getCause().toString().replace("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry", "");
            String[] aux1 = aux.split("'");
            throw new SQLIntegrityConstraintViolationException(aux1[1] + " ya existe.");
        } catch (Exception ex) {
            throw new Exception("Ocurrio una Exception al insertar el usuario   " + ex.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Boolean insertarMedicoYTurno(Usuario user, List<TurnoMedico> listaTurnos) throws Exception {
        boolean resp = false;
        try {
            if (user.getNombreUsuario() == null) {
                // Consultar y generar Folio
                int tipoDoc = TipoDocumento_Enum.USUARIO_MANUAL.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                String folioMedico = Comunes.generaFolio(folio);
                folio.setSecuencia(Comunes.separaFolio(folioMedico));
                resp = foliosMapper.actualizaFolios(folio);

                user.setNombreUsuario(folioMedico);
                user.setCorreoElectronico(folioMedico+"@dominio.com");
                user.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(Constantes.CLAVE_GENERICA));
            } else {
                resp = true;
            }
            
            if (!resp) {
                throw new Exception("Error al registrar nombreUsuario del Médico.");

            } else {
                resp = usuarioMapper.insertar(user);
                if (!resp) {
                    throw new Exception("Error al registrar al Médico.");

                } else if (listaTurnos != null && !listaTurnos.isEmpty()) {
                    resp = turnoMedicoMapper.insertarListaTurnos(listaTurnos);
                    if (!resp) {
                        throw new Exception("Error al registrar turnos del Médico.");

                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrar el Médico. " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public List<Usuario> obtenerListaMedicosActivos() throws Exception {
        try {
            return usuarioMapper.obtenerListaMedicosActivos();
        } catch (Exception ex) {
            throw new Exception("Ocurrio una Exception al buscar usuarios   " + ex.getMessage());
        }
    }

    @Override
    public Usuario_Extended obtenerCuentaUsuarioPorId(String idUsuario) throws Exception {
        try {
            return usuarioMapper.obtenerCuentaUsuarioPorId(idUsuario);
        } catch (Exception ex) {
            throw new Exception("Ocurrio una Exception al buscar la cuenta del Usuario   " + ex.getMessage());
        }
    }

    @Override
    public boolean actualizarCuentaUsuario(Usuario usuario) throws Exception {
        try {
            return usuarioMapper.actualizarCuentaUsuario(usuario);
        } catch (Exception e) {
            throw new Exception("Ocurrio un error al Actualizar la Cuenta del Usuario   " + e.getMessage());
        }

    }

    public List<Usuario> obtenerListaUsuarios(String idEstructura) throws Exception {
        try {
            return usuarioMapper.obtenerListaUsuarios(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtenerListaUsuarios " + ex.getMessage());
        }
    }

    @Override
    public Usuario obtenerUsuarioByIdUsuario(String idUsuario) throws Exception {
        try {
            return usuarioMapper.obtenerUsuarioByIdUsuario(idUsuario);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerUsuarioByIdUsuario " + ex.getMessage());
        }
    }

    @Override
    public List<Usuario> obtenerMedicosPorCriteriosBusquedaYEstructura(String cadenaBusqueda, Integer idTipoUsuario,
            String idEstructura, Integer numRegistros, Boolean puedePrescribir) throws Exception {
        try {
            return usuarioMapper.obtenerMedicosPorCriteriosBusquedaYEstructura(cadenaBusqueda,
                    idTipoUsuario, idEstructura, numRegistros, puedePrescribir);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de buscar el usuario medico   " + ex.getMessage());
        }
    }

    @Override
    public Usuario obtenerUsuarioByMatriculaPersonal(String matriculaPersonal) throws Exception {
        try {
            return usuarioMapper.obtenerUsuarioByMatriculaPersonal(matriculaPersonal);
        } catch (Exception e) {
            throw new Exception("Ocurrio un error al consultar por matriculaPersonal de médico   " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> getAllUsersByIdRol(String idRol) throws Exception {
        try {
            return usuarioMapper.getAllUsersByIdRol(idRol);
        } catch (Exception e) {
            throw new Exception("Ocurrio un error al obtener los usuarios por idRol" + e.getMessage());
        }
    }

    @Override
    public List<Usuario> obtenerAutoCompUsario(String nombre) throws Exception{
        try {
            return usuarioMapper.obtenerAutoCompUsario(nombre);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener los usuarios: " + ex.getMessage());
        }
    }
    
    @Override
    public List<Usuario> obtenerUsuarioQuimicos(String cadenaBusqueda) throws Exception {
        try {
            return usuarioMapper.obtenerUsuarioQuimicos(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener usuarios " + ex.getMessage());
        }
    }
}
