import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static javax.swing.JOptionPane.*;

public class SocialMain {

    private LoginFrame loginFrame = new LoginFrame();
    private static RandomAccessFile mainFile;
    private User member[];
    private int memberNumber;
    private int counterMessage = 0;
    private Message message[] = new Message[100];

    public void initialSocial() throws IOException {
        member = new User[10];
        File file = new File("mainFile.txt");
        if (!file.exists()) {
            mainFile = new RandomAccessFile("mainFile.txt", "rw");
            member[0] = new User("Ali", "Barzegar", "rdfgc", mainFile, 0);
            member[1] = new User("Mohammad", "Ghaane", "54", mainFile, 1);
            member[2] = new User("Reza", "Sadeghi", "otx12", mainFile, 2);
            member[3] = new User("Goli", "Khaaki", "rfglh1", mainFile, 3);
            member[4] = new User("Ahmad", "Karimi", "ylted", mainFile, 4);
            member[5] = new User("Mahdi", "Hosseinzadeh", "std", mainFile, 5);
            member[6] = new User("Hamideh", "Shadi", "zxcg", mainFile, 6);
            member[7] = new User("Kobra", "Taslimi", "9765", mainFile, 7);
            member[8] = new User("Zahra", "Mofid", "864513", mainFile, 8);
            member[9] = new User("Gholam", "Pir", "p3099em", mainFile, 9);
            removeUser(10);
        }
        mainFile = new RandomAccessFile("mainFile.txt", "rw");
        showWelcome();
        menu(member[memberNumber] + member[memberNumber].getFriends() + showMessages());
    }

    public void social() {
        showWelcome();
        menu(member[memberNumber] + member[memberNumber].getFriends() + showMessages());
    }

    private void showWelcome() {
        loginFrame.setSize(325,200);
       loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
        String name = loginFrame.getString1();
        while (findUser(name) == -1) {
            loginFrame.jLabel4.setText(" Invalid username");
            loginFrame.string1 = "";
            name = loginFrame.getString1();
        }
        memberNumber = findUser(name);
        createUser(memberNumber);
        String password = loginFrame.getString2();
        while (!password.equals(member[memberNumber].getPassword())) {
            loginFrame.jLabel4.setText(" Wrong password");
            loginFrame.string2 = "";
            password = loginFrame.getString2();

        }
        loginFrame.setVisible(false);
    }

    public int findUser(String inputName) {
        int num;
        int endOfString = 0;
        int counter;
        char[] nameAraay = new char[20];
        for (int i = 0; i < 10; i++) {
            counter = 0;
            num = i * 100000;
            try {
                mainFile.seek(num);
                for (int j = num; j < (num + 20); j++, counter++) {
                    nameAraay[counter] = mainFile.readChar();
                    if (nameAraay[counter] == '#') {
                        endOfString = counter;
                        break;
                    }
                }
            } catch (IOException ie) {
                System.out.print("Error finding user.");
            }
            String Name = String.valueOf(nameAraay);
            Name = Name.substring(0, endOfString);
            if (Name.equals(inputName)) {
                return i;
            }
        }
        return -1;
    }

    private String showMessages() {
        int sender;
        int messageNumber;
        String string = "\n\nYour messages:\n";
        if (member[memberNumber].getCounterMessage() > 0) {
            for (int i = 0; i < member[memberNumber].getCounterMessage(); i++) {
                string += member[memberNumber].getMessage(i);
                sender = findUser(member[memberNumber].getSender(i));
                messageNumber = member[memberNumber].getRealMessageNumber(i);
                createUser(sender);
                if (member[sender].getLike(messageNumber) < 2) {
                    string += String.format(", %d like)", member[sender].getLike(messageNumber));
                } else {
                    string += String.format(", %d likes)", member[sender].getLike(messageNumber));
                }
                if (i + 1 < member[memberNumber].getCounterMessage()) {
                    string += "\n";
                }
                removeUser(sender);
            }
        } else {
            string += "You have no messages.";
        }
        return string;
    }

    private void menu(String priorStrings) {
        String menuNumber;
        while (true) {
            if (priorStrings == null) {
                menuNumber = showInputDialog(null, "1- Add friends\n" + "2- Send message\n"
                        + "3- View friends\n" + "4- Like a message\n" + "5- View sent messages\n"
                        + "6- Logout\n" + "7- Exit\n\n", "Judiance social network", PLAIN_MESSAGE);
            } else {
                menuNumber = showInputDialog(null, priorStrings + "\n\n" + "1- Add friends\n" +
                        "2- Send message\n" + "3- View friends\n" + "4- Like a message\n" +
                        "5- View sent messages\n" + "6- Logout\n" + "7- Exit\n\n", "Judiance social network", PLAIN_MESSAGE);
            }
            switch (menuNumber) {
                case "1":
                    addFriend();
                    break;
                case "2":
                    sendMessage();
                    break;
                case "3":
                    showMessageDialog(null, member[memberNumber].getFriends(), "Your friends", PLAIN_MESSAGE);
                    menu(null);
                    break;
                case "4":
                    likeMessage();
                    break;
                case "5":
                    sentMessages();
                    break;
                case "6":
                    removeUser(memberNumber);
                    social();
                    break;
                case "7":
                    System.exit(0);
                    break;
                default:
                    priorStrings = "Enter only between 1 to 7: ";
            }
        }
    }

