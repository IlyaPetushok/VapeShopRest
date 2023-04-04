package project.vapeshop.dto.user;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class PrivilegesDTO {
    private Integer id;
    @NonNull
    @Size(max = 40, message = "name role dont greater 40")
    private String name;
}
