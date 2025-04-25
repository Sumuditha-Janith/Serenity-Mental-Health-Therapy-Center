package edu.ijse.gdse71.serenity.bo.custom.impl;

import edu.ijse.gdse71.serenity.bo.custom.TherapistBO;
import edu.ijse.gdse71.serenity.dao.DAOFactory;
import edu.ijse.gdse71.serenity.dao.custom.impl.TherapistDAOImpl;
import edu.ijse.gdse71.serenity.dto.TherapistDTO;
import edu.ijse.gdse71.serenity.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAOImpl therapistDAO = (TherapistDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);

    @Override
    public boolean save(TherapistDTO therapist) {
        Therapist therapistEntity = toEntity(therapist);
        return therapistDAO.save(therapistEntity);
    }

    @Override
    public boolean update(TherapistDTO therapist) {
        Therapist therapistEntity = toEntity(therapist);
        return therapistDAO.update(therapistEntity);
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return therapistDAO.deleteByPK(pk);
    }

    @Override
    public List<TherapistDTO> getAll() {
        List<TherapistDTO> users = new ArrayList<>();
        List<Therapist> all = therapistDAO.getAll();
        for (Therapist therapist : all) {
            users.add(new TherapistDTO(
                    therapist.getId(),
                    therapist.getName(),
                    therapist.getSpecialization()
            ));
        }
        return users;
    }

    @Override
    public Optional<TherapistDTO> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return therapistDAO.getLastPK();
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<String> therapistList() {
        return therapistDAO.therapistList();
    }

    @Override
    public TherapistDTO getAllTherapist(String therapistName) {
        Therapist therapist = therapistDAO.getAllTherapist(therapistName);
        return toDTO(therapist);
    }

    public static TherapistDTO toDTO(Therapist therapist) {
        if (therapist == null) {
            return null;
        }
        return new TherapistDTO(
                therapist.getId(),
                therapist.getName(),
                therapist.getSpecialization()
        );
    }

    public static Therapist toEntity(TherapistDTO therapistDTO) {
        if (therapistDTO == null) {
            return null;
        }
        return new Therapist(
                therapistDTO.getId(),
                therapistDTO.getName(),
                therapistDTO.getSpecialization(),
                null
        );
    }
}