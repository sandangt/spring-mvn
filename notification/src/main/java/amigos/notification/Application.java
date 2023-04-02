package amigos.notification;

import amigos.amqp.RabbitMQMessageProducer;
import amigos.notification.config.NotificationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
    scanBasePackages = {"amigos.notification", "amigos.amqp"}
)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer, NotificationConfig config) {
        return args -> {
            producer.publish(
                new Person("San", 28),
                config.getInternalExchange(),
                config.getInternalNotificationRoutingKey());
        };
    }
    record Person(String name, Integer age) {}
}