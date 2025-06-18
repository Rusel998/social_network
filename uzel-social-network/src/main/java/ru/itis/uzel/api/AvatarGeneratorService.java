package ru.itis.uzel.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.repository.FileInfoRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarGeneratorService {

    private final FileInfoRepository fileInfoRepository;

    @Value("${storage.path}")
    private String storagePath;

    public FileInfo generateAvatarForUser(String seed) {
        String avatarUrl = "https://api.dicebear.com/7.x/adventurer/png?seed=" + seed;
        String fileName = UUID.randomUUID() + "_" + seed + ".png";
        Path filePath = Path.of(storagePath, fileName);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(avatarUrl))
                    .GET()
                    .build();

            HttpResponse<InputStream> response =
                    client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            Files.copy(response.body(), filePath);

            FileInfo fileInfo = new FileInfo()
                    .setOriginalFileName(seed + ".png")
                    .setStorageFileName(fileName)
                    .setType("image/png")
                    .setSize(Files.size(filePath))
                    .setUrl("/files/" + fileName);

            return fileInfoRepository.save(fileInfo);

        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Ошибка при генерации аватара", e);
        }
    }
}
