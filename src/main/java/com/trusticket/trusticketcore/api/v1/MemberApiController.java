package com.trusticket.trusticketcore.api.v1;

import com.trusticket.trusticketcore.common.response.CommonResponse;
import com.trusticket.trusticketcore.dto.member.LoginRequest;
import com.trusticket.trusticketcore.dto.member.SignupRequest;
import com.trusticket.trusticketcore.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Member", description = "Member API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입 API")
    public CommonResponse<Long> signUp(
            @Valid @RequestBody SignupRequest request
    ) {

        Long memberId = memberService.addMember(request);
        return new CommonResponse<>(true, HttpStatus.CREATED, "회원 가입에 성공했습니다.", memberId);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인 API")
    public CommonResponse<String> signIn(@Valid @RequestBody LoginRequest req) {
        String token = memberService.login(req);
        return new CommonResponse<>(true, HttpStatus.OK, "로그인에 성공했습니다", token);
    }



}
