
import java.util.Scanner;

public class SocialMain {//this class runs the entire program

    private User member[] = new User[10];//an instance variable of kind array from class User 
    private int memberNumber;//a variable that shows the number of User that has loggined
    private Scanner input = new Scanner(System.in);//an instance variable of Scanner

    public void social() {//this method runs the entire program
        //10 variable of type User
        member[0] = new User("Ali", "Fattahi", "rdfgc", "932501");
        member[1] = new User("Mohammad", "Ghaane", "ytf54", "932502");
        member[2] = new User("Reza", "Sadeghi", "otx", "932503");
        member[3] = new User("Goli", "Sharifi", "rfglh1", "932504");
        member[4] = new User("Ahmad", "Motevaseli", "ylted", "932505");
        member[5] = new User("Mahdi", "Hosseinzadeh", "std", "932506");
        member[6] = new User("Hamideh", "Ranjbar", "zxcg", "932507");
        member[7] = new User("Kobra", "Taslim", "9765", "932508");
        member[8] = new User("Zahra", "Bozorgian", "864513", "932509");
        member[9] = new User("Gholam", "Dashti", "p3099em", "932510");
        showWelcome();
        member[memberNumber].getFriends();
        member[memberNumber].getMessage();
        menu();
    }

    public void initialMenu() {//runs the program every time called
        showWelcome();
        member[memberNumber].getFriends();
        member[memberNumber].getMessage();
        menu();
    }

    public void showWelcome() {//shows the welcome part of program (getting name and password and displaying the user name and family
        String name;
        String password;

        System.out.printf("\n\n***** Welcome to Judiance social network *****\n\n");
        System.out.printf("Enter your name: ");
        name = input.next();

        while (findUser(name) == -1) {
            System.out.printf("Please enter a valid name: ");
            name = input.next();
        }

        memberNumber = findUser(name);

        System.out.printf("Enter your password: ");
        password = input.next();

        while (password == null ? member[memberNumber].getPass() != null : !password.equals(member[memberNumber].getPass())) {
            System.out.printf("The password is wrong. Enter again: ");
            password = input.next();
        }

        System.out.printf("\n\n** %s %s **\n", member[memberNumber].getName(), member[memberNumber].getFamily());
    }

    public int findUser(String tempName) {//a method that checks if the name is one of the users
        for (int x = 0; x < 10; x++) {
            if (tempName == null ? member[x].getName() == null : tempName.equals(member[x].getName())) {
                return x;
            }
        }

        return -1;
    }

    public void menu() {//the menu part of the program
        String menuNumber;
        System.out.printf("\n\nEnter 1 to add friends\nEnter 2 to send message\nEnter 3 to view your friends\nEnter 4 to logout\n");
        menuNumber = input.next();

        while (!"1".equals(menuNumber) && !"2".equals(menuNumber) && !"3".equals(menuNumber) && !"4".equals(menuNumber)) {
            System.out.printf("Enter only between 1 to 4: ");
            menuNumber = input.next();
        }

        if ("1".equals(menuNumber)) {
            addFriends();
        }
        if ("2".equals(menuNumber)) {
            if (member[memberNumber].getNumberOfFriends() == 0) {
                System.out.printf("\nYou have no friends to send message to them.");
                menu();
            } else {
                sendMessage();
            }
        }
        if ("3".equals(menuNumber)) {
            member[memberNumber].getFriends();
            menu();
        }
        if ("4".equals(menuNumber)) {
            initialMenu();
        }
    }

    public void addFriends() {//a method that does the adding-friend part of the program
        String name;
        System.out.printf("\nPlease enter your friend name: ");
        name = input.next();
        findUser(name);

        while (member[memberNumber].findFriend(name) != -1 || (member[memberNumber].getName() == null ? name == null : member[memberNumber].getName().equals(name)) || findUser(name) == -1) {
            if (findUser(name) == -1) {
                System.out.printf("This name does not exist in our program. Enter another one: ");
                name = input.next();
            }

            if (name == null ? member[memberNumber].getName() == null : name.equals(member[memberNumber].getName())) {
                System.out.printf("You cannot be friend with yourself! Enter another name: ");
                name = input.next();
            }

            if (member[memberNumber].findFriend(name) != -1) {
                System.out.printf("%s is already your friend.", name);
                menu();
            }
        }

        member[memberNumber].addFriend(name);
        member[findUser(name)].addFriend(member[memberNumber].getName());
        System.out.printf("\n%s was successfully added to your friends.", name);
        menu();
    }

    public void sendMessage() {//a method that does the sending-message part of the program
        String k;
        System.out.printf("\nEnter 1 to send message to one of your friends\n");
        System.out.printf("Enter 2 to send message to all of your friends\n");
        k = input.next();

        while (!"1".equals(k) && !"2".equals(k)) {
            System.out.printf("Please enter only 1 or 2: ");
            k = input.next();
        }

        messageSender(k);
        menu();
    }

    public void messageSender(String w) {//a submethod that does the sending-message part of the program
        if ("1".equals(w)) {
            Scanner enter = new Scanner(System.in);
            String message;
            String name;
            System.out.printf("\nEnter your friend name: ");
            name = input.next();
            while (member[memberNumber].findFriend(name) == -1 || (name == null ? member[memberNumber].getName() == null : name.equals(member[memberNumber].getName()))) {
                if (name == null ? member[memberNumber].getName() == null : name.equals(member[memberNumber].getName())) {
                    System.out.printf("You cannot send message to yourself! Enter another name: ");
                    name = input.next();
                } else {
                    System.out.printf("%s is not your friend. Enter name of your friend: ", name);
                    name = enter.next();
                }
            }
            System.out.printf("Enter your message: ");
            enter = new Scanner(System.in);
            message = enter.nextLine();
            member[findUser(name)].addMessage(message, member[memberNumber]);
            System.out.printf("\nYour message has been sent to %s seccussfully.", name);

        }

        if ("2".equals(w)) {
            Scanner scan = new Scanner(System.in);
            String message;
            System.out.printf("\nEnter your message: ");
            message = scan.nextLine();

            for (int q = 0; q < 10; q++) {
                if (friendFinder(q) == 1) {
                    member[q].addMessage(message, member[memberNumber]);
                }
            }

            System.out.printf("\nYour message has been sent successfully.");
        }

    }

    public int friendFinder(int num) {//a mothed that checks if there is a friend corresponding given name
        for (int i = 0; i <= member[num].getNumberOfFriends(); i++) {
            if (member[num].getFriendName(i) == null ? member[memberNumber].getName() == null : member[num].getFriendName(i).equals(member[memberNumber].getName())) {
                return 1;
            }
        }
        return -1;
    }

}
