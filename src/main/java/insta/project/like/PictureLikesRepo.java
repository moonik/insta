package insta.project.like;

public interface PictureLikesRepo {

    /**
     * searches picture by picture id and user that wants to like
     * @param id picture id
     * @param owner user that wants to like
     * @return false if like was or true if wasn't
     */
    Boolean findByPicId(Long id, String owner);
}
