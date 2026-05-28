package store.order.dto;

import store.order.ItemIn;
import store.order.ItemOut;
import store.order.ItemProductOut;
import store.order.model.OrderItemModel;

public record OrderItem(
        String id,
        OrderItemProduct product,
        Integer quantity,
        Double total
) {
    public static OrderItem from(ItemIn in) {
        return new OrderItem(
                null,
                new OrderItemProduct(in.idProduct()),
                in.quantity(),
                null
        );
    }

    public OrderItemModel intoModel(Double price) {
        var item = new OrderItemModel();
        item.setId(id);
        item.setProductId(product.id());
        item.setQuantity(quantity);
        item.setTotal(price * quantity);

        return item;
    }

    public ItemOut intoOut(Double rate) {
        return new ItemOut(
            id,
            new ItemProductOut(product.id()),
            quantity,
            total * rate
        );
    }

    public ItemOut intoOut() {
        return new ItemOut(
            id,
            new ItemProductOut(product.id()),
            quantity,
            total
        );
    }
}
