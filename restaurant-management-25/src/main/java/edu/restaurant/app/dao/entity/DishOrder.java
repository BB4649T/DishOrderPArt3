package edu.restaurant.app.dao.entity;

import lombok.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishOrder {
    private Long id;
    private Dish dish;
    private Double quantity;
    private Order order;

    @Builder.Default
    private List<DishOrderStatus> statusHistory = new ArrayList<>();

    public DishOrderStatusType getActualStatus() {
        return statusHistory.stream()
                .max(Comparator.comparing(DishOrderStatus::getCreationDatetime))
                .map(DishOrderStatus::getStatus)
                .orElse(DishOrderStatusType.PENDING);
    }
}