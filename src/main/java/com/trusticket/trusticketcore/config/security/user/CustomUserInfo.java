package com.trusticket.trusticketcore.config.security.user;


import com.trusticket.trusticketcore.config.security.constant.RoleType;
import com.trusticket.trusticketcore.dto.member.MemberDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfo extends MemberDto {
    private Long memberId;

    private String email;

    private String name;

    private String password;

    private RoleType role;

}