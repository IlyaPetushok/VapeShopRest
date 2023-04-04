package project.vapeshop.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTOSaleResponse {
    private Integer idOrder;
    private Integer idUser;
    private Double beganPrice;
    private Date date;
    private String role;
    private Double priceAfterDiscount;

    public OrderDTOSaleResponse(Integer idOrder,Integer idUser,Double beganPrice, String role) {
        this.idOrder=idOrder;
        this.idUser=idUser;
        this.beganPrice = beganPrice;
        this.role = role;
    }
}
