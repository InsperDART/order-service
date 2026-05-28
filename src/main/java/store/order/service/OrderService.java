package store.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import store.order.OrderOut;
import store.order.repository.OrderRepository;
import store.order.dto.Order;
import store.order.model.OrderItemModel;
import store.order.model.OrderModel;
import store.product.ProductController;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private ProductController products;

    public Order create(Order order) {
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

    public List<Order> findAll(String idAccount) {
        return repository.findAllByIdAccount(idAccount).stream()
            .map(OrderModel::into)
            .toList();
    }

    public Order findById(String id, String idAccount) {
        return repository.findByIdAndIdAccount(id, idAccount).map(OrderModel::into)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Order not found."
            ));
    }
}
