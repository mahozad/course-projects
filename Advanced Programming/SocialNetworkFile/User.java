import java.io.IOException;
import java.io.RandomAccessFile;

public class User {

    private static RandomAccessFile file;
    private final int myNumber;
    private int counterFriend, counterMessage, counterSender, counterOwnMessage, counterReceiver;

    public User(String name, String family, String password, RandomAccessFile mainFile, int userNumber) throws IOException {
        file = mainFile;
        myNumber = userNumber;
        file.seek(myNumber * 100000);
        name = String.format("%-20s", name + "#");
        family = String.format("%-20s", family + "#");
        password = String.format("%-20s", password + "#");
        file.writeChars(name);
        file.writeChars(family);
        file.writeChars(password);
        file.writeChars("0#");
        initializeCounters();
    }

    public User(RandomAccessFile mainFile, int userNumber) throws IOException {
        file = mainFile;
        myNumber = userNumber;
    }

    public String getName() {
        long num = myNumber * 100000;
        return readString(num);
    }

    public String getFamily() {
        long num = myNumber * 100000 + 40;
        return readString(num);
    }

    public String getPassword() {
        long num = myNumber * 100000 + 80;
        return readString(num);
    }

    public void addReceiver(String name) {
        long num = myNumber * 100000 + 640 + getCounterReceiver() * 40;
        writeString(num, name);
        counterReceiver++;
        num = myNumber * 100000 + 560;
        writeCounter(num, counterReceiver);
    }

    public String getReceiver(int i) {
        long num = myNumber * 100000 + 640 + i * 40;
        return readString(num);
    }

    public int getCounterReceiver() {
        long num = myNumber * 100000 + 560;
        counterReceiver = readCounter(num);
        return readCounter(num);
    }

    public void addLike(int i) {
        int like = getLike(i);
        like++;
        long num = myNumber * 100000 + 89640 + i * 20;
        writeCounter(num, like);
    }

    public int getLike(int i) {
        long num = myNumber * 100000 + 89640 + i * 20;
        return readCounter(num);
    }

    public void addFriend(User friend) {
        long num = myNumber * 100000 + 140 + getCounterFriend() * 40;
        String name = friend.getName();
        writeString(num, name);
        counterFriend++;
        num = myNumber * 100000 + 120;
        writeCounter(num, counterFriend);
    }

    public int getCounterFriend() {
        long num = myNumber * 100000 + 120;
        counterFriend = readCounter(num);
        return readCounter(num);
    }

    public void addMessage(Message message) {
        long num = myNumber * 100000 + 44640 + getCounterSender() * 40;
        writeString(num, message.getSender());
        counterSender++;
        num = myNumber * 100000 + 600;
        writeCounter(num, counterSender);
        num = myNumber * 100000 + 48640 + getCounterMessage() * 20;
        writeCounter(num, message.getMessageNumber());
        num = myNumber * 100000 + 49640 + getCounterMessage() * 1000;
        writeString(num, message.getMessage());
        counterMessage++;
        num = myNumber * 100000 + 540;
        writeCounter(num, counterMessage);
    }

    public int getCounterMessage() {
        long num = myNumber * 100000 + 540;
        counterMessage = readCounter(num);
        return readCounter(num);
    }

    public int getRealMessageNumber(int i) {
        long num = myNumber * 100000 + 48640 + i * 20;
        return readCounter(num);
    }

    public void getMessage(int i) {
        System.out.printf("%d-(%s: %s ", (i + 1), getSender(i), getOneMessage(i));
    }

    public void getFriends() {
        System.out.print("\nYour friends:\n");
        if (getCounterFriend() < 1) {
            System.out.print("You have no friends yet.");
        } else {
            for (int i = 0; i < getCounterFriend(); i++) {
                System.out.printf("%s ", getFriendName(i));
                if (i + 1 != getCounterFriend()) {
                    System.out.print(", ");
                }
            }
        }
    }

    public void addOwnMessage(Message message) {
        long num = myNumber * 100000 + 4640 + getCounterOwnMessage() * 1000;
        writeString(num, message.getMessage());
        counterOwnMessage++;
        num = myNumber * 100000 + 580;
        writeCounter(num, counterOwnMessage);

    }

    public int getCounterOwnMessage() {
        long num = myNumber * 100000 + 580;
        counterOwnMessage = readCounter(num);
        return readCounter(num);
    }

    public String getOneOwnMessage(int i) {
        long num = myNumber * 100000 + 4640 + i * 1000;
        return readString(num);
    }

    public boolean findFriend(String name) {
        for (int i = 0; i < getCounterFriend(); i++) {
            long num = myNumber * 100000 + 140 + i * 40;
            if (readString(num).equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getSender(int i) {
        long num = myNumber * 100000 + 44640 + i * 40;
        return readString(num);
    }

    public String getOneMessage(int i) {
        long num = myNumber * 100000 + 49640 + i * 1000;
        return readString(num);
    }

    public String getFriendName(int i) {
        long num = myNumber * 100000 + 140 + i * 40;
        return readString(num);
    }

    public int getCounterSender() {
        long num = myNumber * 100000 + 600;
        counterSender = readCounter(num);
        return readCounter(num);
    }

    private void initializeCounters() {
        try {
            for (int i = 0; i < 100; i++) {
                long num = myNumber * 100000 + 89640 + i * 20;
                file.seek(num);
                file.writeChars("0#");
            }
            file.seek(myNumber * 100000 + 540);
            file.writeChars("0#");
            file.seek(myNumber * 100000 + 560);
            file.writeChars("0#");
            file.seek(myNumber * 100000 + 580);
            file.writeChars("0#");
            file.seek(myNumber * 100000 + 600);
            file.writeChars("0#");
        } catch (IOException io) {
            System.out.print("Error initializing counters.");
        }
    }

    private void writeString(long num, String string) {
        try {
            file.seek(num);
            file.writeChars(string + "#");
        } catch (IOException io) {
            System.out.print("Error writing string.");
        }
    }

    private void writeCounter(long num, int counter) {
        try {
            file.seek(num);
            file.writeChars(counter + "#");
        } catch (IOException io) {
            System.out.print("Error writing counter.");
        }
    }

    private String readString(long num) {
        int endOfString = 0;
        int counter = 0;
        char[] stringArray = new char[1000];
        try {
            file.seek(num);
            for (long j = num; j < (num + 1000); j++, counter++) {
                stringArray[counter] = file.readChar();
                if (stringArray[counter] == '#') {
                    endOfString = counter;
                    break;
                }
            }
        } catch (IOException ie) {
            System.out.print("Error reading string.");
        }
        String string = String.valueOf(stringArray);
        return string.substring(0, endOfString);
    }

    private int readCounter(long num) {
        int endOfString = 0;
        int i = 0;
        char counterArray[] = new char[10];
        try {
            file.seek(num);
            for (long j = num; j < (num + 10); j++, i++) {
                counterArray[i] = file.readChar();
                if (counterArray[i] == '#') {
                    endOfString = i;
                    break;
                }
            }
        } catch (IOException io) {
            System.out.print("Error reading counter.");
        }
        String string = String.valueOf(counterArray);
        string = string.substring(0, endOfString);
        return Integer.parseInt(string);
    }

    @Override
    public String toString() {
        String spaces = "";
        int length = getName().length() + getFamily().length() + 7;
        int spaceNumber = (66 - length) / 2;
        for (int i = 0; i < spaceNumber; i++) {
            spaces += " ";
        }
        return String.format("\n%s** %s %s **\n", spaces, getName(), getFamily());
    }
}