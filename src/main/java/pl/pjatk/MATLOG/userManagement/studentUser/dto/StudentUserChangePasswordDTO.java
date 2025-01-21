package pl.pjatk.MATLOG.userManagement.studentUser.dto;

public record StudentUserChangePasswordDTO(
        String id,
        String rawPassword
) {
}
