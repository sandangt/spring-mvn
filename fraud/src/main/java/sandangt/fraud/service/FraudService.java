package sandangt.fraud.service;

import sandangt.fraud.model.FraudCheckHistory;
import sandangt.fraud.repository.IFraudCheckHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudService {
    private final IFraudCheckHistoryRepository fraudCheckHistoryRepository;
    public Boolean isFraudulentCustomer(Integer customerId) {
        fraudCheckHistoryRepository.save(
            FraudCheckHistory.builder()
                    .customerId(customerId)
                    .isFraudster(false)
                    .createdAt(LocalDateTime.now())
                    .build()
        );
        return false;
    }
}
