package store.order;

public record OrderItem(
        String id,
        OrderItemProduct product,
        Integer quantity,
        Double total
) {
    static OrderItem from(ItemIn in) {
        return new OrderItem(
                null,
                new OrderItemProduct(in.idProduct()),
                in.quantity(),
                null
        );
    }

    OrderItemModel intoModel(Double price) {
        var item = new OrderItemModel();
        item.setId(id);
        item.setProductId(product.id());
        item.setQuantity(quantity);
        item.setTotal(price * quantity);

        return item;
    }
}
