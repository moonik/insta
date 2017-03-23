package insta.project.Like;

import java.util.List;

public interface PictureLikesRepo {

    List<PictureLikes> findBypicture_id(Long id);

    /**
     * searches picture by picture id and user that wants to like
     * @param id picture id
     * @param owner user that wants to like
     * @return false if like was or true if wasn't
     */
    Boolean findByPicId(Long id, String owner);

    /**
     * searches by picture id and user that wants to like
     * @param id picture id
     * @param owner user that wants to like
     * @return like
     */
    PictureLikes findByPicIdAndOwner(Long id, String owner);
}
