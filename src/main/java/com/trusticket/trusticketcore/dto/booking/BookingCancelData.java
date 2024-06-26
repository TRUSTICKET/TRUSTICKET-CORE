package com.trusticket.trusticketcore.dto.booking;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookingCancelData {
    private String offsetId;
}
