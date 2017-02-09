package insta.project.storage;

import insta.project.Comment.Comment;
import insta.project.Comment.CommentDTO;
import insta.project.Comment.CommentRepository;
import insta.project.TestUtils;
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

    private Picture picture;

    public Picture upload(PictureDTO pictureDTO, MultipartFile file)
    {
        if(file == null){
            return null;
        }

        String token = System.currentTimeMillis() + '_' + file.getOriginalFilename();
        if (token == null) {
            return null;
        }
        token = token + TestUtils.getMultipartFileExtension(file);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        Picture picture = new Picture(pictureDTO.getName(), owner, token);
        storageService.store(file, token);
        return pictureRepository.save(picture);
    }

//    public List<Picture> findPicture(String name)
//    {
//        return pictureRepo.findPictureByOwner(name);
//    }

    public Comment create(CommentDTO commentDTO)
    {

        Comment comment = new Comment(commentDTO.getContent(), commentDTO.getOwner(), commentDTO.getPicture_id());

        return commentRepository.save(comment);
    }

}


