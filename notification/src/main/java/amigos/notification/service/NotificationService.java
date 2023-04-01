package amigos.notification.service;

import amigos.clients.notification.NotificationRequest;
import amigos.notification.model.Notification;
import amigos.notification.repository.INotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final INotificationRepository notificationRepository;
    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
            Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerName())
                .sender("Amigoscode")
                .message(notificationRequest.message())
                .sentAt(LocalDateTime.now())
                .build()
        );
    }
}
