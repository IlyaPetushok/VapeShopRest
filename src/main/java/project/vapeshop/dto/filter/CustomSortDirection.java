package project.vapeshop.dto.filter;

import org.springframework.data.domain.Sort;

import java.util.Locale;

public enum CustomSortDirection {
    ASC(Sort.Direction.ASC),
    DESC(Sort.Direction.DESC);

    private final Sort.Direction direction;

    CustomSortDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public static Sort.Direction getSortDirection(String nameDirection){
        CustomSortDirection customSortDirection=CustomSortDirection.valueOf(nameDirection.toUpperCase(Locale.ROOT));
        return customSortDirection.direction;
    }
}
