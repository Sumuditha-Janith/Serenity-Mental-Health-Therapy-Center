module ORM.CW.Serenity.Mental.Health.Therapy.Center {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;

    requires java.naming;
    requires modelmapper;
    requires bcrypt;
    requires java.desktop;

    opens edu.ijse.gdse71.serenity.controller to javafx.fxml;

    opens edu.ijse.gdse71.serenity.entity to org.hibernate.orm.core;
    opens edu.ijse.gdse71.serenity.config to jakarta.persistence;

    exports edu.ijse.gdse71.serenity;
    exports edu.ijse.gdse71.serenity.controller;


}