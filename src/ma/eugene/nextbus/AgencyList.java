package ma.eugene.nextbus;

import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;

public class AgencyList extends Command {
    private List<String> agencies;

    public String[] getAgencies() {
        return agencies.toArray(new String[0]);
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=agencyList");
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            public void handleElement(String tag, Attributes attributes) {
                if (tag.equals("body"))
                    agencies = new LinkedList<String>();
                else if (tag.equals("agency"))
                    agencies.add(attributes.getValue("tag"));
            }
        };
    }

    public static void main(String[] args) {
        AgencyList command = new AgencyList();
        command.execute();
        for (String agency : command.getAgencies())
            System.out.println(agency);
    }
}
