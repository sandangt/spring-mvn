package sandangt.customer.service;

import sandangt.amqp.RabbitMQMessageProducer;
import sandangt.clients.fraud.FraudCheckResponse;
import sandangt.clients.fraud.FraudClient;
import sandangt.clients.notification.NotificationRequest;
import sandangt.customer.model.Customer;
import sandangt.customer.repository.ICustomerRepository;
import sandangt.customer.viewmodel.CustomerRegistrationRequest;
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
                String.format("Hi %s, welcome to template", customer.getFirstName())
        );
        producer.publish(
            notificationRequest, "internal.exchange", "internal.notification.routing-key"
        );
    }
}
