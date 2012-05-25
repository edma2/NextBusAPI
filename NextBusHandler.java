import java.util.Stack;
import java.util.EmptyStackException;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class NextBusHandler extends DefaultHandler {        
    private Stack<String> tags = new Stack<String>();

    protected String getLastTag() {
        try {
            return tags.peek();
        } catch (EmptyStackException ex) {
            return "";
        }
    }

    protected void pushTag(String tag) {
        tags.push(tag);
    }

    public void characters(char[] ch, int start, int length)
                    throws SAXException {
        if (getLastTag().equals("Error"))
            throw new SAXException(new String(ch, start, length));
    }

    public void endElement(
            java.lang.String uri,
            java.lang.String localName,
            java.lang.String qName) throws SAXException {
        tags.pop();
    }
}
