package bg.sofia.uni.fmi.mjt.markdown.tags.html.headings;

public enum Headings {
    One(1),
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6);

    private final int number;

    Headings(int number) {
        this.number = number;
    }

    public final int getNumber() {
        return number;
    }
}
