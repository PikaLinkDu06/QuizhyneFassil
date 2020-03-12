package com.boyon_armando.quizhynefassil;

import java.util.ArrayList;

public class Recipe {

    private int recipeId ;
    private String recipeName ;
    private String recipePhotoURL ;
    private ArrayList<String> recipeSteps ;

    public Recipe(int id, String name, ArrayList<String> steps) {
        this.recipeId = id ;
        this.recipeName = name ;
        this.recipeSteps = steps ;
    }

    public String getRecipePhotoURL() {
        return recipePhotoURL;
    }

    public void setRecipePhotoURL(String recipePhotoURL) {
        this.recipePhotoURL = recipePhotoURL;
    }

    public Recipe(int id, String name, String photo) {
        this.recipeId = id ;
        this.recipeName = name ;
        this.recipePhotoURL = photo ;
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
