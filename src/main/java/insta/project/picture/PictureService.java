package insta.project.picture;

import insta.project.comment.Comment;
import insta.project.comment.CommentDTO;
import insta.project.comment.CommentRepo;
import insta.project.comment.CommentRepository;
import insta.project.InstaUtils;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private PictureRepo pictureRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepository userRepository;

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

    public Comment create(CommentDTO commentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Comment comment = new Comment(commentDTO.getContent(), auth.getName(), commentDTO.getPicture_id(), new Date());
        return commentRepository.save(comment);
    }

    public PictureLikes saveLike(Long picture_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        PictureLikes pictureLikes = pictureLikesRepository.findByPicIdAndOwner(picture_id, owner);

        if (pictureLikesRepo.findByPicId(picture_id, owner)) {
            pictureLikesRepository.delete(pictureLikes);
            throw new LikeException();
        }else{
            pictureLikes = new PictureLikes(owner, picture_id);
            Picture picture = pictureRepository.findOne(picture_id);
            LikedPictures likedPicture = new LikedPictures(new Date(), picture.getToken(), picture.getOwner(), picture.getId());
            likedPicturesRepository.save(likedPicture);
        }

        return pictureLikesRepository.save(pictureLikes);
    }

    public List<Picture> getAll() {
        return pictureRepository.findAll();
    }

    public Picture getOne(Long id) {
        return pictureRepository.findOne(id);
    }

    public List<Picture> getHomePage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return pictureRepo.findPictures(auth.getName()); // searches pictures where you're not owner
    }

    public void delete(Long id) {
        Picture picture = pictureRepository.findOne(id);
        pictureRepository.delete(id);
        storageService.delete(picture.getToken());
    }

    public List<Picture> getPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return pictureRepository.findAllByOwnerOrderByIdDesc(auth.getName());
    }

    public List<Comment> getComments(@PathVariable("picture_id") Long picture_id) {
        return commentRepository.findBypicture_id(picture_id);
    }

    public List<Comment> getNewComments(Long picture_id, Comment lastComment) {
        if (commentRepo.checkIfNewComment(picture_id, lastComment.getId())) {
            //return new ResponseEntity<List<comment>>(HttpStatus.NOT_FOUND);
            return Collections.emptyList();
        }
        //return new ResponseEntity<List<comment>>(commentRepo.findNewComments(picture_id, lastComment.getId()), HttpStatus.OK);

        return commentRepository.findNewComments(picture_id, lastComment.getId());
    }

    public void deleteComment(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName().equals(commentRepository.findOne(id).getOwner())){
            commentRepository.delete(id);
        }else
            throw new NotCommentOwnerException("You're trying to delete not your comment");
    }

    public List<Picture> getUsersPictures(String userName) {
        return pictureRepository.findAllByOwnerOrderByIdDesc(userName);
    }

    public List<Picture> getMyNews() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        UserAccount user = userRepository.findOneByUsername(currentUser).get();

        List<UserAccount> myFollowings = user.getFollowings();
        List<Picture> myFollowingsPictures = new ArrayList<>();
        for (int i = 0; i < myFollowings.size(); i++) {
            myFollowingsPictures.addAll(pictureRepo.findByFollowing(myFollowings.get(i).getUsername()));
        }

        return myFollowingsPictures;
    }

    public Picture savePicture(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        Picture findPicture = pictureRepository.findOne(id);
        Picture savedPicture = new Picture(new Date(), "saved", findPicture.getToken(), owner);
        return pictureRepository.save(savedPicture);
    }

    public List<Picture> getSavedPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return pictureRepo.findSavedPictures(currentUser);
    }

    public List<LikedPictures> getLikedPictures() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return likedPicturesRepository.findAllByOwnerOrderByIdDesc(currentUser);
    }
}


