package project.vapeshop.dto.filter;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilterRequest {
    private Integer page=0;
    private Integer size=10;
    private String sortDirection="asc";
    private String sortByName="id";
    private Pageable pageable;

    public Pageable getPageable() {
        return pageable=PageRequest.of(page,size,Sort.by(CustomSortDirection.getSortDirection(sortDirection),sortByName));
    }

}
