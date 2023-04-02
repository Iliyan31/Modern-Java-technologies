package bg.sofia.uni.fmi.mjt.markdown.tags;

public enum OpeningHtmlTags {
    HEADER1("<h1>"),
    HEADER2("<h2>"),
    HEADER3("<h3>"),
    HEADER4("<h4>"),
    HEADER5("<h5>"),
    HEADER6("<h6>"),
    BOLD("<strong>"),
    ITALIC("<em>"),
    CODE("<code>");

    private final String text;

    OpeningHtmlTags(final String text) {
        this.text = text;
    }

    public final String toString() {
        return text;
    }
}
