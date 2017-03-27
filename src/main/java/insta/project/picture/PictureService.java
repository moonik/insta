package insta.project.picture;

import insta.project.comment.Comment;
import insta.project.comment.CommentDTO;
import insta.project.comment.CommentRepository;
import insta.project.InstaUtils;
import insta.project.like.PictureLikes;
import insta.project.like.PictureLikesRepo;
import insta.project.like.PictureLikesRepository;
import insta.project.likedPictures.LikedPictures;
import insta.project.likedPictures.LikedPicturesRepository;
import insta.project.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by Роман on 27.01.2017.
 */
@Service
public class PictureService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PictureLikesRepository pictureLikesRepository;

    @Autowired
    private PictureLikesRepo pictureLikesRepo;

    @Autowired
    private LikedPicturesRepository likedPicturesRepository;

    /**
     * uploads picture
     * @param pictureDTO - object from front end
     * @param file - picture
     * @return save picture
     */
    public Picture upload(PictureDTO pictureDTO, MultipartFile file) {
        if (file == null) {
            return null;
        }

        String token = System.currentTimeMillis() + '_' + file.getOriginalFilename();
        if (token == null) {
            return null;
        }
        token = token + InstaUtils.getMultipartFileExtension(file);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        Picture picture = new Picture(pictureDTO.getDate(), pictureDTO.getName(), token, owner);
        storageService.store(file, token);
        return pictureRepository.save(picture);
    }

    /**
     * save new comment
     * @param commentDTO - object from fron end
     * @return save new comment
     */
    public Comment create(CommentDTO commentDTO) {

        Comment comment = new Comment(commentDTO.getContent(), commentDTO.getOwner(), commentDTO.getPicture_id(), commentDTO.getDate());

        return commentRepository.save(comment);
    }

    /**
     * adds like
     * @param picture_id
     * @return save like
     */
    public PictureLikes saveLike(Long picture_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        PictureLikes pictureLikes = new PictureLikes(owner, picture_id);

        Picture picture = pictureRepository.findOne(picture_id);

        LikedPictures likedPicture = new LikedPictures(new Date(), picture.getToken(), picture.getOwner(), picture.getId());

        likedPicturesRepository.save(likedPicture);

        return pictureLikesRepository.save(pictureLikes);
    }

}


