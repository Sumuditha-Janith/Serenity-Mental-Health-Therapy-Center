package edu.ijse.gdse71.serenity.bo.custom.impl;

import edu.ijse.gdse71.serenity.bo.custom.PatientBO;
import edu.ijse.gdse71.serenity.dao.DAOFactory;
import edu.ijse.gdse71.serenity.dao.custom.impl.PatientDAOImpl;
import edu.ijse.gdse71.serenity.dto.PatientDTO;
import edu.ijse.gdse71.serenity.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientBOImpl implements PatientBO {

    private final PatientDAOImpl patientDAO = (PatientDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);

    @Override
    public boolean save(PatientDTO patient) {
        Patient patientDTO = toEntity(patient);
        return patientDAO.save(patientDTO);
    }

    @Override
    public boolean update(PatientDTO patient) {
        Patient patientDTO = toEntity(patient);
        return patientDAO.update(patientDTO);
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return patientDAO.deleteByPK(pk);
    }

    @Override
    public List<PatientDTO> getAll() {
        List<PatientDTO> users = new ArrayList<>();
        List<Patient> all = patientDAO.getAll();
        for (Patient patient : all) {
            users.add(new PatientDTO(
                    patient.getId(),
                    patient.getName(),
                    patient.getContactInfo(),
                    patient.getGender(),
                    patient.getBirthDate()
            ));
        }
        return users;
    }

    @Override
    public Optional<PatientDTO> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return patientDAO.getLastPK();
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<String> patientList() {
        return patientDAO.patientList();
    }

    @Override
    public PatientDTO getAllPatient(String patientName) {
        Patient patient = patientDAO.getAllPatient(patientName);
        return toDTO(patient);
    }

    public static PatientDTO toDTO(Patient patient) {
        if (patient == null) {
            return null;
        }
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getContactInfo(),
                patient.getGender(),
                patient.getBirthDate()
        );
    }

    public static Patient toEntity(PatientDTO patientDTO) {
        if (patientDTO == null) {
            return null;
        }
        return new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getContactInfo(),
                patientDTO.getGender(),
                patientDTO.getBirthDate(),
                null
        );
    }
}
