package com.example.homeworkforurok3_4webapplicationstructure.services.impl;

import com.example.homeworkforurok3_4webapplicationstructure.model.Recipe;
import com.example.homeworkforurok3_4webapplicationstructure.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final FilesServiceImpl filesService;
    private static Map<Long, Recipe> recipeMap = new HashMap<>();

    public static long id = 0;

    public RecipeServiceImpl(FilesServiceImpl filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public long addRecipe(Recipe recipe) {
        recipeMap.put(id, recipe);
        saveToFile();
        return id++;
    }

    @Override
    public Recipe getRecipe(long id) {
        for (Recipe ignored : recipeMap.values()) {
            Recipe recipe1 = recipeMap.get(id);
            if (recipe1 != null) {
                return recipe1;
            }
        }
        return null;
    }

    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        for (Map.Entry<Long, Recipe> ignored : recipeMap.entrySet()) {
            if (recipeMap.containsKey(id)) {
                recipeMap.put(id, recipe);
                saveToFile();
                return recipe;
            }
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(long id) {
        for (Recipe ignored : recipeMap.values()) {
            if (recipeMap.containsKey(id)) {
                recipeMap.remove(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Recipe> getAllRecipe() {
        return recipeMap.values();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
