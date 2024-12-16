package pl.pjatk.MATLOG.UserManagement.user;

public interface UserMapperFactory {

    UserDTO createDTO();
    UserDAO createDAO();
}
