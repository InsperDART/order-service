package store.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.order.OrderOut;
import store.order.dto.Order;

import java.time.LocalDateTime;
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

    @Column(name = "id", nullable = false, updatable = false)
    private String idAccount;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItemModel> items;

    @Column(name = "total", nullable = false)
    private Double total;

    public Order into() {
        return new Order(
            id,
            idAccount,
            date,
            items.stream().map(OrderItemModel::into).toList(),
            total
        );
    }
}

