package com.example.homeworkforurok3_4webapplicationstructure.services.impl;

import com.example.homeworkforurok3_4webapplicationstructure.services.FilesService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Override
    public boolean saveToFile(String json, String dataFilePath, String dataFileName) {
        try {
            cleanDataFile(dataFilePath, dataFileName);
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromFile(String dataFilePath, String dataFileName) {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cleanDataFile(String dataFilePath, String dataFileName) {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile(File dataFilePath, File dataFileName) {
        return new File((dataFilePath + "/" + dataFileName));
    }
}
