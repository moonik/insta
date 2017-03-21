package insta.project.storage;

import insta.project.Comment.Comment;
import insta.project.Comment.CommentDTO;
import insta.project.Comment.CommentRepo;
import insta.project.Comment.CommentRepository;
import insta.project.Follower.Follower;
import insta.project.Follower.FollowerRepo;
import insta.project.Like.PictureLikes;
import insta.project.Like.PictureLikesRepo;
import insta.project.Like.PictureLikesRepository;
import insta.project.LikedPictures.LikedPictures;
import insta.project.LikedPictures.LikedPicturesRepository;
import insta.project.storage.exceptions.LikeException;
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

    @Autowired
    private FollowerRepo followerRepo;

    @Autowired
    private LikedPicturesRepository likedPicturesRepository;

    @GetMapping("getAll")
    public List<Picture> get() {
        return pictureRepository.findAll();
    }

    @GetMapping("getOne/{picture_id}")
    public Picture getOne(@PathVariable("picture_id") Long id) {
        return pictureRepository.findOne(id);
    }

    @GetMapping("home")
    public List<Picture> getHomePage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return pictureRepo.findPictures(name);
    }

    @PostMapping("upload")
    public Picture upload(@RequestParam("name") String name, @RequestParam("owner") String owner, @RequestParam("file") MultipartFile file) {

        Picture picture = pictureService.upload(new PictureDTO(name, owner, new Date()), file);
        return picture;
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        Picture picture = pictureRepository.findOne(id);
        pictureRepository.delete(id);
        storageService.delete(picture.getToken());
    }

    @GetMapping("my")
    public List<Picture> getPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return pictureRepository.findAllByOwnerOrderByIdDesc(name);
    }

    @PostMapping("comment")
    public Comment save(@RequestBody CommentDTO commentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        commentDTO.setOwner(owner);
        commentDTO.setDate(new Date());

        return pictureService.create(commentDTO);
    }

    @GetMapping("{picture_id}")
    public List<Comment> getComments(@PathVariable("picture_id") Long picture_id) {
        return commentRepo.findBypicture_id(picture_id);
    }

    @PostMapping("updateComments/{picture_id}")
    public List<Comment> getNewComments(@PathVariable("picture_id") Long picture_id, @RequestBody Comment lastComment) {
        if (!(commentRepo.checkIfNewComment(picture_id, lastComment.getId()))) {
            //return new ResponseEntity<List<Comment>>(HttpStatus.NOT_FOUND);
            return Collections.emptyList();
        }
        //return new ResponseEntity<List<Comment>>(commentRepo.findNewComments(picture_id, lastComment.getId()), HttpStatus.OK);

        return commentRepo.findNewComments(picture_id, lastComment.getId());
    }

    @PostMapping("like/{id}")
    public PictureLikes like(@PathVariable("id") Long picture_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();


        if (pictureLikesRepo.findByPicId(picture_id, owner)) {
            PictureLikes pictureLikes = pictureLikesRepo.findByPicIdAndOwner(picture_id, owner);
            pictureLikesRepository.delete(pictureLikes);

            throw new LikeException();
        } else
            return pictureService.saveLike(picture_id);


    }

    @GetMapping("profile/{userName}")
    public List<Picture> getUsersPictures(@PathVariable("userName") String userName) {
        return pictureRepository.findAllByOwnerOrderByIdDesc(userName);

    }

    @GetMapping("myNews")
    public List<Picture> getMyNews() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        List<Follower> myFollowings = followerRepo.findFollowingByName(owner);
        List<Picture> myFollowingsPictures = new ArrayList<>();
        for (int i = 0; i < myFollowings.size(); i++) {
            myFollowingsPictures.addAll(pictureRepo.findByFollowing(myFollowings.get(i).getFollowing()));
        }

        return myFollowingsPictures;
    }

    @PostMapping("savePicture/{id}")
    public Picture savePicture(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        Picture findPicture = pictureRepository.findOne(id);
        Picture savedPicture = new Picture(new Date(), "saved", findPicture.getToken(), owner);
        return pictureRepository.save(savedPicture);
    }

    @GetMapping("savedPictures")
    public List<Picture> getSavedPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return pictureRepo.findSavedPictures(currentUser);
    }

    @GetMapping("likedPictures")
    public List<LikedPictures> getLikedPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return likedPicturesRepository.findAllByOwnerOrderByIdDesc(currentUser);
    }
}
