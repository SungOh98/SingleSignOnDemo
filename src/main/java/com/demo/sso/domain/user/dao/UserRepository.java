package com.demo.sso.domain.user.dao;

import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return Optional.ofNullable(em.find(User.class, id))
                .orElseThrow(() -> UserNotFoundException.withDetail("userId : " + id));
    }

    public void delete(User user) {
        em.remove(user);
    }

//    public List<User> findAllByAccount(String account, String application) {
//        String query = """
//                SELECT u
//                FROM User u
//                WHERE u.account = :account AND u.application = :application
//                """;
//
//        return em.createQuery(query, User.class)
//                .setParameter("account", account)
//                .setParameter("application", application)
//                .getResultList();
//    }
//
//    public List<User> findAllByPhone(String phone, String application) {
//        String query = """
//                SELECT u
//                FROM User u
//                WHERE u.phone = :phone AND u.application = :application
//                """;
//
//        return em.createQuery(query, User.class)
//                .setParameter("phone", phone)
//                .setParameter("application", application)
//                .getResultList();
//    }

    public List<User> findAllByApplication(String application) {
        String query = """
                SELECT u
                FROM User u
                WHERE u.application = :application
                """;

        return em.createQuery(query, User.class)
                .setParameter("application", application)
                .getResultList();
    }


    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
