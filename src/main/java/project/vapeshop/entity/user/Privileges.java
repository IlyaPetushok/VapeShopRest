package project.vapeshop.entity.user;

import lombok.*;
import project.vapeshop.entity.EntityId;
import jakarta.persistence.*;

@Entity
@Table(name="privileges")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Privileges implements EntityId<Integer> {
    @Id
    @Column(name="id_privelege")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name="name_privelege")
    private String name;

}
