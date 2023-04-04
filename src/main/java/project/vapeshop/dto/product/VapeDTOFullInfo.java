package project.vapeshop.dto.product;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class VapeDTOFullInfo {
    private Integer id;
    @Positive
    @NonNull
    private Integer power;
    @NonNull
    private Integer battery;
    @Size(max = 30, message = "type vape dont greater 30")
    @NonNull
    private String type;
    @NonNull
    private ItemDTOInfoForCatalog itemForVape;
}
