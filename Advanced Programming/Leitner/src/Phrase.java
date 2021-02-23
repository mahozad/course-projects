import java.util.*;
import java.io.*;

class Phrase implements Serializable, Comparable<Phrase> {

    private String english;
    private String persian;
    private Date date;

    Phrase(String en, String per) {
        english = en;
        persian = per;
        date = new Date();
    }

    @Override
    public int compareTo(Phrase anotherPhrase) {
        return getDate().compareTo(anotherPhrase.getDate());
    }

    public void setDate(Date nextDate) {
        date = nextDate;
    }

    String getEnglish() {
        return english;
    }

    String getPersian() {
        return persian;
    }

    Date getDate() {
        return date;
    }
}
