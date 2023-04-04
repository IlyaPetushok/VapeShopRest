package project.vapeshop.dto.common;

import lombok.*;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.user.UserDTOForCommon;
import project.vapeshop.entity.common.StatusOrder;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.user.User;

import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDTOFullInfo {
    private Integer id;

    @FutureOrPresent
    @NonNull
    private Date date;

    @NonNull
    private StatusOrder status;

    @Digits(integer = 4,fraction = 2)
    @NonNull
    private Double price;

    @NonNull
    private UserDTOForCommon user;

    @NonNull
    private List<ItemDTOInfoForCatalog> items;
}
