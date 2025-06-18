package ru.itis.uzel.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.uzel.service.FileInfoService;

import java.io.IOException;

@Controller
@RequestMapping("/files/{fileName:.+}")
@RequiredArgsConstructor
public class FileController {
    private final FileInfoService fileService;

    @GetMapping
    public void serve(@PathVariable("fileName") String fileName,
                      HttpServletResponse response)
            throws IOException {
        fileService.writeFileToResponse(fileName, response);
    }
}