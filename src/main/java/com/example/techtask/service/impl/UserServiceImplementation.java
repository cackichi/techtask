package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUser() {
        String s = "SELECT * " +
                "FROM users u " +
                "JOIN (SELECT user_id, SUM(price * quantity) AS total_price " +
                "FROM orders " +
                "WHERE order_status = 'DELIVERED' " +
                "AND created_at BETWEEN '2003-01-01' AND '2003-12-31' " +
                "GROUP BY user_id " +
                "ORDER BY total_price DESC LIMIT 1) " +
                "o ON u.id = o.user_id";
        Query query = entityManager.createNativeQuery(s, User.class);
        List<User> res = query.getResultList();
        if(!res.isEmpty()) return res.get(0);

        return null;
    }

    @Override
    public List<User> findUsers() {
        String s = "SELECT * " +
                "FROM users u " +
                "JOIN orders o ON u.id = o.user_id " +
                "WHERE o.order_status = 'PAID' " +
                "AND o.created_at BETWEEN '2010-01-01' AND '2010-12-31' ";

        Query query = entityManager.createNativeQuery(s);
        List<User> res = query.getResultList();

        if(!res.isEmpty()) return res;

        return null;
    }
}
