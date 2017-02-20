package insta.project.Like;

import java.util.List;

public interface PictureLikesRepo {

    List<PictureLikes> findBypicture_id(Long id);
    Boolean findByPicId(Long id, String owner);
    public PictureLikes findByPicIdAndOwner(Long id, String owner);
}
