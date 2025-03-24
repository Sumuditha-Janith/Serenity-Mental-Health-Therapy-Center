package edu.ijse.gdse71.serenity.config;

import edu.ijse.gdse71.serenity.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration().configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Therapist.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(TherapyProgram.class)
                .addAnnotatedClass(TherapySession.class)
                .addAnnotatedClass(Payment.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null)
                ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
