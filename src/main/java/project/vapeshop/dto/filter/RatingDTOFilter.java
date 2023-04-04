package project.vapeshop.dto.filter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTOFilter extends FilterRequest{
    private String comment;
    private Integer quantityStar;
    private Integer quantityStarMax;
}
