
public class User {

    private String userName;
    private String userFamily;
    private String userPassword;
    private String userId;
    private String friends[] = new String[50];
    private String messages[] = new String[500];
    private int counterFriend = 0, counterMessage = 0, counterSender = 0;
    private String sender[] = new String[100];

    public User(String name, String family, String password, String id) {//constructor method
        userName = name;
        userFamily = family;
        userPassword = password;
        userId = id;
    }

    public void addFriend(String friendName) {
        friends[counterFriend] = friendName;
        counterFriend++;
    }

    public void addMessage(String message, User messsender) {
        messages[counterMessage] = message;
        counterMessage++;
        sender[counterSender] = messsender.getName();
        counterSender++;
    }

    public void getMessage() {
        System.out.printf("\n\nReceived messages:\n");
        if (counterMessage < 1) {
            System.out.printf("You have no messages yet.");
        } else {
            for (int h = 0; h < counterMessage; h++) {
                System.out.printf("(%s: %s) ", sender[h], messages[h]);
            }
        }
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
