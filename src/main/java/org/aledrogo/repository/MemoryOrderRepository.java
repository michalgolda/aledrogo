package org.aledrogo.repository;

import org.aledrogo.entity.Order;

import java.util.ArrayList;

public class MemoryOrderRepository extends OrderRepository {
    public ArrayList<Order> orders = new ArrayList<>();


    @Override
    public Order getById(int id) {
        for (Order order : orders)  {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Order> getAll() {
        return orders;
    }

    @Override
    public Order create(Order entity) {
        orders.add(entity);
        return entity;
    }

    @Override
    public Order update(Order entity) {
        ArrayList<Order> ordersForReplace = new ArrayList<>();
        for (Order order : orders) {
            if (order.getId() != entity.getId()) {
                ordersForReplace.add(entity);
            }
        }
        ordersForReplace.add(entity);
        return entity;
    }

    @Override
    public void delete(Order entity) {
        ArrayList<Order> ordersForReplace = new ArrayList<>();
        for (Order order : orders) {
            if (order.getId() != entity.getId()) {
                ordersForReplace.add(entity);
            }
        }
        orders = ordersForReplace;
    }
}
