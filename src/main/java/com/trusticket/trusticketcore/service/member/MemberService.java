package com.trusticket.trusticketcore.service.member;

import com.trusticket.trusticketcore.dto.member.LoginRequest;
import com.trusticket.trusticketcore.dto.member.SignupRequest;

public interface MemberService {

    Long addMember(SignupRequest member);

    String login(LoginRequest dto);


}
