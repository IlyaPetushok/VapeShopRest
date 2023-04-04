package project.vapeshop.dto.user;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer id;
    @Size(max = 40, message = "name role dont greater 40")
    private String name;

    public RoleDTO(Integer id) {
        this.id = id;
    }

    public RoleDTO(String name) {
        this.name = name;
    }
}
