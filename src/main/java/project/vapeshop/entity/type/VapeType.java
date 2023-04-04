package project.vapeshop.entity.type;

import java.util.Locale;

public enum VapeType {
    POD("подик"),
    VAPE("мод"),
    EGO("егошка");

    private final String typeVape;

    VapeType(String typeVape) {
        this.typeVape = typeVape;
    }

    public static String getTypeValue(String name){
        VapeType vapeType=VapeType.valueOf(name.toUpperCase(Locale.ROOT));
        return vapeType.typeVape;
    }
}
