package com.pbru.it.backend.Services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pbru.it.backend.Models.NewsImage;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String IMAGE_DIR;

    public String uploadImageFile(MultipartFile file) {

        try {

            File dir = new File(IMAGE_DIR);
            if (!dir.exists())
                dir.mkdirs();

            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String newFilename = UUID.randomUUID().toString() + fileExtension;

            Path filePath = Paths.get(IMAGE_DIR, newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return newFilename;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void deleteImageFile(String filename) {
        Path filePath = Paths.get(IMAGE_DIR, filename);
        File file = filePath.toFile();
        if (file.exists()) {
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteAllByNewsImage(List<NewsImage> newsImages) {
        for (NewsImage item : newsImages) {
            deleteImageFile(item.getPath());
        }
    }

}
