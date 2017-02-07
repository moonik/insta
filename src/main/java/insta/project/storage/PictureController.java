package insta.project.storage;

import insta.project.Comment.Comment;
import insta.project.Comment.CommentDTO;
import insta.project.Comment.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CommentRepo commentRepo;


    @GetMapping("getAll")
    public List<Picture> get(){
        return pictureRepository.findAll();
    }

    @PostMapping("upload")
    public Picture upload(@RequestParam("name") String name, @RequestParam("owner") String owner, @RequestParam("file") MultipartFile file) {
        Picture picture = pictureService.upload(new PictureDTO(name, owner), file);
        return picture;
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id)
    {
        pictureRepository.delete(id);
    }

    @GetMapping("my")
    public  List<Picture> getPictures()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return pictureRepository.findAllByOwner(name);
    }

    @PostMapping("comment")
    public Comment save(@RequestBody Comment comment)
    {
        comment = pictureService.create(new CommentDTO(comment.getContent()));
        return comment;
    }
}
