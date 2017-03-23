package insta.project.storage;

import java.util.List;

public interface PictureRepo {

    /**
     * searches pictures of logged user
     * @param currentUser - user that is logged at that moment
     * @return list of pictures
     */
    List<Picture> findPictures(String currentUser);

    /**
     * searches pictures from people that you are following
     * @param owner - current user
     * @return list of pictures
     */
    List<Picture> findByFollowing(String owner);

    /**
     * searches saved pictures of current user
     * @param currentUser - logged user
     * @return list of pictures
     */
    List<Picture> findSavedPictures(String currentUser);
}
