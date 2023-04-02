package amigos.customer;

import amigos.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
    scanBasePackages = {"amigos.customer", "amigos.amqp"}
)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"amigos.clients.fraud", "amigos.clients.notification"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer) {
//        return args -> {
//            producer.publish(
//                    new Person("San", 28),
//                    "internal.exchange",
//                    "internal.notification.routing-key");
//        };
//    }
//    record Person(String name, Integer age) {}
}
