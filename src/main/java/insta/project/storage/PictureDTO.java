package insta.project.storage;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Роман on 27.01.2017.
 */
public class PictureDTO {

    private String name; // picture name
    private String owner; // picture owner

    @DateTimeFormat
    private Date date;

    public  PictureDTO(){}

    public PictureDTO(String name, String owner, Date date) {
        this.name = name;
        this.owner = owner;
        this.date = date;
    }

    public PictureDTO(String name, String owner){
        this.name = name;
        this.owner = owner;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
