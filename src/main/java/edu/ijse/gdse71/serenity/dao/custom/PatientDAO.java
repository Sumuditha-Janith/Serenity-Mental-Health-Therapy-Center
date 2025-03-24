package edu.ijse.gdse71.serenity.dao.custom;

import edu.ijse.gdse71.serenity.dao.CrudDAO;
import edu.ijse.gdse71.serenity.entity.Patient;

import java.util.ArrayList;

public interface PatientDAO extends CrudDAO<Patient,String> {
    ArrayList<String> patientList();
    Patient getAllPatient(String patientName);
}