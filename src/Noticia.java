import java.util.HashSet;



public class Noticia{

    private String title;
    private String descripcion;
    private String creador;
    private HashSet<String> categoria;

    

    public Noticia(String title, String descripcion, String creador, HashSet<String> categoria) {
        this.title = title;
        this.descripcion = descripcion;
        this.creador = creador;
        this.categoria = categoria;
    }



    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}



    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}



    public String getCreador() {return creador;}
    public void setCreador(String creador) {this.creador = creador;}



    public HashSet<String> getCategoria() {return categoria;}
    public void setCategoria(HashSet<String> categoria) {this.categoria = categoria;}



    @Override
    public String toString() {
        return "Noticia [title=" + title + ", descripcion=" + descripcion + ", creador=" + creador + ", categoria=" + categoria + "]";
    }

}