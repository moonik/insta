package insta.project.likedPictures;

public interface LikedPicturesRepo {

    /**
     * searches picture by picture id and picture owner
     * @param id
     * @param owner
     * @return pictures
     */
    LikedPictures findByPicIdAndOwner(Long id, String owner);

}
