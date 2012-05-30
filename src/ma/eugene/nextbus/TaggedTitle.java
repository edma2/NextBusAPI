package ma.eugene.nextbus;

public abstract class TaggedTitle {
    public String tag;
    public String title;

    public TaggedTitle() {}

    public TaggedTitle(String tag, String title) {
        this.tag = tag;
        this.title = title;
    }
}
