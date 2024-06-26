package com.trusticket.trusticketcore.api.v1;

import com.trusticket.trusticketcore.common.response.CommonResponse;
import com.trusticket.trusticketcore.config.security.constant.AuthConstant;
import com.trusticket.trusticketcore.dto.booking.BookingCancelRequest;
import com.trusticket.trusticketcore.dto.booking.BookingRequest;
import com.trusticket.trusticketcore.dto.member.SignupRequest;
import com.trusticket.trusticketcore.service.booking.BookingService;
import com.trusticket.trusticketcore.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Booking", description = "Booking API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingApiController {
    private final BookingService bookingService;

    @PostMapping("/request")
    @PreAuthorize(AuthConstant.AUTH_COMMON)
    @Operation(summary = "이벤트 티켓 발권 시도")
    public CommonResponse<Long> insert(@Valid @RequestBody BookingRequest req) {
        Long offset = bookingService.insertBookingInQueue(req);
        if(offset != -1){
            return new CommonResponse<>(true, HttpStatus.OK, "티켓발권을 위해 대기열에 진입합니다.", offset);
        }
        else{
            throw new RuntimeException("시도 중 오류가 발생하였습니다.");
        }

    }

    @PostMapping("/cancel")
    @PreAuthorize(AuthConstant.AUTH_COMMON)
    @Operation(summary = "티켓 발권 대기열 이탈")
    public CommonResponse<Long> quit(@Valid @RequestBody BookingCancelRequest req) {
        Long offset = bookingService.quitBookingInQueue(req);
        if(offset != -1){
            return new CommonResponse<>(true, HttpStatus.OK, "대기열에서 이탈하였습니다.", offset);
        }
        else{
            throw new RuntimeException("시도 중 오류가 발생하였습니다.");
        }

    }
}
