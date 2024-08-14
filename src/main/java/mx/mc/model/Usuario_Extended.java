package mx.mc.model;

public class Usuario_Extended extends Usuario {
    private static final long serialVersionUID = 1;
    
    private String nombreServicio;
    private String perfil;

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
}
