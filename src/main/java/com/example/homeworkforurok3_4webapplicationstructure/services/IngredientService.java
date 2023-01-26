package com.example.homeworkforurok3_4webapplicationstructure.services;

import com.example.homeworkforurok3_4webapplicationstructure.model.Ingredient;

import java.util.Collection;

public interface IngredientService {

    long addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long id);

    Ingredient editIngredient(long id, Ingredient ingredient);

    boolean deleteIngredient(long id);

    Collection<Ingredient> getAllIngredient();
}
