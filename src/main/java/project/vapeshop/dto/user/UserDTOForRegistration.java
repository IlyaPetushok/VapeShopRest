package project.vapeshop.dto.user;

import lombok.*;
import project.vapeshop.entity.user.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTOForRegistration {
    private Integer id;
    @NonNull
    @Size(max = 30, message = "surname dont greater than 30")
    private String surname;
    @NonNull
    @Size(max = 30, message = "name dont greater than 30")
    private String name;
    @NonNull
    @Size(max = 30, message = "patronymic dont greater than 30")

    private String patronymic;
    @NonNull
    @Size(max = 30, message = "login dont greater than 30")

    private String login;
    @NonNull
    @Size(max = 30, message = "password dont greater than 30")
    private String password;
    @NonNull
    @Size(max = 30, message = "email dont greater than 30")
    @Email(message = " correct name email ")
    private String mail;
    @NonNull
    private RoleDTO role;

    public UserDTOForRegistration(@NonNull String login, @NonNull String password) {
        this.login = login;
        this.password = password;
    }

}
