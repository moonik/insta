package insta.project.picture;

import insta.project.comment.Comment;
import insta.project.comment.CommentDTO;
import insta.project.comment.CommentRepo;
import insta.project.comment.CommentRepository;
import insta.project.like.PictureLikes;
import insta.project.like.PictureLikesRepo;
import insta.project.like.PictureLikesRepository;
import insta.project.likedPictures.LikedPictures;
import insta.project.likedPictures.LikedPicturesRepository;
import insta.project.picture.exceptions.NotCommentOwnerException;
import insta.project.storage.StorageService;
import insta.project.storage.exceptions.LikeException;
import insta.project.user.domain.UserAccount;
import insta.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Роман on 28.01.2017.
 */
@RestController
@RequestMapping("api/pictures/")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("getAll")
    public List<Picture> get() {
        return pictureService.getAll();
    }

    @GetMapping("getOne/{picture_id}")
    public Picture getOne(@PathVariable("picture_id") Long id) {
        return pictureService.getOne(id);
    }

    @GetMapping("home")
    public List<Picture> getHomePage() {
        return pictureService.getHomePage();
    }

    @PostMapping("upload")
    public Picture upload(@RequestParam("name") String name, @RequestParam("owner") String owner,
                          @RequestParam("file") MultipartFile file) {
        return pictureService.upload(new PictureDTO(name, owner, new Date()), file);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id) {
       pictureService.delete(id);
    }

    @GetMapping("my")
    public List<Picture> getPictures() {
        return pictureService.getPictures();
    }

    @PostMapping("comment")
    public Comment save(@RequestBody CommentDTO commentDTO) {
        return pictureService.create(commentDTO);
    }

    @GetMapping("{picture_id}")
    public List<Comment> getComments(@PathVariable("picture_id") Long picture_id) {
        return pictureService.getComments(picture_id);
    }

    @PostMapping("updateComments/{picture_id}")
    public List<Comment> getNewComments(@PathVariable("picture_id") Long picture_id, @RequestBody Comment lastComment) {
        return pictureService.getNewComments(picture_id, lastComment);
    }

    @DeleteMapping("deleteComment/{id}")
    public void deleteComment(@PathVariable("id") Long id){
        pictureService.deleteComment(id);
    }

    @PostMapping("like/{id}")
    public PictureLikes like(@PathVariable("id") Long picture_id) {
        return pictureService.saveLike(picture_id);
    }

    @GetMapping("profile/{userName}")
    public List<Picture> getUsersPictures(@PathVariable("userName") String userName) {
        return pictureService.getUsersPictures(userName);
    }

    @GetMapping("myNews")
    public List<Picture> getMyNews() {
        return pictureService.getMyNews();
    }

    @PostMapping("savePicture/{id}")
    public Picture savePicture(@PathVariable("id") Long id) {
        return pictureService.savePicture(id);
    }

    @GetMapping("savedPictures")
    public List<Picture> getSavedPictures() {
        return pictureService.getSavedPictures();
    }

    @GetMapping("likedPictures")
    public List<LikedPictures> getLikedPictures() {
        return pictureService.getLikedPictures();
    }
}
