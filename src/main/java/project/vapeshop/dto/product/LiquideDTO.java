package project.vapeshop.dto.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class LiquideDTO {
    private Integer id;
    @NonNull
    private String flavour;
    @NonNull
    private Integer fortress;
    @NonNull
    private String typeNicotine;
    @NonNull
    private Integer volume;

    public LiquideDTO(Integer id) {
        this.id = id;
    }
}

