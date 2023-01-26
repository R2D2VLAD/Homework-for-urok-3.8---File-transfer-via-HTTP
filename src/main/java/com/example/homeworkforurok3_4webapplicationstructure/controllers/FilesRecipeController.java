package com.example.homeworkforurok3_4webapplicationstructure.controllers;

import com.example.homeworkforurok3_4webapplicationstructure.services.impl.FilesServiceImpl;
import com.example.homeworkforurok3_4webapplicationstructure.services.impl.FilesServiceImpl2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
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
public class FilesRecipeController {

    private final FilesServiceImpl filesService;
    private final FilesServiceImpl2 filesService2;

    public FilesRecipeController(FilesServiceImpl filesService, FilesServiceImpl2 filesService2) {
        this.filesService = filesService;
        this.filesService2 = filesService2;
    }

    @GetMapping("/recipeExport")
    @Operation(summary = "Endpoint для выгрузки рецептов")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = filesService.getDataFile();
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
        filesService.cleanDataFile();
        File dataFile = filesService.getDataFile();

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
        filesService2.cleanDataFile();
        File dataFile = filesService2.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
