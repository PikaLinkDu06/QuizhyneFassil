package com.boyon_armando.quizhynefassil.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int recipeId;
    private String recipeName;
    private String recipePhotoURL;
    private String steps;
    ArrayList<Ingredient> ingredients;

    public Recipe(int id, String name, String photo) {
        this.recipeId = id;
        this.recipeName = name;
        this.recipePhotoURL = photo;
        ingredients = new ArrayList<>();
    }

    protected Recipe(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        recipePhotoURL = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipePhotoURL() {
        return recipePhotoURL;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return recipeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeString(recipePhotoURL);
        dest.writeString(steps);
    }


}
