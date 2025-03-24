package edu.ijse.gdse71.serenity.dao.custom.impl;

import edu.ijse.gdse71.serenity.config.FactoryConfiguration;
import edu.ijse.gdse71.serenity.dao.custom.TherapistDAO;
import edu.ijse.gdse71.serenity.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TherapistDAOImpl implements TherapistDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Therapist therapist) {
        Transaction transaction = null;

        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            session.persist(therapist);

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
    public boolean update(Therapist therapist) {
        Transaction transaction = null;

        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            session.merge (therapist);

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
    public boolean deleteByPK(String pk) throws Exception {
        Transaction transaction = null;

        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            Therapist therapist = session.get(Therapist.class, pk);
            if (therapist != null) {
                session.remove(therapist);
            } else {
                return false;
            }

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
    public List<Therapist> getAll() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery("FROM Therapist ", Therapist.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Therapist> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = null;
        try {
            session = factoryConfiguration.getSession();
            Long lastPk = session
                    .createQuery("SELECT p.id FROM Therapist p ORDER BY p.id DESC", Long.class)
                    .setMaxResults(1)
                    .uniqueResult();

            Long newPk = (lastPk != null) ? lastPk + 1 : 1;

            System.out.println(newPk);

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
    public ArrayList<String> therapistList() {
        ArrayList<String> therapistsNames = new ArrayList<>();

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            List<String> names = session.createQuery("SELECT tp.name FROM Therapist tp", String.class).list();
            therapistsNames.addAll(names);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return therapistsNames;
    }

    @Override
    public Therapist getAllTherapist(String therapistName) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery(
                            "FROM Therapist WHERE name = :name", Therapist.class)
                    .setParameter("name", therapistName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
