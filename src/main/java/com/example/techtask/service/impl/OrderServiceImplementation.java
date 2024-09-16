package com.example.techtask.service.impl;

import com.example.techtask.model.Order;
import com.example.techtask.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Order findOrder() {
        String s = "SELECT * FROM orders " +
                "WHERE quantity > 1 " +
                "ORDER BY created_at DESC " +
                "LIMIT 1";
        Query query = entityManager.createNativeQuery(s, Order.class);
        List<Order> res = query.getResultList();

        if(!res.isEmpty()) return res.get(0);
        return null;
    }

    @Override
    public List<Order> findOrders() {
        String s = "SELECT * FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "WHERE u.user_status = 'ACTIVE' " +
                "ORDER BY created_at";

        Query query = entityManager.createNativeQuery(s);
        List<Order> res = query.getResultList();

        if(!res.isEmpty()) return res;
        return null;
    }
}
