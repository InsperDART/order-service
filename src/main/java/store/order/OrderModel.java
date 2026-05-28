package store.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItemModel> items;

    @Column(name = "total", nullable = false)
    private Double total;

    OrderOut into() {
        return new OrderOut(
            id,
            date,
            items.stream().map(OrderItemModel::into).toList(),
            total
        );
    }
}

