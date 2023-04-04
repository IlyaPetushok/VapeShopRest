package project.vapeshop.dto.common;

import lombok.*;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.user.UserDTOAfterAuthorization;
import project.vapeshop.dto.user.UserDTOForCommon;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingDTOFullInfo {
    private Integer id;
    @NonNull
    @Size(max = 1000,message = "size comment between 0 and 1000 characters")
    private String comment;
    @NonNull
    @Min(value = 0,message = "dont be star negative")
    @Max(value = 5,message = "dont be star greater than 5")
    private Integer quantityStar;
    @NonNull
    private ItemDTOInfoForCatalog item;
    @NonNull
    private UserDTOAfterAuthorization user;

}