    private void addFriend() {
        String name = showInputDialog(null, "\nPlease enter your friend name: ", "Add friends", PLAIN_MESSAGE);
        while (member[memberNumber].findFriend(name) || (member[memberNumber].getName().equals(name)) || findUser(name) == -1) {
            if (findUser(name) == -1) {
                name = showInputDialog(null, "This name does not exist in the program. Enter another one: ", "Add friends", ERROR_MESSAGE);
            } else if (name.equals(member[memberNumber].getName())) {
                menu("You cannot be friend with yourself!");
            } else if (member[memberNumber].findFriend(name)) {
                menu(String.format("%s is already your friend.", name));
            }
        }
        createUser(findUser(name));
        member[memberNumber].addFriend(member[findUser(name)]);
        member[findUser(name)].addFriend(member[memberNumber]);
        removeUser(findUser(name));
        menu(String.format("%s was successfully added to your friends.", name));
    }

    private void sendMessage() {
        if (member[memberNumber].getCounterFriend() == 0) {
            menu("You have no friends to send message to them.");
        } else {
            String messageMenuNumber = showInputDialog(null, "\n1- Send to an individual\n2- Send to all\n", "Send message", PLAIN_MESSAGE);
            while (!"1".equals(messageMenuNumber) && !"2".equals(messageMenuNumber)) {
                messageMenuNumber = showInputDialog(null, "Please enter only 1 or 2: ", "Send message", ERROR_MESSAGE);
            }
            messageSender(messageMenuNumber);
        }
    }

    private void messageSender(String messageMenuNumber) {
        String string;
        if ("1".equals(messageMenuNumber)) {
            String name = showInputDialog(null, "\nEnter your friend name: ", "Friend name", PLAIN_MESSAGE);
            while (!member[memberNumber].findFriend(name) || name.equals(member[memberNumber].getName())) {
                if (name.equals(member[memberNumber].getName())) {
                    string = "You cannot send message to yourself! Enter another name: ";
                } else {
                    string = String.format("%s is not your friend. Enter name of your friend: ", name);
                }
                name = showInputDialog(null, string, "Invalid name", ERROR_MESSAGE);
            }
            member[memberNumber].addReceiver(name);
            inputMessage();
            createUser(findUser(name));
            member[findUser(name)].addMessage(message[counterMessage]);
            removeUser(findUser(name));
        } else {
            member[memberNumber].addReceiver("All");
            inputMessage();
            for (int num = 0; num < 10; num++) {
                createUser(num);
                if (friendFinder(num)) {
                    member[num].addMessage(message[counterMessage]);
                    removeUser(num);
                }
            }
        }
        counterMessage++;
        menu("Your message has been sent successfully.");
    }

    private void inputMessage() {
        String messageString = showInputDialog(null, "\nEnter your message: ", "Message", PLAIN_MESSAGE);
        counterMessage = member[memberNumber].getCounterOwnMessage();
        message[counterMessage] = new Message(messageString, member[memberNumber], counterMessage);
        member[memberNumber].addOwnMessage(message[counterMessage]);
    }

    public void createUser(int userNumber) {
        try {
            member[userNumber] = new User(mainFile, userNumber);
        } catch (IOException io) {
            System.out.println("Error creating the user.");
        }
    }

    public void removeUser(int num) {
        if (num == 10) {
            for (int user = 0; user < 10; user++) {
                member[user] = null;
            }
        } else {
            member[num] = null;
        }
    }

    private void sentMessages() {
        String string = "\nYour sent messages:\n\n";
        if (member[memberNumber].getCounterOwnMessage() == 0) {
            string += "You have sent no messages.";
        } else {
            for (int i = 0; i < member[memberNumber].getCounterOwnMessage(); i++) {
                string += String.format("%d-(\"%s\" to \"%s\" , %d ", i + 1, member[memberNumber].getOneOwnMessage(i),
                        member[memberNumber].getReceiver(i), member[memberNumber].getLike(i));
                if (member[memberNumber].getLike(i) < 2) {
                    string += "like)";
                } else {
                    string += "likes)";
                }
                if (i + 1 < member[memberNumber].getCounterOwnMessage()) {
                    string += "\n";
                }
            }
        }
        showMessageDialog(null, string + "\n\n", "Sent messages", PLAIN_MESSAGE);
        menu(null);
    }

    private boolean friendFinder(int num) {
        for (int i = 0; i <= member[num].getCounterFriend(); i++) {
            if (member[num].getFriendName(i).equals(member[memberNumber].getName())) {
                return true;
            }
        }
        return false;
    }

    private void likeMessage() {
        String string;
        if (member[memberNumber].getCounterMessage() < 1) {
            showMessageDialog(null, "\nYou do not have any messages to like.", "Like", PLAIN_MESSAGE);
            menu(null);
        } else {
            int messageNumber = Integer.parseInt(showInputDialog(null, "\nEnter the number of message you want to like: ", "Like", PLAIN_MESSAGE));
            while (messageNumber < 1 || messageNumber > member[memberNumber].getCounterMessage()) {
                string = "Enter a valid message number ";
                if (member[memberNumber].getCounterMessage() >= 2) {
                    string = string + String.format("(%d - %d): ", 1, member[memberNumber].getCounterMessage());
                } else if (member[memberNumber].getCounterMessage() < 2) {
                    string = string + "(you have only 1 message): ";
                }
                messageNumber = Integer.parseInt(showInputDialog(null, string, "Invalid message number", ERROR_MESSAGE));
            }
            messageNumber--;
            int realMessageNumber = member[memberNumber].getRealMessageNumber(messageNumber);
            int userNumber = findUser(member[memberNumber].getSender(messageNumber));
            createUser(userNumber);
            member[userNumber].addLike(realMessageNumber);
            removeUser(userNumber);
            menu("The message was liked successfully.");
        }
    }
}