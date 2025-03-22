package edu.restaurant.app.dao.entity;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishOrderStatus {
    private Long id;
    private DishOrder dishOrder;
    private DishOrderStatusType status;
    private Instant creationDatetime;
}