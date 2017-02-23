package insta.project.storage;

import insta.project.Comment.Comment;
import insta.project.Comment.CommentDTO;
import insta.project.Comment.CommentRepo;
import insta.project.Comment.CommentRepository;
import insta.project.Like.PictureLikes;
import insta.project.Like.PictureLikesRepo;
import insta.project.Like.PictureLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Роман on 28.01.2017.
 */
@RestController
@RequestMapping("api/pictures/")
public class PictureController {


    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PictureRepo pictureRepo;

    @Autowired
    private PictureLikesRepository pictureLikesRepository;

    @Autowired
    private PictureLikesRepo pictureLikesRepo;

    @GetMapping("getAll")
    public List<Picture> get(){
        return pictureRepository.findAll();
    }

    @GetMapping("home")
    public List<Picture> getHomePage()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return pictureRepo.findByOwner(name);
    }

    @PostMapping("upload")
    public Picture upload(@RequestParam("name") String name, @RequestParam("owner") String owner, @RequestParam("file") MultipartFile file) {
        Picture picture = pictureService.upload(new PictureDTO(name, owner), file);
        return picture;
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id)
    {
        Picture picture = pictureRepository.findOne(id);
        pictureRepository.delete(id);
        storageService.delete(picture.getToken());
    }

    @GetMapping("my")
    public  List<Picture> getPictures()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return pictureRepository.findAllByOwnerOrderByIdDesc(name);
    }

    @PostMapping("comment")
    public Comment save(@RequestBody CommentDTO commentDTO)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        commentDTO.setOwner(owner);

        return pictureService.create(commentDTO);
    }

    @GetMapping("{picture_id}")
    public List<Comment> getComments(@PathVariable("picture_id") Long picture_id)
    {
        return commentRepo.findBypicture_id(picture_id);
    }

    @PostMapping("like/{id}")
    public ResponseEntity<PictureLikes> like(@PathVariable("id") Long picture_id)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();


        if(pictureLikesRepo.findByPicId(picture_id, owner)){
            PictureLikes pictureLikes = pictureLikesRepo.findByPicIdAndOwner(picture_id, owner);
            pictureLikesRepository.delete(pictureLikes);
            return new ResponseEntity<PictureLikes>(HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<PictureLikes>(pictureService.saveLike(picture_id), HttpStatus.OK);}


    }

    @GetMapping("profile/{userName}")
    public List<Picture> getUsersPictures(@PathVariable("userName") String userName)
    {
        return pictureRepository.findAllByOwnerOrderByIdDesc(userName);

    }

}
