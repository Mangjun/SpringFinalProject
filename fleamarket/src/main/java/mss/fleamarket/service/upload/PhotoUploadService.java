package mss.fleamarket.service.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoUploadService {
    private String dir = "D:" + File.separator
            + "app" + File.separator
            + "photos" + File.separator;

    public String upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Path uploadPath = Paths.get(dir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // 디렉토리가 없으면 생성
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        file.transferTo(filePath.toFile());

        return "/photos/" + fileName;
    }
}
