package edu.restaurant.app.dao.test;

import edu.restaurant.app.dao.entity.*;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.AssertJUnit.assertEquals;

public class HotDogTest{

    @Test
    public void testHotDogIngredientsCost() {
        Ingredient bread = new Ingredient(1L, "Bread", Arrays.asList(new Price(1000.0)), null);
        Ingredient sausage = new Ingredient(2L, "Sausage", Arrays.asList(new Price(2000.0)), null);
        Ingredient mustard = new Ingredient(3L, "Mustard", Arrays.asList(new Price(500.0)), null);

        // Création des DishIngredient avec les quantités requises
        DishIngredient breadIngredient = new DishIngredient(1L, bread, 1.0, Unit.U);
        DishIngredient sausageIngredient = new DishIngredient(2L, sausage, 2.0, Unit.U);
        DishIngredient mustardIngredient = new DishIngredient(3L, mustard, 1.0, Unit.U);

        // Création du plat "Hot Dog" avec les ingrédients
        Dish hotDog = new Dish(1L, "Hot Dog", Arrays.asList(breadIngredient, sausageIngredient, mustardIngredient), 0.0);

        // Calcul du coût total des ingrédients
        double totalCost = hotDog.getTotalIngredientsCost();

        assertEquals(5500.0, totalCost, 0.01);
    }
}