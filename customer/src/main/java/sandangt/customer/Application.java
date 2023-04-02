package sandangt.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
    scanBasePackages = {"sandangt.customer", "sandangt.amqp"}
)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"sandangt.clients.fraud", "sandangt.clients.notification"})
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
