import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class SocialMain {

    private static RandomAccessFile mainFile;
    private User member[];
    private int memberNumber;
    private int counterMessage = 0;
    private Message message[] = new Message[100];
    private Scanner input = new Scanner(System.in);

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
        member[memberNumber].getFriends();
        showMessages();
        menu();
    }

    public void social() {
        showWelcome();
        member[memberNumber].getFriends();
        showMessages();
        menu();
    }

    private void showWelcome() {
        System.out.print("\n\n******************************************************************\n\n");
        System.out.print("          ***** Welcome to Judiance social network *****\n\n");
        System.out.print("Enter your name: ");
        String name = input.next();
        while (findUser(name) == -1) {
            System.out.print("Please enter a valid name: ");
            name = input.next();
        }
        memberNumber = findUser(name);
        createUser(memberNumber);
        System.out.print("Enter your password: ");
        String password = input.next();
        while (!password.equals(member[memberNumber].getPassword())) {
            System.out.print("The password doesn't match. Enter again: ");
            password = input.next();
        }
        System.out.print(member[memberNumber]);
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

    private void showMessages() {
        int sender;
        int messageNumber;
        System.out.print("\n\nYour messages:\n");
        if (member[memberNumber].getCounterMessage() > 0) {
            for (int i = 0; i < member[memberNumber].getCounterMessage(); i++) {
                member[memberNumber].getMessage(i);
                sender = findUser(member[memberNumber].getSender(i));
                messageNumber = member[memberNumber].getRealMessageNumber(i);
                createUser(sender);
                if (member[sender].getLike(messageNumber) < 2) {
                    System.out.printf(", %d like)", member[sender].getLike(messageNumber));
                } else {
                    System.out.printf(", %d likes)", member[sender].getLike(messageNumber));
                }
                if (i + 1 < member[memberNumber].getCounterMessage()) {
                    System.out.println();
                }
                removeUser(sender);
            }
        } else {
            System.out.print("You have no messages.");
        }
    }

    private void menu() {
        System.out.print("\n\n1- Add friends\n2- Send message\n3- View friends\n4- Like a message\n5- View sent messages\n6- Logout\n7- Exit\n");
        while (true) {
            String menuNumber = input.next();
            switch (menuNumber) {
                case "1":
                    addFriend();
                    break;
                case "2":
                    sendMessage();
                    break;
                case "3":
                    member[memberNumber].getFriends();
                    menu();
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
                    System.out.print("Enter only between 1 to 7: ");
            }
        }
    }

    private void addFriend() {
        System.out.print("\nPlease enter your friend name: ");
        String name = input.next();
        while (member[memberNumber].findFriend(name) || (member[memberNumber].getName().equals(name)) || findUser(name) == -1) {
            if (findUser(name) == -1) {
                System.out.print("This name does not exist in the program. Enter another one: ");
                name = input.next();
            } else if (name.equals(member[memberNumber].getName())) {
                System.out.print("You cannot be friend with yourself!");
                menu();
            } else if (member[memberNumber].findFriend(name)) {
                System.out.printf("%s is already your friend.", name);
                menu();
            }
        }
        createUser(findUser(name));
        member[memberNumber].addFriend(member[findUser(name)]);
        member[findUser(name)].addFriend(member[memberNumber]);
        removeUser(findUser(name));
        System.out.printf("%s was successfully added to your friends.", name);
        menu();
    }

    private void sendMessage() {
        if (member[memberNumber].getCounterFriend() == 0) {
            System.out.print("\nYou have no friends to send message to them.");
            menu();
        } else {
            System.out.print("\n1- Send to an individual\n2- Send to all\n");
            String messageMenuNumber = input.next();
            while (!"1".equals(messageMenuNumber) && !"2".equals(messageMenuNumber)) {
                System.out.print("Please enter only 1 or 2: ");
                messageMenuNumber = input.next();
            }
            messageSender(messageMenuNumber);
            menu();
        }
    }

    private void messageSender(String messageMenuNumber) {
        if ("1".equals(messageMenuNumber)) {
            input = new Scanner(System.in);
            System.out.print("\nEnter your friend name: ");
            String name = input.next();
            while (!member[memberNumber].findFriend(name) || name.equals(member[memberNumber].getName())) {
                if (name.equals(member[memberNumber].getName())) {
                    System.out.print("You cannot send message to yourself! Enter another name: ");
                } else {
                    System.out.printf("%s is not your friend. Enter name of your friend: ", name);
                }
                name = input.next();
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
        System.out.print("\nYour message has been sent successfully.");
    }

    private void inputMessage() {
        input = new Scanner(System.in);
        System.out.print("\nEnter your message: ");
        String messageString = input.nextLine();
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
        System.out.print("\nYour sent messages:\n");
        if (member[memberNumber].getCounterOwnMessage() == 0) {
            System.out.print("You have sent no messages.");
        } else {
            for (int i = 0; i < member[memberNumber].getCounterOwnMessage(); i++) {
                System.out.printf("%d-(\"%s\" to \"%s\" , %d ", i + 1, member[memberNumber].getOneOwnMessage(i), member[memberNumber].getReceiver(i), member[memberNumber].getLike(i));
                if (member[memberNumber].getLike(i) < 2) {
                    System.out.printf("like)");
                } else {
                    System.out.printf("likes)");
                }
                if (i + 1 < member[memberNumber].getCounterOwnMessage()) {
                    System.out.println();
                }
            }
        }
        menu();
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
        if (member[memberNumber].getCounterMessage() < 1) {
            System.out.print("\nYou do not have any messages to like.");
        } else {
            System.out.print("\nEnter the number of message you want to like: ");
            int messageNumber = input.nextInt();
            while (messageNumber < 1 || messageNumber > member[memberNumber].getCounterMessage()) {
                System.out.print("Enter a valid message number ");
                if (member[memberNumber].getCounterMessage() > 2) {
                    System.out.printf("(%d - %d): ", 1, member[memberNumber].getCounterMessage());
                } else if (member[memberNumber].getCounterMessage() < 2) {
                    System.out.print("(you have only 1 message): ");
                }
                messageNumber = input.nextInt();
            }
            messageNumber--;
            int realMessageNumber = member[memberNumber].getRealMessageNumber(messageNumber);
            int userNumber = findUser(member[memberNumber].getSender(messageNumber));
            createUser(userNumber);
            member[userNumber].addLike(realMessageNumber);
            System.out.print("The message was liked successfully.");
            removeUser(userNumber);
        }
        menu();
    }
}