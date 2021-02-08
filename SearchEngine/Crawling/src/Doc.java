import org.jsoup.nodes.Document;

class Doc {

    private Document document;
    private int depth;

    Doc(Document doc, int dep) {
        document = doc;
        depth = dep;
    }

    Document getDocument() {
        return document;
    }

    int getDepth() {
        return depth;
    }
}
