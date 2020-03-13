package com.boyon_armando.quizhynefassil.classes;

public class Ingredient {

    private String ingredientName ;
    private String quantity ;

    public Ingredient(String name, String quant) {
        this.ingredientName = name ;
        this.quantity = quant ;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
            return " -\t" + quantity + " of " + ingredientName + "\n" ;
    }
}
