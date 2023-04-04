package project.vapeshop.dto.product;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDTO {
    private Integer id;

    @Size(max = 30,message = "size comment between 0 and 1000 characters")
    @NonNull
    private String name;
}
