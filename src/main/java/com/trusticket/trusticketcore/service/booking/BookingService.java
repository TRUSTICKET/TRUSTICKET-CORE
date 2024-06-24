package com.trusticket.trusticketcore.service.booking;

import com.trusticket.trusticketcore.common.kafka.KafkaProducer;
import com.trusticket.trusticketcore.config.security.SecurityUtil;
import com.trusticket.trusticketcore.dto.booking.BookingData;
import com.trusticket.trusticketcore.dto.booking.BookingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    private final KafkaProducer kafkaProducer;

    @Transactional
    public Boolean insertBookingInQueue(BookingRequest request) {
        BookingData data = BookingData.builder()
                .id(request.getId())
                .memberId(SecurityUtil.getCurrentMemberPk())
                .build();
        Boolean success = kafkaProducer.sendBookingData("booking-request", data);
        return success;
    }
}
