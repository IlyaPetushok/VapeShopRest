package project.vapeshop.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.vapeshop.entity.common.StatusOrder;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOFilter extends FilterRequest{
    private Integer id;
    private Date dateBegan;
    private Date dateFinish;
    private StatusOrder status;
    private Double priceBegan;
    private Double priceFinish;
}
