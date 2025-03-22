// src/test/java/edu/restaurant/app/dao/test/DishHistoricalPriceIT.java
package edu.restaurant.app.dao.test;

import edu.restaurant.app.dao.DataSource;
import edu.restaurant.app.dao.entity.Dish;
import edu.restaurant.app.dao.operations.DishCrudOperations;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class HistoriquePriceTest {

    private static DishCrudOperations dishCrud;
    @Test
    public void testHistoricalPriceCalculation() {
        // Given
        LocalDate testDate = LocalDate.of(2023, 10, 1);

        // When
        Dish hotDog = dishCrud.findById(1L);
        double totalCost = hotDog.getTotalIngredientsCostAt(testDate);

        // Then
        assertEquals(4000.0, totalCost, 0.001);
    }

    @Test
    public void testCurrentPriceCalculation() {
        // Given
        LocalDate testDate = LocalDate.of(2023, 10, 5);

        // When
        Dish hotDog = dishCrud.findById(1L);
        double totalCost = hotDog.getTotalIngredientsCostAt(testDate);

        // Then
        assertEquals(4400.0, totalCost, 0.001); // (2*1200) + (1*2200)
    }
}