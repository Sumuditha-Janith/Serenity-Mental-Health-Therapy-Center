package edu.ijse.gdse71.serenity.dao.custom.impl;

import edu.ijse.gdse71.serenity.config.FactoryConfiguration;
import edu.ijse.gdse71.serenity.dao.custom.PaymentDAO;
import edu.ijse.gdse71.serenity.entity.Payment;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Payment payment) {
        return false;
    }

    @Override
    public boolean update(Payment payment) {
        return false;
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return false;
    }

    @Override
    public List<Payment> getAll() {
        return List.of();
    }

    @Override
    public Optional<Payment> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = null;
        try {
            session = factoryConfiguration.getSession();
            Long lastPk = session
                    .createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC", Long.class)
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
}
