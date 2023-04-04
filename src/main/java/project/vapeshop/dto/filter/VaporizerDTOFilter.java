package project.vapeshop.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VaporizerDTOFilter extends FilterRequest{
    private Double resistance;
    private Double resistanceMax;
    private List<String> type;
}
