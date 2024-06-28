package com.trusticket.trusticketcore.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    @Schema(description = "이벤트 ID", example = "cetSSZABXRBCGU9nqg3O")
    private String id;

    @Schema(description = "유저 ID(나중에 없애야함, 보안)", example = "1")
    private String memberId;


}
