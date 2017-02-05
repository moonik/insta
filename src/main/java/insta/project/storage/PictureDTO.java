package insta.project.storage;

/**
 * Created by Роман on 27.01.2017.
 */
public class PictureDTO {

    private String name;
    private String owner;

    public  PictureDTO(){}


    public PictureDTO(String name, String owner){
        this.name = name;
        this.owner = owner;

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
