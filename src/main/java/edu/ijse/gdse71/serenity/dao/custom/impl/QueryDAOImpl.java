package edu.ijse.gdse71.serenity.dao.custom.impl;

import edu.ijse.gdse71.serenity.config.FactoryConfiguration;
import edu.ijse.gdse71.serenity.dao.custom.QueryDAO;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public ArrayList<String> getPatientDetails(String selectedPatient) {
        ArrayList<String> patientDetails = new ArrayList<>();

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "SELECT p.id, ts.id, ts.date, ts.time, tp.name, tp.fee " +
                    "FROM TherapySession ts " +
                    "JOIN ts.patient p " +
                    "JOIN ts.therapyProgram tp " +
                    "WHERE p.name = :patientName";

            List<Object[]> results = session.createQuery(hql, Object[].class)
                    .setParameter("patientName", selectedPatient)
                    .list();

            for (Object[] row : results) {
                for (Object field : row) {
                    patientDetails.add(String.valueOf(field));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patientDetails;
    }




}
