package insta.project.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import insta.project.TestUtils;

/**
 * Created by Роман on 27.01.2017.
 */
@Service
public class PictureService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private PictureRepository pictureRepository;

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
        Picture picture = new Picture(pictureDTO.getName(), token);
        storageService.store(file, token);
        return pictureRepository.save(picture);
    }

}


