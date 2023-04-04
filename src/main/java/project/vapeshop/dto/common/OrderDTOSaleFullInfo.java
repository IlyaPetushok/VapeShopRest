package project.vapeshop.dto.common;

import lombok.*;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.user.UserDTOForCommon;
import project.vapeshop.entity.common.StatusOrder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOSaleFullInfo {
    private Integer id;
    private Date date;
    private StatusOrder status;
    private Double price;
    private Double priceWithDiscount;
    private UserDTOForCommon user;
    private List<ItemDTOInfoForCatalog> items;
}
