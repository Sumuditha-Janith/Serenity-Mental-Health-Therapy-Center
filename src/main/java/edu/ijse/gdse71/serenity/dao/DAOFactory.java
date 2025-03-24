package edu.ijse.gdse71.serenity.dao;

import edu.ijse.gdse71.serenity.dao.custom.impl.*;


public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType {
        PATIENT,
        PAYMENT,
        QUERY,
        THERAPIST,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        USER
    }

    public SuperDAO getDAO(DAOType type) {
        return switch (type) {
            case PATIENT -> new PatientDAOImpl();
            case PAYMENT -> new PaymentDAOImpl();
            case QUERY -> new QueryDAOImpl();
            case THERAPIST -> new TherapistDAOImpl();
            case THERAPY_PROGRAM -> new TherapyProgramDAOImpl();
            case THERAPY_SESSION -> new TherapySessionDAOImpl();
            case USER -> new UserDAOImpl();
        };
    }

}