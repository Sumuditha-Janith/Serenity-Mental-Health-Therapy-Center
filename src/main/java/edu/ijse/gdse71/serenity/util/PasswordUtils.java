package edu.ijse.gdse71.serenity.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    public static String hashPassword(String password) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        return hasher.hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        BCrypt.Verifyer verifyer = BCrypt.verifyer();
        return verifyer.verify(password.toCharArray(), hashedPassword).verified;
    }
}
