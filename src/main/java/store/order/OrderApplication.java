package store.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {
        "store.product", "store.order"
})
@SpringBootApplication
public class OrderApplication {
    static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}