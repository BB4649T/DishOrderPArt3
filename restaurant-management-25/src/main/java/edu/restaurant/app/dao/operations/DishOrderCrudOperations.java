package edu.restaurant.app.dao.operations;

import edu.restaurant.app.dao.DataSource;
import edu.restaurant.app.dao.entity.DishOrder;
import edu.restaurant.app.dao.entity.Order;

import java.sql.*;
import java.util.*;

public class DishOrderCrudOperations implements CrudOperations<DishOrder> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<DishOrder> getAll(int page, int size) {
        throw new UnsupportedOperationException("not suppoerted yet");
    }

    @Override
    public DishOrder findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM dish_order WHERE id = ?")) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    DishOrder dishOrder = new DishOrder();
                    dishOrder.setId(resultSet.getLong("id"));
                    return dishOrder;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DishOrder> saveAll(List<DishOrder> entities) {
        throw new UnsupportedOperationException("not supported yet");
    }
}