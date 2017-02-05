package insta.project.storage;

/**
 * Created by Роман on 27.01.2017.
 */
public class PictureDTO {

    private String name;

    public  PictureDTO(){}


    public PictureDTO(String name){
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
