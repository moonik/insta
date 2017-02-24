package insta.project.storage;

import java.util.List;

public interface PictureRepo {

    List<Picture> findByOwner(String owner);
    List<Picture> findByFollowing(String owner);
}
