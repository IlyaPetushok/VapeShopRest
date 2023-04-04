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
public class VapeDTOFilter extends FilterRequest{
    private Integer power;
    private Integer powerMax;
    private Integer battery;
    private Integer batteryMax;
    private List<String> type;
}
