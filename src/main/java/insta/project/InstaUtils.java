package insta.project;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Роман on 27.01.2017.
 */
public class InstaUtils {
    public static String getMultipartFileExtension(MultipartFile file) {
        String[] parts = file.getOriginalFilename().split("\\.");
        return '.' + parts[parts.length-1];
    }
}
