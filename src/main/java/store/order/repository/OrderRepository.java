package store.order.repository;

import org.springframework.data.repository.CrudRepository;
import store.order.model.OrderModel;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderModel, String> {
    List<OrderModel> findAllByIdAccount(String idAccount);
    Optional<OrderModel> findByIdAndIdAccount(String id, String idAccount);
}
