package amigos.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "NOTIFICATION")
public interface NotificationClient {
    @PostMapping("/api/v1/notification")
    void sendNotification(NotificationRequest notificationRequest);
}
