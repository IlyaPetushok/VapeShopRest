package project.vapeshop.dto.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class VaporizerDTO {
    private Integer id;
    @NonNull
    private Double resistance;
    @NonNull
    private String type;
}
