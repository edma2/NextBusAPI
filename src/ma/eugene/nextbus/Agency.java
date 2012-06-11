package ma.eugene.nextbus;

public class Agency extends TaggedTitle {
    public Agency() {
        super();
    }

    public Agency(String tag, String title) {
        super(tag, title);
    }

    @Override
    public String toString() {
        return title;
    }
}
