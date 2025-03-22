package edu.restaurant.app.dao.entity;

import edu.restaurant.app.exception.InsufficientIngredientsException;
import lombok.*;
import java.time.Instant;
import java.util.*;
import static edu.restaurant.app.dao.entity.OrderProcessStatus.*;
import static java.lang.Long.sum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String reference;

    @Builder.Default
    private List<DishOrder> dishOrders = new ArrayList<>();

    @Builder.Default
    private List<OrderStatus> statusHistory = new ArrayList<>();

    private Instant creationDatetime;

    public OrderProcessStatus getActualStatus() {
        return statusHistory.stream()
                .max(Comparator.comparing(OrderStatus::getCreationDatetime))
                .map(OrderStatus::getStatus)
                .orElse(OrderProcessStatus.CREATED);
    }

    public void addDishOrders(List<DishOrder> dishOrders) {
        if (getActualStatus() != OrderProcessStatus.CREATED) {
            throw new IllegalStateException("Cannot add dishes to non-CREATED order");
        }

        dishOrders.forEach(dishOrder -> {
            dishOrder.getStatusHistory().add(new DishOrderStatus(
                    null,
                    dishOrder,
                    DishOrderStatusType.PENDING,
                    Instant.now()
            ));
        });

        this.dishOrders.addAll(dishOrders);
    }

    public void confirm() {
        validateIngredients();
        statusHistory.add(new OrderStatus(null, this, CONFIRMED, Instant.now()));
        updateDishOrdersStatus(DishOrderStatusType.CONFIRMED);
    }

    private void validateIngredients() {
        Map<Ingredient, Double> required = new HashMap<>();

        for (DishOrder dishOrder : dishOrders) {
            for (DishIngredient di : dishOrder.getDish().getDishIngredients()) {
                required.merge(di.getIngredient(),
                        di.getRequiredQuantity() * dishOrder.getQuantity(),
                        Double::sum);
            }
        }

        List<String> missing = new ArrayList<>();
        for (Map.Entry<Ingredient, Double> entry : required.entrySet()) {
            if (entry.getKey().getAvailableQuantity() < entry.getValue()) {
                missing.add(String.format("%s: Need %.2f, Available %.2f",
                        entry.getKey().getName(),
                        entry.getValue(),
                        entry.getKey().getAvailableQuantity()));
            }
        }

        if (!missing.isEmpty()) {
            throw new InsufficientIngredientsException("Missing ingredients: " + String.join(", ", missing));
        }
    }

    public double getTotalAmount() {
        return dishOrders.stream()
                .mapToDouble(dishOrder -> dishOrder.getDish().getPrice() * dishOrder.getQuantity())
                .sum();
    }

    private void updateDishOrdersStatus(DishOrderStatusType status) {
        for (DishOrder dishOrder : dishOrders) {
            dishOrder.getStatusHistory().add(
                    new DishOrderStatus(null, dishOrder, status, Instant.now())
            );
        }
    }
}