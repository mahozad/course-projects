public class Message {

    private String message;
    private User sender;
    private int messageNumber;

    public Message(String message, User sender, int messageNumber) {
        this.message = message;
        this.sender = sender;
        this.messageNumber = messageNumber;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getSender() {
        return sender.getName();
    }

    public String getMessage() {
        return message;
    }
}