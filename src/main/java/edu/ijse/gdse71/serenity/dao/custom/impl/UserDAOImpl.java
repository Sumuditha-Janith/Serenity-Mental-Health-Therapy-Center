package edu.ijse.gdse71.serenity.dao.custom.impl;

import edu.ijse.gdse71.serenity.config.FactoryConfiguration;
import edu.ijse.gdse71.serenity.dao.custom.UserDAO;
import edu.ijse.gdse71.serenity.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(User user) {
        Transaction transaction = null;

        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return false;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public Optional<User> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {


        Session session = null;
        try {
            session = factoryConfiguration.getSession();
            Long lastPk = session
                    .createQuery("SELECT u.id FROM User u ORDER BY u.id DESC", Long.class)
                    .setMaxResults(1)
                    .uniqueResult();

            Long newPk = (lastPk != null) ? lastPk + 1 : 1;

            return Optional.of(String.valueOf(newPk));

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean cheackUser(String userName) {
        Session session = null;
        try {
            session = factoryConfiguration.getSession();

            String username = session.createQuery("SELECT u.username FROM User u WHERE u.username = :username", String.class)
                    .setParameter("username", userName)
                    .uniqueResult();

            return username != null;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User getSelectUser(String userName) {
        Session session = null;
        try {
            session = factoryConfiguration.getSession();

            User user = session.createQuery("FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", userName)
                    .uniqueResult();

            System.out.println(user.getPassword());
            System.out.println(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}