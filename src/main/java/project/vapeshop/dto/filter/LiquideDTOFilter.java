package project.vapeshop.dto.filter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LiquideDTOFilter extends FilterRequest{
    private List<String> flavour;
    private Integer fortress;
    private Integer fortressMax;
    private String typeNicotine;
    private Integer volume;
    private Integer volumeMax;
}
