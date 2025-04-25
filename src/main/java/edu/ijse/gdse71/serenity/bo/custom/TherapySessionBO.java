package edu.ijse.gdse71.serenity.bo.custom;

import edu.ijse.gdse71.serenity.bo.SuperBO;
import edu.ijse.gdse71.serenity.dto.TherapySessionDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TherapySessionBO extends SuperBO {
    boolean save(TherapySessionDTO therapySession);
    boolean update(TherapySessionDTO therapySession);
    boolean updateSessionStatus(String sessionId, String status);
    boolean deleteByPK(String pk) throws Exception;
    List<TherapySessionDTO> getAll();
    Optional<TherapySessionDTO> findByPK(String pk);
    Optional<String> getLastPK();
    boolean exist(String id) throws SQLException, ClassNotFoundException;
}