package edu.ijse.gdse71.serenity.dao.custom;

import edu.ijse.gdse71.serenity.dao.SuperDAO;

import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<String> getPatientDetails(String selectedPatient);
}

