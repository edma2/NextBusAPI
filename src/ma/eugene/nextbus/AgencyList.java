package ma.eugene.nextbus;

import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;

public class AgencyList extends Command {
    private List<Agency> agencies = new LinkedList<Agency>();

    public Agency[] getAgencies() {
        return agencies.toArray(new Agency[0]);
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
                if (tag.equals("body")) {
                    agencies.clear();
                } else if (tag.equals("agency")) {
                    String agencyTag = attributes.getValue("tag");
                    String agencyTitle = attributes.getValue("title");
                    agencies.add(new Agency(agencyTag, agencyTitle));
                }
            }
        };
    }

    public static void main(String[] args) {
        AgencyList command = new AgencyList();
        command.execute();
        for (Agency agency : command.getAgencies())
            System.out.println(agency.title);
    }
}
