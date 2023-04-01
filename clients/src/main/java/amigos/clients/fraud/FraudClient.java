package amigos.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "FRAUD")
public interface FraudClient {
    @GetMapping(path = "/api/v1/fraud-check/{customerId}")
    FraudCheckResponse isFrauster(@PathVariable("customerId") Integer customerId);
}
