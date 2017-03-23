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

    /**
     *
     * @return all pictures
     */
    @GetMapping("getAll")
    public List<Picture> get() {
        return pictureRepository.findAll();
    }

    /**
     * searches picture by id
     * @param id - picture id
     * @return picture
     */
    @GetMapping("getOne/{picture_id}")
    public Picture getOne(@PathVariable("picture_id") Long id) {
        return pictureRepository.findOne(id);
    }

    /**
     * fuction returns all pictures but not yours
     * @return pictures
     */
    @GetMapping("home")
    public List<Picture> getHomePage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return pictureRepo.findPictures(name); // searches pictures where you're not owner
    }

    /**
     * fuction uploads picture
     * @param name - picture name
     * @param owner - picture owner(who uploaded picture)
     * @param file - picture
     * @return picture
     */
    @PostMapping("upload")
    public Picture upload(@RequestParam("name") String name, @RequestParam("owner") String owner, @RequestParam("file") MultipartFile file) {

        Picture picture = pictureService.upload(new PictureDTO(name, owner, new Date()), file);
        return picture;
    }

    /**
     * deletes picture
     * @param id - picture id
     */
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        Picture picture = pictureRepository.findOne(id);
        pictureRepository.delete(id);
        storageService.delete(picture.getToken());
    }

    /**
     * function gets pictures bot only yours
     * @return pictures of logged user
     */
    @GetMapping("my")
    public List<Picture> getPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return pictureRepository.findAllByOwnerOrderByIdDesc(name);
    }

    /**
     * function adds new comment
     * @param commentDTO - object that transfers from front end by method POST
     * @return new comment
     */
    @PostMapping("comment")
    public Comment save(@RequestBody CommentDTO commentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        commentDTO.setOwner(owner);
        commentDTO.setDate(new Date());

        return pictureService.create(commentDTO);
    }

    /**
     *
     * @param picture_id
     * @return comments
     */
    @GetMapping("{picture_id}")
    public List<Comment> getComments(@PathVariable("picture_id") Long picture_id) {
        return commentRepo.findBypicture_id(picture_id); // searches comments for picture
    }

    /**
     *function check and return new comments or if there aren't new comments it returns empty list
     * @param picture_id
     * @param lastComment
     * @return new comments
     */
    @PostMapping("updateComments/{picture_id}")
    public List<Comment> getNewComments(@PathVariable("picture_id") Long picture_id, @RequestBody Comment lastComment) {
        if (!(commentRepo.checkIfNewComment(picture_id, lastComment.getId()))) {
            //return new ResponseEntity<List<Comment>>(HttpStatus.NOT_FOUND);
            return Collections.emptyList();
        }
        //return new ResponseEntity<List<Comment>>(commentRepo.findNewComments(picture_id, lastComment.getId()), HttpStatus.OK);

        return commentRepo.findNewComments(picture_id, lastComment.getId());
    }

    /**
     * like picture
     * searches like by picture id then returns new like or if like already exists then delete like
     * @param picture_id
     * @return new like
     */
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

    /**
     * returns other users pictures
     * @param userName - user profile name
     * @return user's pictures
     */
    @GetMapping("profile/{userName}")
    public List<Picture> getUsersPictures(@PathVariable("userName") String userName) {
        return pictureRepository.findAllByOwnerOrderByIdDesc(userName);

    }

    /**
     * gets pictures of people that you'are following
     * @return pictures
     */
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

    /**
     * saving pictures to your album
     * @param id - picture id
     * @return picture
     */
    @PostMapping("savePicture/{id}")
    public Picture savePicture(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        Picture findPicture = pictureRepository.findOne(id);
        Picture savedPicture = new Picture(new Date(), "saved", findPicture.getToken(), owner);
        return pictureRepository.save(savedPicture);
    }

    /**
     * gets saved pictures
     * @return pictures
     */
    @GetMapping("savedPictures")
    public List<Picture> getSavedPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return pictureRepo.findSavedPictures(currentUser);
    }

    /**
     * gets pictures that you liked
     * @return pictures
     */
    @GetMapping("likedPictures")
    public List<LikedPictures> getLikedPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return likedPicturesRepository.findAllByOwnerOrderByIdDesc(currentUser);
    }
}
