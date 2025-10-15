import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class RssErrorHandler implements ErrorHandler{

    /*
     * El errorHandler por defecto, NO detiene el paseo del xml
     * 
     * warning -> avisa de posibles problemas pero no criticos. Puede tener sentido que siga paseando.
     * error -> error que podria ser recuperable, por defecto no lo detiene salvo que lo indiquemos
     * fatal error -> error no recuperable
     */

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("Alerta! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("Error! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("Fatal error! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");
        throw exception;
    }

}
