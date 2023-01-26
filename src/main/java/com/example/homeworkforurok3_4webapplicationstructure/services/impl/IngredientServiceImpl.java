package com.example.homeworkforurok3_4webapplicationstructure.services.impl;

import com.example.homeworkforurok3_4webapplicationstructure.model.Ingredient;
import com.example.homeworkforurok3_4webapplicationstructure.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FilesServiceImpl2 filesService2;
    private static Map<Long, Ingredient> ingredientMap = new HashMap<>();

    public static long id = 0;

    public IngredientServiceImpl(FilesServiceImpl2 filesService2) {
        this.filesService2 = filesService2;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public long addIngredient(Ingredient ingredient) {
        ingredientMap.put(id, ingredient);
        saveToFile();
        return id++;
    }

    @Override
    public Ingredient getIngredient(long id) {
        for (Ingredient ignored : ingredientMap.values()) {
            Ingredient ingredient1 = ingredientMap.get(id);
            if (ingredient1 != null) {
                return ingredient1;
            }
        }
        return null;
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient ingredient) {
        for (Ingredient ignored : ingredientMap.values()) {
            if (ingredientMap.containsKey(id)) {
                ingredientMap.put(id, ingredient);
                saveToFile();
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(long id) {
        for (Ingredient ignored : ingredientMap.values()) {
            if (ingredientMap.containsKey(id)) {
                ingredientMap.remove(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Ingredient> getAllIngredient() {
        return ingredientMap.values();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService2.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = filesService2.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}



