package project.vapeshop.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingDTOForProduct {
    private Integer id;
    @NonNull
    private String comment;
    @NonNull
    private Integer quantityStar;
    @NonNull
    private Integer idUser;
}
