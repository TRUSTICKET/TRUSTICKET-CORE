package com.trusticket.trusticketcore.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @Schema(description = "사용자 PK", example = "1")
    private Long memberId;

    @Schema(description = "사용자 이메일", example = "testtest@gmail.com")
    private String email;

    @Schema(description = "사용자 이름", example = "John Doe")
    private String name;

}