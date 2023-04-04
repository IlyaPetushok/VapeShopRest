package project.vapeshop.dto.product;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class VaporizerDTOFullInfo {
    private Integer id;
    @Positive
    @NonNull
    private Double resistance;
    @Size(max = 15, message = "type vaporizer dont greater than 15")
    @NonNull
    private String type;
    @NonNull
    private ItemDTOInfoForCatalog itemForVaporizer;
}
