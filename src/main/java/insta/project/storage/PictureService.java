package insta.project.storage;

import insta.project.Comment.Comment;
import insta.project.Comment.CommentDTO;
import insta.project.Comment.CommentRepository;
import insta.project.Like.PictureLikes;
import insta.project.Like.PictureLikesRepo;
import insta.project.Like.PictureLikesRepository;
import insta.project.InstaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Picture upload(PictureDTO pictureDTO, MultipartFile file)
    {
        if(file == null){
            return null;
        }

        String token = System.currentTimeMillis() + '_' + file.getOriginalFilename();
        if (token == null) {
            return null;
        }
        token = token + InstaUtils.getMultipartFileExtension(file);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        Picture picture = new Picture(pictureDTO.getName(), owner, token);
        storageService.store(file, token);
        return pictureRepository.save(picture);
    }

    public Comment create(CommentDTO commentDTO)
    {

        Comment comment = new Comment(commentDTO.getContent(), commentDTO.getOwner(), commentDTO.getPicture_id());

        return commentRepository.save(comment);
    }

    public PictureLikes saveLike(Long picture_id)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        PictureLikes pictureLikes = new PictureLikes(owner , picture_id);

        return pictureLikesRepository.save(pictureLikes);
    }

}


