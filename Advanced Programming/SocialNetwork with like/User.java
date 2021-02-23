
public class User {

    private String userName;
    private String userFamily;
    private String userPassword;
    private String userId;
    private String friends[] = new String[50];
    private String messages[] = new String[500];
    private int likes[] = new int[500];
    private String ownMessages[] = new String[500];
    private int counterFriend = 0, counterMessage = 0, counterSender = 0, counterOwnMessage = 0, counterReceiver = 0;
    private int thNumber = 0;
    private String sender[] = new String[100];
    private int mn[] = new int[500];
    private String receiver[] = new String[500];

    public User(String name, String family, String password, String id) {//constructor method
        userName = name;
        userFamily = family;
        userPassword = password;
        userId = id;
    }

    public String getReceiver(int i) {
        return receiver[i];
    }

    public void addReceiver(String name) {
        receiver[counterReceiver] = name;
        counterReceiver++;
    }

    public void addLike(int i) {
        likes[i] = likes[i] + 1;
    }

    public int getLike(int i) {
        return likes[i];
    }

    public void addFriend(String friendName) {
        friends[counterFriend] = friendName;
        counterFriend++;
    }

    public void addMessage(String message, User messsender, int messageNumber) {
        messages[counterMessage] = message;
        sender[counterSender] = messsender.getName();
        counterSender++;
        mn[counterMessage] = messageNumber;
        counterMessage++;
    }

    public int getmn(int i) {
        return mn[i];
    }

    public int getThNumberOfMessage() {
        return thNumber;
    }

    public void addThNumber() {
        thNumber++;
    }

    public void getMessage(int i) {
        System.out.printf("%d-(%s: %s ", (i + 1), sender[i], messages[i]);
    }

    public void getFriends() {
        System.out.printf("\nYour friends:\n");
        if (counterFriend < 1) {
            System.out.printf("You have no friends yet.");
        } else {
            for (int u = 0; u < counterFriend; u++) {
                if (u + 1 == counterFriend) {
                    System.out.printf("%s ", friends[u]);
                } else {
                    System.out.printf("%s , ", friends[u]);
                }
            }
        }
    }

    public void addOwnMessage(String message) {
        ownMessages[counterOwnMessage] = message;
        counterOwnMessage++;

    }

    public int getNumberOfOwnMessages() {
        return counterOwnMessage;
    }

    public String getOneOwnMessage(int i) {
        return ownMessages[i];
    }

    public int getNumberOfFriends() {
        return counterFriend;
    }

    public int findFriend(String tempName) {
        for (int x = 0; x <= (counterFriend + 2); x++) {
            if (tempName == null ? friends[x] == null : tempName.equals(friends[x])) {
                return x;
            }
        }

        return -1;
    }

    public String getSender(int i) {
        return sender[i];
    }

    public int getNmberOfMessages() {
        return counterMessage;
    }

    public String getOneMessage(int i) {
        return messages[i];
    }

    public String getFriendName(int i) {
        return friends[i];
    }

    public String getName() {
        return userName;
    }

    public String getFamily() {
        return userFamily;
    }

    public String getPass() {
        return userPassword;
    }
}
