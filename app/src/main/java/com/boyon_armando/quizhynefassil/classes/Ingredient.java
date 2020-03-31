package com.boyon_armando.quizhynefassil.classes;

// Classe servant à la récuperation des différents ingrédients d'une recette
public class Ingredient {

    private String ingredientName;
    private String quantity;

    public Ingredient(String name, String quant) {
        this.ingredientName = name.trim();
        this.quantity = quant.trim();
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return " -\t" + ingredientName + " : " + quantity + "\n";
    }
}
