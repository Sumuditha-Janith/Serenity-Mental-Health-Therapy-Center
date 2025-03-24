package edu.ijse.gdse71.serenity.dao.custom;

import edu.ijse.gdse71.serenity.dao.CrudDAO;
import edu.ijse.gdse71.serenity.entity.User;

public interface UserDAO extends CrudDAO<User,String> {
    boolean cheackUser(String email);
    User getSelectUser(String userName);
}