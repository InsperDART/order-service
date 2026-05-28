package store.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class OrderResource implements OrderController {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<List<OrderOut>> findAll() {
        final var orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<OrderOut> findById(@NotEmpty @NotNull String idOrder) {
        final var order = orderService.findById(idOrder);
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<OrderOut> create(@Valid OrderIn in) {
        final var order = orderService.create(Order.from(in));
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.id())
                .toUri()
        ).build();
    }
}
