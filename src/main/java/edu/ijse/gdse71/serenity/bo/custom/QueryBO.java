package edu.ijse.gdse71.serenity.bo.custom;

import edu.ijse.gdse71.serenity.bo.SuperBO;

import java.util.ArrayList;

public interface QueryBO extends SuperBO {
    ArrayList<String> getPatientDetails(String selectedPatient);
}