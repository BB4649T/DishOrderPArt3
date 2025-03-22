package edu.restaurant.app.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private Long id;
    private String name;

    @Builder.Default
    private List<DishIngredient> dishIngredients = new ArrayList<>();

    private Double price;

    // Calcul de la marge brute actuelle
    public Double getGrossMargin() {
        return price - getTotalIngredientsCost();
    }

    // Calcul de la marge brute historique
    public Double getGrossMarginAt(LocalDate date) {
        return price - getTotalIngredientsCostAt(date);
    }

    // Coût total des ingrédients (version actuelle)
    public Double getTotalIngredientsCost() {
        return dishIngredients.stream()
                .mapToDouble(di -> di.getIngredient().getActualPrice() * di.getRequiredQuantity())
                .sum();
    }

    // Coût total des ingrédients à une date donnée
    public Double getTotalIngredientsCostAt(LocalDate date) {
        return dishIngredients.stream()
                .mapToDouble(di -> di.getIngredient().getPriceAt(date) * di.getRequiredQuantity())
                .sum();
    }

    // Quantité disponible actuelle
    public Double getAvailableQuantity() {
        return dishIngredients.stream()
                .mapToDouble(this::calculateIngredientCapacity)
                .min()
                .orElse(0.0);
    }

    public Double getAvailableQuantityAt(Instant datetime) {
        return dishIngredients.stream()
                .mapToDouble(di -> calculateHistoricalIngredientCapacity(di, datetime))
                .min()
                .orElse(0.0);
    }

    private double calculateIngredientCapacity(DishIngredient di) {
        return Math.ceil(
                di.getIngredient().getAvailableQuantity() / di.getRequiredQuantity()
        );
    }

    private double calculateHistoricalIngredientCapacity(DishIngredient di, Instant datetime) {
        return Math.ceil(
                di.getIngredient().getAvailableQuantityAt(datetime) / di.getRequiredQuantity()
        );
    }
}