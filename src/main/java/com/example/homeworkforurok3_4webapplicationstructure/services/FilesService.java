package com.example.homeworkforurok3_4webapplicationstructure.services;

import java.io.File;

public interface FilesService {

    boolean saveToFile(String json, String dataFilePath, String dataFileName);

    String readFromFile(String dataFilePath, String dataFileName);

    File getDataFile(File dataFilePath, File dataFileName);
}
