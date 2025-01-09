package pl.pjatk.MATLOG.userManagement.securityConfiguration;


/**
 * Implementation of standard checking if password is secure.
 */
public class StandardUserPasswordValidator implements UserPasswordValidator {

    @Override
    public UserPasswordValidator getInstance() {
        return this;
    }

    /**
     * Method that checks if password is secure according to standard rules.
     * @param password password to check
     * @return true if password is secure, false otherwise
     */
    @Override
    public boolean isSecure(String password) {
        boolean hasCapitalLetter = password.chars().anyMatch(Character::isUpperCase);
        boolean hasSpecialSign = password.chars().anyMatch((e) -> e >= 33 && e <= 64);
        boolean isAtLeast6CharactersLong = password.length() >= 6;
        return hasCapitalLetter && hasSpecialSign && isAtLeast6CharactersLong;
    }
}
