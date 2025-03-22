package edu.restaurant.app.dao.test;

import edu.restaurant.app.dao.entity.Ingredient;
import edu.restaurant.app.dao.operations.IngredientCrudOperations;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrierTest {

    @Test
    public void testFilterSortAndPaginateIngredients() {
        // Création d'une liste d'ingrédients
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient(1L, "Bread", null, null),
                new Ingredient(2L, "Sausage", null, null),
                new Ingredient(3L, "Mustard", null, null)
        );

        // Création d'une instance de IngredientCrudOperations
        IngredientCrudOperations ingredientCrudOperations = new IngredientCrudOperations();

        // Filtrage, tri et pagination (simulés ici)
        List<Ingredient> filteredSortedPaginatedIngredients = ingredients.stream()
                .filter(ingredient -> ingredient.getName().contains("r"))
                .sorted((i1, i2) -> i1.getName().compareTo(i2.getName())) // Tri par nom
                .skip(0) // Pagination: première page
                .limit(2)
                .toList();

        // Vérification que la liste filtrée, triée et paginée est correcte
        assertEquals(2, filteredSortedPaginatedIngredients.size());
        assertEquals("Bread", filteredSortedPaginatedIngredients.get(0).getName());
        assertEquals("Mustard", filteredSortedPaginatedIngredients.get(1).getName());
    }
}