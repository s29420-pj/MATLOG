package pl.pjatk.MATLOG.domain;

public class UserPasswordValidator {

    public static boolean isSecure(String password) {
        char[] arr = password.toCharArray();
        boolean hasCapitalLetter = password.chars().anyMatch((e) -> e >= 65 && e <= 90);
        boolean hasSpecialSign = password.chars().anyMatch((e) -> e >= 33 && e <= 64);
        boolean isAtLeast6CharactersLong = password.length() >= 6;
        return hasCapitalLetter && hasSpecialSign && isAtLeast6CharactersLong;
    }
}
