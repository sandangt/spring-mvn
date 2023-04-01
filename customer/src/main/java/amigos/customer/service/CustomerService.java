package amigos.customer.service;

import amigos.clients.fraud.FraudCheckResponse;
import amigos.clients.fraud.FraudClient;
import amigos.clients.notification.NotificationClient;
import amigos.clients.notification.NotificationRequest;
import amigos.customer.model.Customer;
import amigos.customer.repository.ICustomerRepository;
import amigos.customer.viewmodel.CustomerRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(ICustomerRepository customerRepository,
                              FraudClient fraudClient,
                              NotificationClient notificationClient
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
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to amigoscode", customer.getFirstName())
                )
        );
    }
}
