class Link {

    private String url;
    private int depth;

    Link(String link, int dep) {
        url = link;
        depth = dep;
    }

    String getUrl() {
        return url;
    }

    int getDepth() {
        return depth;
    }
}
