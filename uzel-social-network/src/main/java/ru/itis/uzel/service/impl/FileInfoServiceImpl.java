package ru.itis.uzel.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.repository.FileInfoRepository;
import ru.itis.uzel.service.FileInfoService;
import ru.itis.uzel.util.ErrorsLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Override
    public FileInfo saveFile(MultipartFile uploadFile) {
        try {
            String storageName = UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();

            FileInfo file = new FileInfo()
                    .setType(uploadFile.getContentType())
                    .setOriginalFileName(uploadFile.getOriginalFilename())
                    .setSize(uploadFile.getSize())
                    .setStorageFileName(storageName)
                    .setUrl("/files/" + storageName);

            try {
                Files.copy(uploadFile.getInputStream(), Paths.get(storagePath, storageName));
            } catch (IOException e) {
                throw new IllegalStateException("Не удалось сохранить файл", e);
            }

            return fileInfoRepository.save(file);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("FileInfo", e);
        }
        return null;
    }


    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        try {
            FileInfo fileInfo = fileInfoRepository.findByStorageFileName(fileName);
            response.setContentType(fileInfo.getType());
            try {
                IOUtils.copy(new FileInputStream(fileInfo.getUrl()), response.getOutputStream());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("FileInfo", e);
        }
    }
}
