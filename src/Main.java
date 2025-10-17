import java.io.File;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws Exception {
        XmlManager.downloadFileFromInternet("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada", "archivo.xml");

        File file = new File("archivo.xml");
        
        String nuevoNombre = "noticias_Cultura_"+XmlManager.obtenerFechaPortada(file)+".xml";
        file.renameTo(new File(nuevoNombre));
        file = new File(nuevoNombre);

        HashSet<Noticia> noticias = XmlManager.ExtraerNoticiasXml(file);

        
        if(noticias != null){
            for (Noticia index : noticias) {
                System.out.println(index);
            }
        }
        

        XmlManager.EscribirTXTNoticias(noticias, new File("salida.txt"));

        String categoria = "España";
        System.out.println("El listado de noticias con categoria "+categoria+" está compuesto por "+XmlManager.contarPorCategoria(noticias, categoria)+" noticias");
        

    }
}
