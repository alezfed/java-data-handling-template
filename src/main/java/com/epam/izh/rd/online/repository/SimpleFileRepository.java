package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class SimpleFileRepository implements FileRepository {

    @Override
    public long countFilesInDirectory(String path) {
        File directory = new File(path);
        File[] filesInDir = directory.listFiles();
        if (!directory.exists() || filesInDir == null) {
            return 0;
        }
        long count = 0;
        for (File currentFile : filesInDir) {
            if (currentFile.isFile() || currentFile.isHidden()) {
                count++;
            } else {
                count += countFilesInDirectory(currentFile.getPath());
            }
        }
        return count;
    }

    @Override
    public long countDirsInDirectory(String path) {
        File directory = new File(path);
        File[] filesInDir = directory.listFiles();
        if (!directory.exists()) {
            return 0;
        }
        if (filesInDir == null) {
            return 1;
        }
        long count = 1;
        for (File currentFile : filesInDir) {
            if (currentFile.isDirectory()) {
                count += countDirsInDirectory(currentFile.getPath());
            }
        }
        return count;
    }

    @Override
    public void copyTXTFiles(String from, String to) {
        if (from == null || to == null) {
            return;
        }
        File fromDir = new File(from);
        File toDir = new File(to);
        if (!fromDir.isDirectory()) {
            fromDir = fromDir.getParentFile();
        }
        if (!toDir.isDirectory()) {
            toDir = toDir.getParentFile();
        }
        if (!fromDir.exists()) {
            return;
        }
        if (!toDir.exists()) {
            toDir.mkdirs();
        }
        File[] filesInDir = fromDir.listFiles();
        if (filesInDir == null || filesInDir.length == 0) {
            return;
        }
        for (File currentFile : filesInDir) {
            if (currentFile.getName().toLowerCase().endsWith(".txt")) {
                try {
                    Files.copy(currentFile.toPath(), Paths.get(toDir.getPath(), currentFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        if (path == null || name == null) {
            return false;
        }
        Path pathDirectory = Paths.get(path);
        Path pathNewFile = Paths.get(path, name);
        try {
            if (!Files.exists(pathDirectory)) {
                Files.createDirectories(pathDirectory);
            }
            Files.deleteIfExists(pathNewFile);
            Files.createFile(pathNewFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Files.exists(pathNewFile);
    }

    @Override
    public String readFileFromResources(String fileName) {
        if (fileName==null || !Files.exists(Paths.get("src/main/resources", fileName))) {
            return null;
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("src/main/resources", fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            return line;
        }
        return null;
    }
}
