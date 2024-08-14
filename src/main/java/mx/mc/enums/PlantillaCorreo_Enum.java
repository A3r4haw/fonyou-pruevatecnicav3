package mx.mc.enums;

/**
 *
 * @author Cervanets
 */
public enum PlantillaCorreo_Enum {

    CAMBIO_CONTRASENA("Cambio Contraseña", "PASS_CHAN"),
    USUARIO_BLOQUEADO("Usuario Bloqueado", "USER_BLOCK"),
    NOTIFICACION_MEZCLA("Notificacion Mezcla", "NOT_MEZ");

    private final String nombre;
    private final String clave;

    private PlantillaCorreo_Enum(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

}
