package edu.ijse.gdse71.serenity.bo.custom;

import edu.ijse.gdse71.serenity.bo.SuperBO;
import edu.ijse.gdse71.serenity.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserBO extends SuperBO {

    boolean save(UserDTO user);
    boolean update(UserDTO user);
    boolean deleteByPK(String pk) throws Exception;
    List<UserDTO> getAll();
    Optional<UserDTO> findByPK(String pk);
    Optional<String> getLastPK();
    boolean exist(String id) throws SQLException, ClassNotFoundException;

    boolean checkUser(String userName);
    UserDTO checkPassword(String userName);
}
