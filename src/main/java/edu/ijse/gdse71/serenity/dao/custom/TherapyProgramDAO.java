package edu.ijse.gdse71.serenity.dao.custom;

import edu.ijse.gdse71.serenity.dao.CrudDAO;
import edu.ijse.gdse71.serenity.entity.TherapyProgram;

import java.util.ArrayList;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram,String> {
    ArrayList<String> getProgramList();
    TherapyProgram getAllTherapyProgram(String programName);
}