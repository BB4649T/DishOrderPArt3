package edu.restaurant.app.dao.test;

import edu.restaurant.app.dao.entity.*;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderCrudTest {

    @Test
    public void save_order() {
        Dish dish = new Dish(1L, "Hot Dog", List.of(), 5.99);

        Order order = Order.builder()
                .reference("CMD-001")
                .creationDatetime(Instant.now())
                .build();

        order.getStatusHistory().add(new OrderStatus(
                null,
                order,
                OrderProcessStatus.CREATED,
                Instant.now()
        ));

        DishOrder dishOrder = DishOrder.builder()
                .dish(dish)
                .quantity(2.0)
                .build();

        order.addDishOrders(List.of(dishOrder));

        assertEquals(OrderProcessStatus.CREATED, order.getActualStatus());
        assertTrue(order.getDishOrders().stream()
                .allMatch(d -> d.getActualStatus() == DishOrderStatusType.PENDING));
    }
}