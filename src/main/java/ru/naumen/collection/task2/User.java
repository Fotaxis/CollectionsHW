package ru.naumen.collection.task2;

import java.util.Arrays;

/**
 * Пользователь
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class User {
    private String username;
    private String email;
    private byte[] passwordHash;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() == this.getClass())
            return false;
        User objUser = (User) obj;
        return username.equals(objUser.username)
                && email.equals(objUser.email)
                && Arrays.equals(passwordHash, objUser.passwordHash);
    }

    @Override
    public int hashCode() {
        int hash = username.hashCode();
        hash = hash * 31 + email.hashCode();
        hash = hash * 31 + Arrays.hashCode(passwordHash);
        return hash;
    }
}
