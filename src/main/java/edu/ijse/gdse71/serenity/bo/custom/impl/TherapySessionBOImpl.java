package edu.ijse.gdse71.serenity.bo.custom.impl;

import edu.ijse.gdse71.serenity.bo.custom.TherapySessionBO;
import edu.ijse.gdse71.serenity.dao.DAOFactory;
import edu.ijse.gdse71.serenity.dao.custom.impl.TherapySessionDAOImpl;
import edu.ijse.gdse71.serenity.dto.PatientDTO;
import edu.ijse.gdse71.serenity.dto.TherapistDTO;
import edu.ijse.gdse71.serenity.dto.TherapyProgramDTO;
import edu.ijse.gdse71.serenity.dto.TherapySessionDTO;
import edu.ijse.gdse71.serenity.entity.Patient;
import edu.ijse.gdse71.serenity.entity.Therapist;
import edu.ijse.gdse71.serenity.entity.TherapyProgram;
import edu.ijse.gdse71.serenity.entity.TherapySession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapySessionBOImpl implements TherapySessionBO {

    private final TherapySessionDAOImpl therapySessionDAO = (TherapySessionDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_SESSION);

    @Override
    public boolean save(TherapySessionDTO therapySession) {
        TherapySession therapySession1 = toEntity(therapySession);
        return therapySessionDAO.save(therapySession1);
    }

    @Override
    public boolean update(TherapySessionDTO therapySession) {
        TherapySession therapySession1 = toEntity(therapySession);
        return therapySessionDAO.update(therapySession1);
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return therapySessionDAO.deleteByPK(pk);
    }

    @Override
    public List<TherapySessionDTO> getAll() {
        List<TherapySessionDTO> users = new ArrayList<>();
        List<TherapySession> all = therapySessionDAO.getAll();
        for (TherapySession patient : all) {
            users.add(new TherapySessionDTO(
                    patient.getId(),
                    patient.getDate(),
                    patient.getTime(),
                    patient.getStatus(),
                    new TherapistDTO(
                            patient.getTherapist().getId(),
                            patient.getTherapist().getName(),
                            patient.getTherapist().getSpecialization()
                    ),
                    new PatientDTO(
                            patient.getPatient().getId(),
                            patient.getPatient().getName(),
                            patient.getPatient().getContactInfo(),
                            patient.getPatient().getGender(),
                            patient.getPatient().getBirthDate()
                    ),
                    new TherapyProgramDTO(
                            patient.getTherapyProgram().getProgramId(),
                            patient.getTherapyProgram().getName(),
                            patient.getTherapyProgram().getDuration(),
                            patient.getTherapyProgram().getFee()
                    )
            ));
        }
        return users;
    }

    @Override
    public Optional<TherapySessionDTO> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return therapySessionDAO.getLastPK();
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public static TherapySession toEntity(TherapySessionDTO dto) {
        if (dto == null) return null;

        TherapySession entity = new TherapySession();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setTime(dto.getTime());
        entity.setStatus(dto.getStatus());

        entity.setTherapist(dto.getTherapist() != null ? new Therapist(
                dto.getTherapist().getId(),
                dto.getTherapist().getName(),
                dto.getTherapist().getSpecialization(),
                null
        ) : null);

        entity.setPatient(dto.getPatient() != null ? new Patient(
                dto.getPatient().getId(),
                dto.getPatient().getName(),
                dto.getPatient().getContactInfo(),
                dto.getPatient().getGender(),
                dto.getPatient().getBirthDate(),
                null
        ) : null);

        entity.setTherapyProgram(dto.getTherapyProgram() != null ? new TherapyProgram(
                dto.getTherapyProgram().getProgramId(),
                dto.getTherapyProgram().getName(),
                dto.getTherapyProgram().getDuration(),
                dto.getTherapyProgram().getFee(),
                null
        ) : null);

        return entity;
    }

    public static TherapySessionDTO toDTO(TherapySession entity) {
        if (entity == null) return null;

        TherapistDTO therapistDTO = entity.getTherapist() != null ? new TherapistDTO(
                entity.getTherapist().getId(),
                entity.getTherapist().getName(),
                entity.getTherapist().getSpecialization()
        ) : null;

        PatientDTO patientDTO = entity.getPatient() != null ? new PatientDTO(
                entity.getPatient().getId(),
                entity.getPatient().getName(),
                entity.getPatient().getContactInfo(),
                entity.getPatient().getGender(),
                entity.getPatient().getBirthDate()
        ) : null;

        TherapyProgramDTO programDTO = entity.getTherapyProgram() != null ? new TherapyProgramDTO(
                entity.getTherapyProgram().getProgramId(),
                entity.getTherapyProgram().getName(),
                entity.getTherapyProgram().getDuration(),
                entity.getTherapyProgram().getFee()
        ) : null;

        return new TherapySessionDTO(
                entity.getId(),
                entity.getDate(),
                entity.getTime(),
                entity.getStatus(),
                therapistDTO,
                patientDTO,
                programDTO
        );
    }

    @Override
    public boolean updateSessionStatus(String sessionId, String status) {
        return therapySessionDAO.updateSessionStatus(sessionId, status);
    }

}