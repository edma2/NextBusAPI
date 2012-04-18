package ma.eugene.nextbusapi;

import org.xml.sax.SAXException;

public class XMLParseException extends SAXException {
    public XMLParseException(String message) {
        super(message);
    }
}
