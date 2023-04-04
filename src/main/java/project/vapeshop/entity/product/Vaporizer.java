package project.vapeshop.entity.product;


//import jakarta.validation.constraints.Positive;
//import jakarta.validation.constraints.Size;
import lombok.*;
import project.vapeshop.entity.EntityId;
import jakarta.persistence.*;

//import javax.validation.constraints.Positive;
//import javax.validation.constraints.Size;

@NamedEntityGraph(
        name = "entity-graph-item-vaporizer",
        attributeNodes = {
                @NamedAttributeNode("itemForVaporizer"),
        }
)

@Entity
@Table(name="vaporizer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Vaporizer implements EntityId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaporizer")
    private Integer id;

    @NonNull
    @Column(name = "resistance")
    private Double resistance;

    @NonNull
    @Column(name = "type_vaporizer")
    private String type;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaporizer_id_item",referencedColumnName = "id_item")
    private Item itemForVaporizer;

}
