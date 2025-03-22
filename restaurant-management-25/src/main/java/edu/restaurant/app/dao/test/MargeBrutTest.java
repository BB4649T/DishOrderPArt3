package edu.restaurant.app.dao.test;

import edu.restaurant.app.dao.entity.*;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MargeBrutTest {

    @Test
    public void testGrossMarginCalculation() {
        // Création des ingrédients avec leurs prix
        Ingredient bread = new Ingredient(1L, "Bread", Arrays.asList(new Price(1000.0)), null);
        Ingredient sausage = new Ingredient(2L, "Sausage", Arrays.asList(new Price(2000.0)), null);

        // Création des DishIngredient avec les quantités requises
        DishIngredient breadIngredient = new DishIngredient(1L, bread, 2.0, Unit.U);
        DishIngredient sausageIngredient = new DishIngredient(2L, sausage, 1.0, Unit.U);

        // Création du plat "Hot Dog" avec les ingrédients et un prix de vente
        Dish hotDog = new Dish(1L, "Hot Dog", Arrays.asList(breadIngredient, sausageIngredient), 7000.0);

        // Calcul de la marge brute
        double grossMargin = hotDog.getGrossMargin();

        // Vérification que la marge brute est correcte
        assertEquals(2000.0, grossMargin, 0.01);
    }
}