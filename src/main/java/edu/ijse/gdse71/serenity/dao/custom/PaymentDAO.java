package edu.ijse.gdse71.serenity.dao.custom;

import edu.ijse.gdse71.serenity.dao.CrudDAO;
import edu.ijse.gdse71.serenity.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment,String> {
    boolean updatePaymentStatus(String paymentId, String status, String date);
}