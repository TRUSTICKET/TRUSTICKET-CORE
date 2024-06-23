package com.trusticket.trusticketcore.dto.member;

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
public class LoginRequest {

    @NotBlank(message = "사용자 이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Schema(description = "사용자 이메일", example = "testtest@gmail.com")
    private String email;


    @NotBlank
    @Schema(description = "비밀번호", example = "test123!")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
}
