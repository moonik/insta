package tutorial.Test.storage;

import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("getAll")
    public List<Picture> get(){
        return pictureRepository.findAll();
    }

    @PostMapping("upload")
    public Picture upload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        Picture picture = pictureService.upload(new PictureDTO(name), file);
        return picture;
    }
}
