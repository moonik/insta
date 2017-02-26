package insta.project.Message;

public class MessageDTO {

    private String receiver;
    private String sender;
    private String text_message;

    public MessageDTO(){}

    public MessageDTO(String receiver, String sender, String text_message) {
        this.receiver = receiver;
        this.sender = sender;
        this.text_message = text_message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }
}
