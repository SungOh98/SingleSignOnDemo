package com.demo.sso.domain.user.dao;

import com.demo.sso.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAllByAccount(String account) {
        String query = """
                SELECT u
                FROM User u
                WHERE u.account = :account
                """;

        return em.createQuery(query, User.class)
                .setParameter("account", account)
                .getResultList();
    }


    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
