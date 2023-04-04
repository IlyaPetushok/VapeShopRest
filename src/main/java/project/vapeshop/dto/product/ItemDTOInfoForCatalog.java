package project.vapeshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTOInfoForCatalog {
    private Integer id;
    private String photo;
    private String name;

    public ItemDTOInfoForCatalog(Integer id) {
        this.id = id;
    }

    public ItemDTOInfoForCatalog(String photo, String name) {
        this.photo = photo;
        this.name = name;
    }

}
