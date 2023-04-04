package project.vapeshop.entity.type;

import java.util.Locale;

public enum VaporizerType {
    CATRIDG("катридж"),
    COIL("койлы"),
    VAPORIZE("испаритель");

    String typeVaporizer;

    VaporizerType(String typeVaporizer) {
        this.typeVaporizer = typeVaporizer;
    }

    public static String getTypeVaporizer(String name){
        VaporizerType vaporizeType=VaporizerType.valueOf(name.toUpperCase(Locale.ROOT));
        return vaporizeType.typeVaporizer;
    }
}
