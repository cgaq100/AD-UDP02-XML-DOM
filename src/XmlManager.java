import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlManager {
    


    public static HashSet<Noticia> ExtraerNoticiasXml(File fileXml){


        if (fileXml.exists() && fileXml.isFile()) {
            HashSet<Noticia> salidaNoticias = new HashSet<>();
            //

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            dbf.setNamespaceAware(true);

            dbf.setIgnoringElementContentWhitespace(true);

            try {
                //

                DocumentBuilder db = dbf.newDocumentBuilder();
                db.setErrorHandler(new NoticiaErrorHandler());
                Document documento = db.parse(fileXml);
                Element elementoRaiz = documento.getDocumentElement();
                
                NodeList listaNodosNoticia = elementoRaiz.getElementsByTagName("item");

                System.out.println("Se dispone de "+listaNodosNoticia.getLength()+" noticias");

                for (int i = 0; i < listaNodosNoticia.getLength(); i++) {
                    Node nodoNoticia = listaNodosNoticia.item(i);

                    if (nodoNoticia.getNodeType() == Node.ELEMENT_NODE) {
                        
                        Element elementoNoticia = (Element)nodoNoticia;

                        NodeList titleNodes = elementoNoticia.getElementsByTagName("title");
                        NodeList descriptionNodes = elementoNoticia.getElementsByTagName("description");
                        NodeList creatorNodes = elementoNoticia.getElementsByTagName("dc:creator");
                        NodeList categoryNodeList = elementoNoticia.getElementsByTagName("category");

                        //System.out.println(titleNodes.item(0).getTextContent());
                        //System.out.println(descriptionNodes.item(0).getTextContent());
                        //System.out.println(creatorNodes.item(0).getTextContent());
                        //System.out.println(categoryNodeList.getLength());

                        HashSet<String> categorias = new HashSet<>();


                        
                        for (int i2 = 0; i2 < categoryNodeList.getLength(); i2++) {
                            categorias.add(categoryNodeList.item(i2).getTextContent());
                        }
                        
                        
                        Noticia newNoticia = new Noticia(titleNodes.item(0).getTextContent(), 
                                                         descriptionNodes.item(0).getTextContent(),
                                                         creatorNodes.item(0).getTextContent(),
                                                         categorias
                                                        );

                        //System.out.println(newNoticia.getTitle());
                        salidaNoticias.add(newNoticia);
                    }

                    
                }

                //
            } catch (Exception e) {
                System.err.println("Error durante el paseo de XML: " + e.getMessage());
            }

            //
            return salidaNoticias;
        }

        return null;
    }




    public static void EscribirTXTNoticias(HashSet<Noticia> noticias){
        


    }



    
    //XmlManager.downloadFileFromInternet("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada", "archivo.xml");
    public static boolean downloadFileFromInternet(String urlString, String fichName){
        URL url = null;

        try {
            url = new URI(urlString).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }

        if (url != null) {
            BufferedInputStream in =null;
            FileOutputStream out= null;
            try{
                in = new BufferedInputStream(url.openStream());
                out = new FileOutputStream(fichName);
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1){
                    out.write(dataBuffer, 0, bytesRead);
                }
            } 
            catch (IOException e) { 
                return false; 
            }
            finally {
                try {
                    if(in != null) in.close();
                    if(out != null) out.close();
                }
                catch(IOException e) {
                    System.err.println("error de E/S durante el cierre de los flujos"); return false;
                }
            }
        }
        else{
            System.err.println("No hay una url asignada");
        }

        return true;
    }




    public static String obtenerFechaPortada(File ficheroXmlNoticias)
    {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document documento = null;
        // Formato de la fecha original en RSS (RFC_1123)
        DateTimeFormatter formatoEntrada = DateTimeFormatter.RFC_1123_DATE_TIME;
        // Formato de salida deseado: yyyyMMdd
        DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("yyyyMMdd",
        Locale.ENGLISH);
        ZonedDateTime fecha = ZonedDateTime.now();
        try
        {
            dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false); // Cuando el xml a procesar no se valida
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new RssErrorHandler());
            documento = db.parse(ficheroXmlNoticias);
            Element elementoRss = documento.getDocumentElement();
            String fechaPortada =
            elementoRss.getElementsByTagName("lastBuildDate").item(0).getTextContent();
            fecha = ZonedDateTime.parse(fechaPortada, formatoEntrada);
        }
        catch (Exception e)
        {
            System.err.println("Error"+ e.getMessage());
        }

        return fecha.format(formatoSalida);
    }


}


