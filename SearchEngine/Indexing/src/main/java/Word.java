import java.io.Serializable;

public class Word implements Comparable<Word>, Serializable {

    private final String word;
    private int occurrence = 1;

    Word(String string) {
        word = string;
    }

    public String getWord() {
        return word;
    }

    public int getOccurrence() {
        return occurrence;
    }

    void incrementOccurrence() {
        occurrence++;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Word)) {
            return false;
        }
        Word wordObject = (Word) o;
        return wordObject.word.equals(this.word);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int c = word.hashCode();
        result = 31 * result + c;
        return result;
    }

    @Override
    public String toString() {
        return "Word: " + word + " Occurrence: " + occurrence;
    }

    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }
}
