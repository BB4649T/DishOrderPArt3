package edu.restaurant.app.dao.operations;

import java.util.List;

public class OrderCrudOperations implements CrudOperations{
    @Override
    public List getAll(int page, int size) {
        return List.of();
    }

    @Override
    public Object findById(Long id) {
        return null;
    }

    @Override
    public List saveAll(List entities) {
        return List.of();
    }
}
