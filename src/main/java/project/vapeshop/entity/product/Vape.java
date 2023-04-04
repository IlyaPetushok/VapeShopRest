package project.vapeshop.entity.product;

//import jakarta.validation.constraints.Positive;
//import jakarta.validation.constraints.Size;
import lombok.*;
import project.vapeshop.entity.EntityId;
import jakarta.persistence.*;

//import javax.validation.constraints.Positive;
//import javax.validation.constraints.Size;


@NamedEntityGraph(
        name = "entity-graph-item",
        attributeNodes = {
                @NamedAttributeNode("itemForVape"),
        }
)

@Entity
@Table(name = "Vape")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Vape implements EntityId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vape")
    private Integer id;

    @NonNull
    @Column(name = "power_vape")
    private Integer power;

    @NonNull
    @Column(name = "battery")
    private Integer battery;

    @NonNull
    @Column(name = "type_vape")
    private String type;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vape_id", referencedColumnName = "id_item")
    private Item itemForVape;

}
