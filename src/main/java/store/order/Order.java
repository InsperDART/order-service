package store.order;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
        String id,
        LocalDateTime date,
        List<OrderItem> items,
        Double total
) {
    static Order from(OrderIn in)
    {
        return new Order(
                null,
                LocalDateTime.now(),
                in.items().stream().map(OrderItem::from).toList(),
                null
        );
    }

    OrderModel intoModel(List<OrderItemModel> items) {
        var order = new OrderModel();
        order.setId(id);
        order.setDate(date);
        items.forEach(i -> i.setOrder(order));
        order.setItems(items);
        order.setTotal(
                items.stream()
                        .map(OrderItemModel::getTotal)
                        .reduce(0.0, Double::sum)
        );
        return order;
    }
}
