package pl.pjatk.MATLOG.domain;

public interface UserPasswordValidator {

    /**
     * Method that checks if password is secure
     * @param password password to check
     * @return boolean true if password is secure otherwise false
     */
    static boolean isSecure(String password) {
        boolean hasCapitalLetter = password.chars().anyMatch((e) -> e >= 65 && e <= 90);
        boolean hasSpecialSign = password.chars().anyMatch((e) -> e >= 33 && e <= 64);
        boolean isAtLeast6CharactersLong = password.length() >= 6;
        return hasCapitalLetter && hasSpecialSign && isAtLeast6CharactersLong;
    }
}
