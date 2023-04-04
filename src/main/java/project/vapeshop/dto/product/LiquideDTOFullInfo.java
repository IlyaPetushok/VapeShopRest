package project.vapeshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LiquideDTOFullInfo {
    private Integer id;
    private ItemDTOInfoForCatalog item;

    @Size(max = 40, message = "dont be getter flovour than 40")
    private String flavour;
    @PositiveOrZero
    private int fortress;
    @Size(max = 15, message = "dont be getter typeNicotine than 15")
    private String typeNicotine;
    @Positive
    private int volume;

    public LiquideDTOFullInfo(Integer id) {
        this.id = id;
    }

    public LiquideDTOFullInfo(ItemDTOInfoForCatalog item, String flavour, int fortress, String typeNicotine, int volume) {
        this.item = item;
        this.flavour = flavour;
        this.fortress = fortress;
        this.typeNicotine = typeNicotine;
        this.volume = volume;
    }

    public LiquideDTOFullInfo(Integer id, String flavour, int fortress, String typeNicotine, int volume) {
        this.id = id;
        this.flavour = flavour;
        this.fortress = fortress;
        this.typeNicotine = typeNicotine;
        this.volume = volume;
    }

}
