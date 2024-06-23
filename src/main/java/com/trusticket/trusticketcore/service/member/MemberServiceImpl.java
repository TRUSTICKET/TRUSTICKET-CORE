package com.trusticket.trusticketcore.service.member;


import com.trusticket.trusticketcore.common.ErrorDefineCode;
import com.trusticket.trusticketcore.common.exception.custom.exception.AlreadyExistElementException409;
import com.trusticket.trusticketcore.common.exception.custom.exception.AuthCredientialException401;
import com.trusticket.trusticketcore.config.security.constant.RoleType;
import com.trusticket.trusticketcore.config.security.jwt.JwtUtil;
import com.trusticket.trusticketcore.config.security.user.CustomUserInfo;
import com.trusticket.trusticketcore.domain.Member;
import com.trusticket.trusticketcore.dto.member.LoginRequest;
import com.trusticket.trusticketcore.dto.member.SignupRequest;
import com.trusticket.trusticketcore.repository.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.trusticket.trusticketcore.common.ErrorDefineCode.ALREADY_EXIST_EMAIL;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public Long addMember(SignupRequest inputMember) {
        Member exist = memberRepository.findMemberByEmail(inputMember.getEmail());
        if(exist != null){
            throw new AlreadyExistElementException409(ALREADY_EXIST_EMAIL);
        }
        inputMember.setPassword(encoder.encode(inputMember.getPassword()));
        Member member = modelMapper.map(inputMember, Member.class);
        member.setRole(RoleType.COMMON);
        member = memberRepository.save(member);
        return member.getMemberId();
    }

    @Override
    @Transactional
    public String login(LoginRequest dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findMemberByEmail(email);
        if(member == null) {
            throw new AuthCredientialException401(ErrorDefineCode.AUTH_NOT_FOUND_EMAIL);
        }

        if(!encoder.matches(password, member.getPassword())) {
            throw new AuthCredientialException401(ErrorDefineCode.AUTH_NMATCH_PWD);
        }

        CustomUserInfo info = modelMapper.map(member, CustomUserInfo.class);

        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }

}
