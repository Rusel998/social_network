package ru.itis.uzel.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@UtilityClass
public class ErrorsLogger {

    public static void writeErrorToFile(String context, Exception e) {
        String fileName = context.toLowerCase() + "-errors.log";
        String filePath = "src/main/resources/static/logs/" + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("\n[" + LocalDateTime.now() + "] Ошибка в " + context + ": ");
            writer.write(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                writer.write("\n\t" + ste.toString());
            }
        } catch (IOException ex) {
            System.err.println("Ошибка при записи в лог-файл: " + ex.getMessage());
        }
    }

}
