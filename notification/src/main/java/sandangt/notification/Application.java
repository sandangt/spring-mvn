package sandangt.notification;

import sandangt.amqp.RabbitMQMessageProducer;
import sandangt.notification.config.NotificationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
    scanBasePackages = {"sandangt.notification", "sandangt.amqp"}
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
