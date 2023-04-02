package sandangt.notification.service;

import sandangt.clients.notification.NotificationRequest;
import sandangt.notification.model.Notification;
import sandangt.notification.repository.INotificationRepository;
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
                .sender("sandangt")
                .message(notificationRequest.message())
                .sentAt(LocalDateTime.now())
                .build()
        );
    }
}
