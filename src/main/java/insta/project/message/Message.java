package insta.project.message;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String sender;
    private String receiver;
    private String text_message;

    @DateTimeFormat
    private Date date;

    public Message() {
    }

//    public message(String sender, String receiver, String text_message) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.text_message = text_message;
//    }

    public Message(String sender, String receiver, String text_message, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.text_message = text_message;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
