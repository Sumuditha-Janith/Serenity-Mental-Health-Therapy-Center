package edu.ijse.gdse71.serenity.bo.custom.impl;

import edu.ijse.gdse71.serenity.bo.custom.QueryBO;
import edu.ijse.gdse71.serenity.dao.DAOFactory;
import edu.ijse.gdse71.serenity.dao.custom.impl.QueryDAOImpl;

import java.util.ArrayList;

public class QueryBOImpl implements QueryBO {

    private final QueryDAOImpl queryDAO = (QueryDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public ArrayList<String> getPatientDetails(String selectedPatient) {
        return queryDAO.getPatientDetails(selectedPatient);
    }
}
