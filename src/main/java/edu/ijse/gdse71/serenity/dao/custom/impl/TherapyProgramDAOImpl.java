package edu.ijse.gdse71.serenity.dao.custom.impl;

import edu.ijse.gdse71.serenity.config.FactoryConfiguration;
import edu.ijse.gdse71.serenity.dao.custom.TherapyProgramDAO;
import edu.ijse.gdse71.serenity.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(TherapyProgram therapyProgram) {
        Transaction transaction = null;

        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            session.persist(therapyProgram);

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
    public boolean update(TherapyProgram therapyProgram) {
        Transaction transaction = null;

        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            session.merge (therapyProgram);

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

            TherapyProgram therapyProgram = session.get(TherapyProgram.class, pk);
            if (therapyProgram != null) {
                session.remove(therapyProgram);
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
    public List<TherapyProgram> getAll() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery("FROM TherapyProgram ", TherapyProgram.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<TherapyProgram> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = null;
        try {
            session = factoryConfiguration.getSession();
            String lastPk = session
                    .createQuery("SELECT t.id FROM TherapyProgram t ORDER BY t.id DESC", String.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastPk != null && lastPk.matches("MT\\d+")) {
                int lastNumber = Integer.parseInt(lastPk.substring(2));
                int newNumber = lastNumber + 1;
                String newPk = String.format("MT%04d", newNumber);
                return Optional.of(newPk);
            } else {
                return Optional.of("MT1001");
            }

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
    public ArrayList<String> getProgramList() {
        ArrayList<String> programNames = new ArrayList<>();

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            List<String> names = session.createQuery("SELECT tp.name FROM TherapyProgram tp", String.class).list();
            programNames.addAll(names);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return programNames;
    }

    @Override
    public TherapyProgram getAllTherapyProgram(String programName) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery(
                            "FROM TherapyProgram WHERE name = :name", TherapyProgram.class)
                    .setParameter("name", programName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
