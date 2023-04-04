package project.vapeshop.entity.type;

import java.util.Locale;

public enum CategoryType {
    LIQUIDE("Жидкости"),
    VAPORIZER("Испарители,Картриджы,Койлы"),
    VAPE("Вейпы и подики");

    private final String categoryName;

    CategoryType(String categoryName) {
        this.categoryName = categoryName;
    }

    public static String getTypeValue(String categoryName){
        CategoryType categoryType=CategoryType.valueOf(categoryName.toUpperCase(Locale.ROOT));
        return categoryType.categoryName;
    }
}
