package store.order.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.order.OrderController;
import store.order.OrderIn;
import store.order.OrderOut;
import store.order.OrdersOut;
import store.order.common.exchange.service.ExchangeClient;
import store.order.dto.Order;
import store.order.service.OrderService;

import java.util.List;

@RestController
public class OrderResource implements OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ExchangeClient exchangeClient;

    @Override
    public ResponseEntity<List<OrdersOut>> findAll(
        @RequestHeader("id-account") String idAccount
    ) {
        final var orders = orderService.findAll(idAccount);
        return ResponseEntity.ok(
            orders.stream()
                .map(Order::intoOuts)
                .toList()
        );
    }

    @Override
    public ResponseEntity<OrderOut> findById(
        @NotEmpty @NotNull String idOrder,
        @RequestParam @Nullable String currency,
        @RequestHeader("id-account") String idAccount
    ) {
        final var order = orderService.findById(idOrder, idAccount);
        OrderOut out;
        if (currency != null && currency.equals("USD")) {
            var rate = exchangeClient.getExchange("USD", currency);
            out = order.intoOut(currency, rate.buy());
        } else {
            out = order.intoOut("USD");
        }
        return ResponseEntity.ok(out);
    }

    @Override
    public ResponseEntity<OrderOut> create(
        @Valid @NotNull OrderIn in,
        @RequestHeader("id-account") String idAccount
    ) {
        final var order = orderService.create(
            Order.from(in, idAccount)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(order.intoOut(null));
    }

    @Override
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
