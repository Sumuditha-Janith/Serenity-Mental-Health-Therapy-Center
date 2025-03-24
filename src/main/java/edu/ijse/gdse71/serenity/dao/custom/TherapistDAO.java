package edu.ijse.gdse71.serenity.dao.custom;

import edu.ijse.gdse71.serenity.dao.CrudDAO;
import edu.ijse.gdse71.serenity.entity.Therapist;

import java.util.ArrayList;

public interface TherapistDAO extends CrudDAO<Therapist,String> {
    ArrayList<String> therapistList();
    Therapist getAllTherapist(String therapistName);
}
