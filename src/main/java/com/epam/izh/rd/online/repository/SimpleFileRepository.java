package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SimpleFileRepository implements FileRepository {

    @Override
    public long countFilesInDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory() || directory.listFiles() == null) {
            return 0;
        }
        long count = 0;
        for (File currentFile : directory.listFiles()) {
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
        if (!directory.exists() || !directory.isDirectory() || directory.listFiles() == null) {
            return 0;
        }
        long count = 1;
        for (File currentFile : directory.listFiles()) {
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
        File fromDirectory = new File(from);
        File toDirectory = new File(to);
        if (!fromDirectory.isDirectory()) {
            fromDirectory= fromDirectory.getParentFile();
        }
        if (!toDirectory.isDirectory()) {
            toDirectory = toDirectory.getParentFile();
        }
        if (!fromDirectory.exists() || fromDirectory.listFiles() == null) {
            return;
        }
        if (!toDirectory.exists()) {
            toDirectory.mkdirs();
        }
        for (File currentFile : fromDirectory.listFiles()) {
            if (currentFile.getName().toLowerCase().endsWith(".txt")) {
                try {
                    Files.copy(currentFile.toPath(), Paths.get(toDirectory.getPath(),
                            currentFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources", fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
