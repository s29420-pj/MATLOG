package pl.pjatk.MATLOG.userManagement;

public class StandardUserPasswordValidator implements UserPasswordValidator {

    @Override
    public UserPasswordValidator getInstance() {
        return this;
    }

    @Override
    public boolean isSecure(String password) {
        boolean hasCapitalLetter = password.chars().anyMatch((e) -> e >= 65 && e <= 90);
        boolean hasSpecialSign = password.chars().anyMatch((e) -> e >= 33 && e <= 64);
        boolean isAtLeast6CharactersLong = password.length() >= 6;
        return hasCapitalLetter && hasSpecialSign && isAtLeast6CharactersLong;
    }
}
