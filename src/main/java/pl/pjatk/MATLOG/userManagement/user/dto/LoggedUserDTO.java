package pl.pjatk.MATLOG.userManagement.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoggedUserDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String token;
}
