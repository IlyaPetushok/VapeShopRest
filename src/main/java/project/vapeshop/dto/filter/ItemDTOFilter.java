package project.vapeshop.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTOFilter extends FilterRequest{
    private String name;
    private BigDecimal price;
    private BigDecimal priceMax;
    private Integer quantity;
}
