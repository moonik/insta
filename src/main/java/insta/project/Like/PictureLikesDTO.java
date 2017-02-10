package insta.project.Like;

public class PictureLikesDTO {

    private Integer likeCounter;
    private String owner;
    private Long picture_id;

    public PictureLikesDTO() {
    }

    public PictureLikesDTO(Integer likeCounter, String owner, Long picture_id) {
        this.likeCounter = likeCounter;
        this.owner = owner;
        this.picture_id = picture_id;
    }


    public Integer getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(Integer likeCounter) {
        this.likeCounter = likeCounter;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(Long picture_id) {
        this.picture_id = picture_id;
    }
}
