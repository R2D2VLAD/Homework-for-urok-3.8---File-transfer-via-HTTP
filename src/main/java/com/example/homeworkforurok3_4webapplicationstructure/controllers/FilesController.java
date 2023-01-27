package com.example.homeworkforurok3_4webapplicationstructure.controllers;

import com.example.homeworkforurok3_4webapplicationstructure.services.impl.FilesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/workFiles")
@Tag(name = "Импорт & Экспорт", description = "Endpoint-ы для импортирования и экспортирования данных")
public class FilesController {

    private final FilesServiceImpl filesService;

    public FilesController(FilesServiceImpl filesService) {
        this.filesService = filesService;
    }

    @Value("${path.to.recipe.file}")
    private String dataFilePath1;

    @Value("${name.of.recipe.file}")
    private String dataFileName1;

    @Value("${path.to.ingredient.file}")
    private String dataFilePath2;

    @Value("${name.of.ingredient.file}")
    private String dataFileName2;



    @GetMapping("/recipeExport")
    @Operation(summary = "Endpoint для выгрузки рецептов")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = filesService.getDataFile(new File(dataFilePath1), new File(dataFileName1));
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"Recipe.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/recipeImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Endpoint для загрузки рецептов")
    public ResponseEntity<Void> uploadRecipe(@RequestParam MultipartFile file) {
        filesService.cleanDataFile(dataFilePath1, dataFileName1);
        File dataFile = filesService.getDataFile(new File(dataFilePath1), new File(dataFileName1));

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/ingredientImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Endpoint для загрузки ингредиентов")
    public ResponseEntity<Void> uploadIngredient(@RequestParam MultipartFile file) {
        filesService.cleanDataFile(dataFilePath2, dataFileName2);
        File dataFile = filesService.getDataFile(new File(dataFilePath2), new File(dataFileName2));

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
