package project.vapeshop.entity.common;

import jakarta.persistence.*;
import lombok.*;
import project.vapeshop.entity.EntityId;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.user.User;


import java.util.Date;
import java.util.List;

@NamedEntityGraphs(
        {
                @NamedEntityGraph(
                        name = "order_items",
                        attributeNodes = {
                            @NamedAttributeNode("items")
                        }
                )
        }
)


@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Order implements EntityId<Integer> {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_order")
    @NonNull
    private Date date;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id_user", referencedColumnName = "id_user")
    @NonNull
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_order")
    @NonNull
    private StatusOrder status;

    @Column(name = "total_price")
    @NonNull
    private Double price;

    @ToString.Exclude
    @NonNull
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_item",
            joinColumns = @JoinColumn(name = "ot_id_order"),
            inverseJoinColumns = @JoinColumn(name = "ot_id_item"))
    private List<Item> items;
}