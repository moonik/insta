package insta.project.storage;

import java.util.List;

public interface PictureRepo {

    List<Picture> findPictures(String currentUser);
    List<Picture> findByFollowing(String owner);
    List<Picture> findSavedPictures(String currentUser);
}
