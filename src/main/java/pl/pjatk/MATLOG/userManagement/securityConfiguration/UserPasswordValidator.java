package pl.pjatk.MATLOG.userManagement.securityConfiguration;

public interface UserPasswordValidator {

    /**
     * Method that checks if password is secure
     * @param password password to check
     * @return boolean true if password is secure otherwise false
     */
    boolean isSecure(String password);

    UserPasswordValidator getInstance();
}
