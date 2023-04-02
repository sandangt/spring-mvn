package amigos.customer.service;

import amigos.amqp.RabbitMQMessageProducer;
import amigos.clients.fraud.FraudCheckResponse;
import amigos.clients.fraud.FraudClient;
import amigos.clients.notification.NotificationRequest;
import amigos.customer.model.Customer;
import amigos.customer.repository.ICustomerRepository;
import amigos.customer.viewmodel.CustomerRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(ICustomerRepository customerRepository,
                              FraudClient fraudClient,
                              RabbitMQMessageProducer producer
) {
    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFrauster(customer.getId());
        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to amigoscode", customer.getFirstName())
        );
        producer.publish(
            notificationRequest, "internal.exchange", "internal.notification.routing-key"
        );
    }
}
