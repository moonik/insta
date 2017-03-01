package insta.project.LikedPictures;

public interface LikedPicturesRepo {

    LikedPictures findByPicIdAndOwner(Long id, String owner);

}
