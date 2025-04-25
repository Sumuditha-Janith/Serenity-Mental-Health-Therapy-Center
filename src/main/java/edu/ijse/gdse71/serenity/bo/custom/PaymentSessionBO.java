package edu.ijse.gdse71.serenity.bo.custom;

import edu.ijse.gdse71.serenity.bo.SuperBO;
import edu.ijse.gdse71.serenity.dto.PaymentDTO;
import edu.ijse.gdse71.serenity.dto.TherapySessionDTO;

public interface PaymentSessionBO extends SuperBO {
    void saveSession(TherapySessionDTO therapySessionDTO, PaymentDTO paymentDTO);
    void updateSession(TherapySessionDTO therapySessionDTO, PaymentDTO paymentDTO);
}

