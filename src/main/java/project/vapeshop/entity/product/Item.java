package project.vapeshop.entity.product;

import lombok.*;
import project.vapeshop.entity.EntityId;
import project.vapeshop.entity.common.Rating;
import java.math.BigDecimal;
import jakarta.persistence.*;
import java.util.List;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "entity-item-graph-liquide",
                attributeNodes = {
                        @NamedAttributeNode("liquide"),
                }
        ),
        @NamedEntityGraph(
                name = "entity-item-graph-category",
                attributeNodes = {
                        @NamedAttributeNode("category")
                }
        )})

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Item implements EntityId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer id;
    @Column(name = "photo")
    private String photo;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Category category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "itemForLiquide",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Liquide liquide;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "itemForVaporizer", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Vaporizer vaporizer;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "itemForVape", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Vape vape;


    public Item(Integer id) {
        this.id = id;
    }

    public Item(Integer id, String photo, String name) {
        this.id = id;
        this.photo = photo;
        this.name = name;
    }

    public Item(Integer id, String photo, String name, Category category, BigDecimal price, int quantity) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }


    public Item(String photo, String name, Category category, BigDecimal price, int quantity) {
        this.photo = photo;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
}
