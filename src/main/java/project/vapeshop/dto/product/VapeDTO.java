package project.vapeshop.dto.product;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class VapeDTO {
    private Integer id;
    @NonNull
    private Integer power;
    @NonNull
    private Integer battery;
    @NonNull
    private String type;
}
