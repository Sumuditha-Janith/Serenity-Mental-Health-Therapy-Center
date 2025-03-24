package edu.ijse.gdse71.serenity.bo.custom.impl;

import edu.ijse.gdse71.serenity.bo.custom.UserBO;
import edu.ijse.gdse71.serenity.dao.DAOFactory;
import edu.ijse.gdse71.serenity.dao.custom.impl.UserDAOImpl;
import edu.ijse.gdse71.serenity.dto.UserDTO;
import edu.ijse.gdse71.serenity.entity.User;
import edu.ijse.gdse71.serenity.util.PasswordUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserBOImpl implements UserBO {

    private final UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean save(UserDTO user) {
        String password = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(password);
        User userEntity = toUser(user);
        return userDAO.save(userEntity);
    }

    @Override
    public boolean update(UserDTO user) {
        return false;
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return false;
    }

    @Override
    public List<UserDTO> getAll() {
        return List.of();
    }

    @Override
    public Optional<UserDTO> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return userDAO.getLastPK();
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean checkUser(String userName) {
        return userDAO.cheackUser(userName);
    }

    @Override
    public UserDTO checkPassword(String userName) {
        User user = userDAO.getSelectUser(userName);
        return toUserDTO(user);
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }

    public static User toUser(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole()
        );
    }

}
