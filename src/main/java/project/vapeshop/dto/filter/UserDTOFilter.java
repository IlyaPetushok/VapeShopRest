package project.vapeshop.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOFilter extends FilterRequest {
    private Integer id;
    private String surname;
    private String name;
    private String patronymic;
    private String login;
    private String password;
    private String mail;
}
