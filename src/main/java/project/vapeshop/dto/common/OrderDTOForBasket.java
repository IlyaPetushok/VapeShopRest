package project.vapeshop.dto.common;

import lombok.*;
import project.vapeshop.entity.common.StatusOrder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDTOForBasket{
    private Integer id;

    @NonNull
    private Date date;

    @NonNull
    private StatusOrder status;

    @NonNull
    private Double price;

}
