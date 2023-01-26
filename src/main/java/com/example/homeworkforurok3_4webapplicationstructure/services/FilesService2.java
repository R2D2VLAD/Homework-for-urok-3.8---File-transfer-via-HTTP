package com.example.homeworkforurok3_4webapplicationstructure.services;

import java.io.File;

public interface FilesService2 {

    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();
}
