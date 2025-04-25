package edu.ijse.gdse71.serenity.bo;

import edu.ijse.gdse71.serenity.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }

    public enum BOType {
        PATIENT,
        PAYMENT,
        PAYMENT_SESSION,
        THERAPIST,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        QUERY,
        USER
    }

    public SuperBO getBO(BOType type) {
        return switch (type) {
            case PATIENT -> new PatientBOImpl();
            case PAYMENT -> new PaymentBOImpl();
            case PAYMENT_SESSION -> new PaymentSessionBOImpl();
            case THERAPIST -> new TherapistBOImpl();
            case THERAPY_PROGRAM -> new TherapyProgramBOImpl();
            case THERAPY_SESSION -> new TherapySessionBOImpl();
            case QUERY -> new QueryBOImpl();
            case USER -> new UserBOImpl();
        };
    }

}
