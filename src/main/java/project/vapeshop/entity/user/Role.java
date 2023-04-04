package project.vapeshop.entity.user;

import lombok.*;
import project.vapeshop.entity.EntityId;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Role implements EntityId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;

    @Column(name = "name_role")
    private String name;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_privileges",
            joinColumns = @JoinColumn(name = "rp_id_role"),
            inverseJoinColumns = @JoinColumn(name = "rp_id_privileges"))
    private List<Privileges> privileges;

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }


    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
