package com.boyon_armando.quizhynefassil;

import java.util.ArrayList;

public class Recipe {

    private int recipeId ;
    private String recipeName ;
    private ArrayList<String> recipeSteps ;

    public Recipe(int id, String name, ArrayList<String> steps) {
        this.recipeId = id ;
        this.recipeName = name ;
        this.recipeSteps = steps ;
    }

    public Recipe(int id, String name) {
        this.recipeId = id ;
        this.recipeName = name ;
        recipeSteps = new ArrayList<>() ;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<String> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<String> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    @Override
    public String toString() {
        return recipeName ;
    }
}
