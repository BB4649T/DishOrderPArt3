package edu.restaurant.app.dao.entity;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    private Long id;
    private Order order;
    private OrderProcessStatus status;
    private Instant creationDatetime;
}
