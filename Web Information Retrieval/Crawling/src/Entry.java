import org.jsoup.nodes.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Entry implements Serializable {

    private String url;
    private String title;
    private String body;
    private LocalDateTime time;

    Entry(Document document) {
        url = document.location();
        title = document.title();
        body = document.body().text();
        time = LocalDateTime.now();
    }

    String getUrl() {
        return url;
    }

    String getTitle() {
        return title;
    }

    String getBody() {
        return body;
    }

    String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ✤ HH:mm:ss");
        return time.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("URL: %s\nTitle: %s\nTime: %s\nBody: %s\n", getUrl(), getTitle(),
                getTime(), getBody());
    }
}
