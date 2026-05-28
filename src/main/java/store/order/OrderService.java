package store.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import store.product.ProductController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private ProductController products;

    OrderOut create(Order order) {
        final var items = new ArrayList<OrderItemModel>();
        for(var item: order.items()) {
            final var product = products.findById(item.id()).getBody();
            assert product != null;
            items.add(item.intoModel(product.price()));
        }

        final var model = order.intoModel(items);
        final var saved = repository.save(model);
        return saved.into();
    }

    List<OrderOut> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(OrderModel::into)
                .toList();
    }

    OrderOut findById(String id) {
        return repository.findById(id).map(OrderModel::into)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Order not found."
            ));

    }
}
