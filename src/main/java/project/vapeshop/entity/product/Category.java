package project.vapeshop.entity.product;

import lombok.*;
import project.vapeshop.entity.EntityId;
import jakarta.persistence.*;


@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Category implements EntityId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Integer id;
    @NonNull
//    @Size(max = 30,message = "size comment between 0 and 1000 characters")
    @Column(name="name")
    private String name;

}
