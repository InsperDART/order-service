package store.order.dto;

import store.order.OrderIn;
import store.order.OrderOut;
import store.order.OrdersOut;
import store.order.model.OrderItemModel;
import store.order.model.OrderModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record Order(
        String id,
        String idAccount,
        LocalDateTime date,
        List<OrderItem> items,
        Double total
) {
    public static Order from(OrderIn in, String idAccount)
    {
        return new Order(
                null,
                idAccount,
                LocalDateTime.now(),
                in.items().stream().map(OrderItem::from).toList(),
                null
        );
    }

    public OrderModel intoModel(List<OrderItemModel> items) {
        var order = new OrderModel();
        order.setId(id);
        order.setIdAccount(idAccount);
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

    public OrderOut intoOut(String currency, Double rate) {
        return new OrderOut(
                id,
                date,
                currency,
                items.stream()
                        .map(i -> i.intoOut(rate))
                        .toList(),
                total * rate
        );
    }

    public OrderOut intoOut(String currency) {
        return new OrderOut(
            id,
            date,
            currency,
            items.stream()
                .map(OrderItem::intoOut)
                .toList(),
            total
        );
    }

    public OrdersOut intoOuts() {
        return new OrdersOut(
                id,
                date,
                total
        );
    }
}
