package project.vapeshop.dto.product;

import lombok.*;
import project.vapeshop.entity.product.Category;
import javax.validation.constraints.Digits;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDTOFullInfo {
    private Integer id;
    @NonNull
    private String photo;
    @NonNull
    @Size(max = 40,message = "name characters getter 40")
    private String name;
    @NonNull
    private Category category;
    @NonNull
    @Digits(integer=3, fraction=2)
    private BigDecimal price;
    @NonNull
    @PositiveOrZero
    private Integer quantity;

    public ItemDTOFullInfo(Integer id) {
        this.id = id;
    }
}
