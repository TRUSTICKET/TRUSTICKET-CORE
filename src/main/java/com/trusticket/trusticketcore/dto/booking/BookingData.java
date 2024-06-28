package com.trusticket.trusticketcore.dto.booking;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BookingData {

    private String id;

    private String memberId;

}
