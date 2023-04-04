package project.vapeshop.entity.type;

import java.util.Locale;

public enum LiquideTypeNicotine {
    SOLD("солевой"),
    USUALLY("обычный");

    private final String typeNicotine;

    LiquideTypeNicotine(String typeNicotine) {
        this.typeNicotine = typeNicotine;
    }

    public static String getTypeValue(String name){
        LiquideTypeNicotine liquideTypeNicotine=LiquideTypeNicotine.valueOf(name.toUpperCase(Locale.ROOT));
        return liquideTypeNicotine.typeNicotine;
    }
}
