package project.vapeshop.entity.user;


import lombok.*;
import project.vapeshop.entity.EntityId;
import jakarta.persistence.*;


@NamedEntityGraph(
        name = "entity-user-graph-role",
        attributeNodes = {
                @NamedAttributeNode("role"),
        }
)


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements EntityId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name="patronymic")
    private String patronymic;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name="mail")
    private String mail;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_role",referencedColumnName = "id_role")
    private Role role;


    public User(Integer id) {
        this.id = id;
    }

    public User(String surname, String name, String patronymic, String login, String password, String mail, Role role) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.role = role;
    }

}
