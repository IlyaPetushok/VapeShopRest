package project.vapeshop.entity.common;


import lombok.*;
import project.vapeshop.entity.EntityId;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.user.User;
import jakarta.persistence.*;


@Entity
@Table(name = "rating")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Rating implements EntityId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rating")
    private Integer id;

    @Column(name="comment")
    @NonNull
    private String comment;


    @Column(name = "quantity_stars")
    @NonNull
    private Integer quantityStar;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id_item",referencedColumnName = "id_item")
    @NonNull
    private Item item;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    @JoinColumn(name = "rating_id_user",referencedColumnName = "id_user")
    private User user;
}
