package edu.ijse.gdse71.serenity.bo.custom.impl;

import edu.ijse.gdse71.serenity.bo.custom.PaymentBO;
import edu.ijse.gdse71.serenity.dao.DAOFactory;
import edu.ijse.gdse71.serenity.dao.custom.impl.PaymentDAOImpl;
import edu.ijse.gdse71.serenity.dto.PatientDTO;
import edu.ijse.gdse71.serenity.dto.PaymentDTO;
import edu.ijse.gdse71.serenity.dto.TherapySessionDTO;
import edu.ijse.gdse71.serenity.entity.Patient;
import edu.ijse.gdse71.serenity.entity.Payment;
import edu.ijse.gdse71.serenity.entity.TherapySession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAOImpl paymentDAO = (PaymentDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public boolean save(PaymentDTO payment) {
        Payment paymentEntity = toEntity(payment);
        return paymentDAO.save(paymentEntity);
    }

    @Override
    public boolean update(PaymentDTO payment) {
        Payment paymentEntity = toEntity(payment);
        return paymentDAO.update(paymentEntity);
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return false;
    }

    @Override
    public List<PaymentDTO> getAll() {
        List<Payment> all = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for (Payment payment : all) {
            PaymentDTO paymentDTO = toDTO(payment);
            paymentDTOList.add(paymentDTO);
        }
        return paymentDTOList;
    }

    @Override
    public Optional<PaymentDTO> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return paymentDAO.getLastPK();
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) return null;

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(payment.getPatient().getId());

        TherapySessionDTO sessionDTO = new TherapySessionDTO();
        sessionDTO.setId(payment.getTherapySession().getId());

        return new PaymentDTO(
                payment.getId(),
                payment.getAmount(),
                payment.getDate(),
                payment.getStatus(),
                patientDTO,
                sessionDTO
        );
    }

    public static Payment toEntity(PaymentDTO dto) {
        if (dto == null) return null;

        Patient patient = new Patient();
        patient.setId(dto.getPatient().getId());

        TherapySession session = new TherapySession();
        session.setId(dto.getTherapySession().getId());

        return new Payment(
                dto.getId(),
                dto.getAmount(),
                dto.getDate(),
                dto.getStatus(),
                patient,
                session
        );
    }

    @Override
    public boolean updatePaymentStatus(String paymentId, String status) {
        return paymentDAO.updatePaymentStatus(paymentId, status);
    }

}
